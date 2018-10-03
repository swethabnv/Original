package com.wsnweb.action;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.FarmerGroupTeam;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.User;
import com.wsndata.data.UserFarmerGroup;
import com.wsndata.data.UserProvince;
import com.wsndata.data.UserRegion;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.FarmerGroupTeamHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.ReportHome;
import com.wsnweb.form.ReportForm;
import com.wsnweb.util.Utility;

public class ReportAction extends Action {
	private static final Logger log = Logger.getLogger(ReportAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Print".equals(eform.getCmd())) {
				return print(mapping, form, request, response);
			} else if ("PrintItext".equals(eform.getCmd())) {
				return printItext(mapping, eform, request, response);
			} else if ("GetBreedGroup".equals(eform.getCmd())) {
				return getBreedGroup(mapping, eform, request, response);
			} else if ("GetCooperative".equals(eform.getCmd())) {
				return getCooperative(mapping, eform, request, response);
			} else if ("GetFarmerGroup".equals(eform.getCmd())) {
				return getFarmerGroup(mapping, eform, request, response);
			} else {
				return load(mapping, form, request, response);
			}
		}
	}

	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		long districtNo = 0, subDistrictNo = 0;
		ArrayList<String> farmerGroupId = new ArrayList<String>();
		ArrayList<String> regionNo = new ArrayList<String>();
		ArrayList<String> provinceNo = new ArrayList<String>();
		try {
			sf.getCurrentSession().beginTransaction();

			if ("R000".equals(eform.getRep())) {
				eform
						.setMsg("F-R000 รายงานรายละเอียดข้อมูลของสมาชิกสหกรณ์ที่เข้าร่วมโครงการ");
			} else if ("R001".equals(eform.getRep())) {
				eform
						.setMsg("F-R001 รายงานประมาณการผลผลิตของเกษตรกร (แยกตามชนิดพืช)");
			} else if ("R002".equals(eform.getRep())) {
				eform
						.setMsg("F-R002 รายงานประมาณการผลผลิตของเกษตรกร (ระบุกลุ่มพันธุ์)");
			} else if ("R003".equals(eform.getRep())) {
				eform
						.setMsg("F-R003 รายงานประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มเกษตรกร)");
			} else if ("R004".equals(eform.getRep())) {
				eform.setMsg("F-R004 รายงานสรุปประมาณการผลผลิตของเกษตรกร");
			} else if ("R005".equals(eform.getRep())) {
				eform
						.setMsg("F-R005 รายงานสรุปประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มพันธุ์)");
			} else if ("R006".equals(eform.getRep())) {
				eform
						.setMsg("F-R006 รายงานสรุปประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มเกษตรกร)");
			} else if ("R007".equals(eform.getRep())) {
				eform
						.setMsg("F-R007 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)");
			} else if ("R008".equals(eform.getRep())) {
				eform
						.setMsg("F-R008 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)");
			} else if ("R009".equals(eform.getRep())) {
				eform
						.setMsg("F-R009 รายงานปริมาณผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)");
			} else if ("R010".equals(eform.getRep())) {
				eform
						.setMsg("F-R010 รายงานปริมาณผลผลิตที่คาดว่าจะออกสู่ตลาด (แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)");
			} else if ("R011".equals(eform.getRep())) {
				eform
						.setMsg("F-R011 รายงานผลผลิตเปรียบเทียบกับต้นทุนค่าใช้จ่ายของเกษตรกร");
			} else if ("R012".equals(eform.getRep())) {
				eform
						.setMsg("F-R012 รายงานผลผลิตเปรียบเทียบกับต้นทุนค่าใช้จ่ายของเกษตรกรรายบุคคล");
			} else {
				eform.setMsg("ไม่มีรายงาน");
			}

			long level = userLogin.getLevel();
			RegionHome rHome = new RegionHome();
			List<Region> rList = new ArrayList<Region>();
			Region reg = new Region();
			if (level == 1)
				rList = rHome.findAll();
			else {
				Region r;
				for(UserRegion u : userLogin.getUserRegion()){
					if(u!=null) {
						r = rHome.searchByNo(u.getRegionNo());
						rList.add(r);
					}
				}
			}
			if (rList.size() > 1) {
				reg.setRegionNo(0);
				reg.setRegionName("กรุณาเลือก");
				rList.add(0, reg);
			}
			session.setAttribute("regionList", rList);

			ProvinceHome pHome = new ProvinceHome();
			List<Province> pList = new ArrayList<Province>();
			Province prov = new Province();
			if (level == 1) {
				pList = pHome.findAll();
			} else if (level == 2) {
				//pList = pHome.retrieveByRegionNo(userLogin.getRegionNo());
				ArrayList<Long> rNoList = new ArrayList<Long>();
				for(UserRegion u : userLogin.getUserRegion()){
					if(u!=null) {
						rNoList.add(u.getRegionNo());
					}
				}
				pList = pHome.retrieveByRegionNoList(rNoList);
			} else {
				Province p;
				for(UserProvince u : userLogin.getUserProvince()){
					if(u!=null) {
						p = pHome.searchByProvinceNo(u.getProvinceNo());
						pList.add(p);
					}
				}
			}
			if (pList.size() > 1) {
				prov.setRegionNo(0);
				prov.setProvinceNo(0);
				prov.setThaiName("กรุณาเลือก");
				pList.add(0, prov);
			}
			session.setAttribute("provinceList", pList);

			FarmerGroupHome fHome = new FarmerGroupHome();
			List<FarmerGroup> fgList = new ArrayList<FarmerGroup>();
			List<FarmerGroup> cgList = new ArrayList<FarmerGroup>();
			FarmerGroup f = new FarmerGroup();
			//farmerGroupId = userLogin.getFarmerGroupId();
			for(UserFarmerGroup u : userLogin.getUserFarmerGroup()){
				if(u!=null)
					farmerGroupId.add(String.valueOf(u.getFarmerGroupId()));
			}
			if (userLogin.getLevel() > 0 && userLogin.getLevel() < 6) {
				if (userLogin.getLevel() > 1) {
					//regionNo = userLogin.getUserRegion();
					for(UserRegion u : userLogin.getUserRegion()){
						if(u!=null)
							regionNo.add(String.valueOf(u.getRegionNo()));
					}
				}
				if (userLogin.getLevel() > 2){
					//provinceNo = userLogin.getUserProvince();
					for(UserProvince u : userLogin.getUserProvince()){
						if(u!=null)
							provinceNo.add(String.valueOf(u.getProvinceNo()));
					}
				}
				if (userLogin.getLevel() > 3)
					districtNo = userLogin.getDistrictNo();
				if (userLogin.getLevel() > 4)
					subDistrictNo = userLogin.getSubDistrictNo();
				if (eform.getProvinceNo() != 0) {
					provinceNo.clear();
					provinceNo.add(String.valueOf(eform.getProvinceNo()));
				}
			}
			cgList = fHome.searchByAnauthorizeFilteredByCriteria("C", regionNo, provinceNo,
					districtNo, subDistrictNo, farmerGroupId);
			if (cgList.size() > 0) {
				f.setFarmerGroupId(0);
				f.setFarmerGroupName("กรุณาเลือก");
				cgList.add(0, f);
			}
			session.setAttribute("cooperativeList", cgList);
			f.setFarmerGroupId(0);
			f.setFarmerGroupName("กรุณาเลือก");
			fgList.add(0, f);
			session.setAttribute("farmerGroupList", fgList);

			BreedTypeHome bhome = new BreedTypeHome();
			List<BreedType> bList = new ArrayList<BreedType>();
			BreedType b = new BreedType();
			bList = bhome.findAll();
			b.setBreedTypeId(0);
			b.setBreedTypeName("กรุณาเลือก");
			bList.add(0, b);
			session.setAttribute("breedTypeList", bList);

			List<BreedGroup> bgList = new ArrayList<BreedGroup>();
			BreedGroup bg = new BreedGroup();
			// bgList = bghome.findAll();
			bg.setBreedGroupId(0);
			bg.setBreedGroupName("กรุณาเลือก");
			bgList.add(0, bg);
			session.setAttribute("breedGroupList", bgList);

			List<String> months = new ArrayList<String>();
			months.add("มกราคม");
			months.add("กุมภาพันธ์");
			months.add("มีนาคม");
			months.add("เมษายน");
			months.add("พฤษภาคม");
			months.add("มิถุนายน");
			months.add("กรกฎาคม");
			months.add("สิงหาคม");
			months.add("กันยายน");
			months.add("ตุลาคม");
			months.add("พฤศจิกายน");
			months.add("ธันวาคม");
			session.setAttribute("Months", months);

			List<String> years = new ArrayList<String>();
			for (int i = 0; i < 20; i++) {
				years.add("" + (2550 + i));
			}
			session.setAttribute("Years", years);

			PlantHome rpHome = new PlantHome();
			session.setAttribute("plantYears", rpHome.getPlantYear());
			session.setAttribute("plantNo", rpHome.getPlantNo());

			session.setAttribute("ReportForm", eform);
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("report");
	}

	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		try {
			sf.getCurrentSession().beginTransaction();
			OutputStream outputStream = response.getOutputStream();
			String path = getServlet().getServletContext().getRealPath("/");
			File reportFile = new File(path + "/report/"
					+ eform.getRep().toLowerCase() + ".jasper");
			byte[] bytes = null;

			try {
				String[] conn = Utility.getConnections();
				Connection con = DriverManager.getConnection(conn[0], conn[1],
						conn[2]);

				Calendar cal = Calendar.getInstance();

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm", new Locale("th", "TH"));
				String printDate = dateFormat.format(cal.getTime());

				Map<String, Object> param = new HashMap<String, Object>();

				// Loop บวกค่าจากตัวแปร แล้วเก็บเข้าพารามิเตอร์ เพื่อส่งเข้า
				// report
				// โดยเมื่อเลือกจะมีค่า 1 2 จากนั้นนำผมรวมมาใช้ โดยค่าที่ได้จะมี
				// 0 คือ ไม่เลือก, 1 คือ นาปี, 2 คือ นาปรัง, 3 คือ นาปี และ
				// นาปรัง
				String[] strSeason = eform.getSeason();
				Integer season = 0;
				if (strSeason != null && strSeason.length > 0) {
					for (int i = 0; i < strSeason.length; i++) {
						season += Integer.parseInt(strSeason[i]);
					}
				}
				String[] strFta = eform.getFta();
				Integer fta = 0;
				if (strFta != null && strFta.length > 0) {
					for (int i = 0; i < strFta.length; i++) {
						fta += Integer.parseInt(strFta[i]);
					}
				}
				String[] strObjective = eform.getObjective();
				Integer objective = 0;
				if (strObjective != null && strObjective.length > 0) {
					for (int i = 0; i < strObjective.length; i++) {
						objective += Integer.parseInt(strObjective[i]);
					}
				}
				String[] strQualifi = eform.getQualification();
				Integer qualification = 0;
				if (strQualifi != null && strQualifi.length > 0) {
					for (int i = 0; i < strQualifi.length; i++) {
						qualification += Integer.parseInt(strQualifi[i]);
					}
				}

				long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0;
				if (userLogin.getLevel() < 3 && eform.getProvinceNo() > 0) {
					provinceNo = eform.getProvinceNo();
				} else {
					if (userLogin.getLevel() > 1)
						regionNo = userLogin.getRegionNo();
					if (userLogin.getLevel() > 2)
						provinceNo = userLogin.getProvinceNo();
					if (userLogin.getLevel() > 3)
						districtNo = userLogin.getDistrictNo();
					if (userLogin.getLevel() > 4)
						subDistrictNo = userLogin.getSubDistrictNo();
					if (eform.getProvinceNo() != 0)
						provinceNo = eform.getProvinceNo();
				}

				param.put("IN_YEAR", eform.getPlantYear());
				param.put("IN_PRINTDATE", printDate);
				param.put("IN_FTA", fta);
				param.put("IN_SEASON", season);
				param.put("IN_OBJECTIVE", objective);
				param.put("IN_MANUFACTURE", qualification);
				param.put("IN_REGIONNO", regionNo);
				param.put("IN_PROVINCENO", provinceNo);
				param.put("IN_DISTRICTNO", districtNo);
				param.put("IN_SUBDISTRICTNO", subDistrictNo);

				if ("R000".equals(eform.getRep())) {
					printR000(form, param);
				} else if ("R001".equals(eform.getRep())) {
					printR001(form, param);
				} else if ("R002".equals(eform.getRep())) {
					printR001(form, param);
				} else if ("R003".equals(eform.getRep())) {
					printR003(form, param);
				} else if ("R004".equals(eform.getRep())) {
					printR001(form, param);
				} else if ("R005".equals(eform.getRep())) {
					printR001(form, param);
				} else if ("R006".equals(eform.getRep())) {
					printR003(form, param);
				} else if ("R007".equals(eform.getRep())) {
					printR007(form, param);
				} else if ("R008".equals(eform.getRep())) {
					printR007(form, param);
				} else if ("R011".equals(eform.getRep())) {
					printR011(form, param);
				} else if ("R012".equals(eform.getRep())) {
					param.put("SUBREPORT_DIR", path + "/report/");
					printR011(form, param);
				}

				JRProperties.setProperty(
						"net.sf.jasperreports.default.pdf.font.name",
						"THSarabunNew.ttf");
				JRProperties.setProperty(
						"net.sf.jasperreports.default.pdf.encoding",
						"Identity-H");
				JRProperties.setProperty(
						"net.sf.jasperreports.default.pdf.embedded", "true");
				
				log.info("==report path="+reportFile.getPath());
				
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						param, con);

				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				
				response.addHeader( "Content-Disposition", "inline; filename=" + eform.getRep() );
				

				outputStream.write(bytes, 0, bytes.length);
				//outputStream.flush();
				outputStream.close();
				
			} catch (JRException e) {
				log.error(e, e);
				// display stack trace in the browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				response.getOutputStream().print(stringWriter.toString());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(outputStream != null){
					outputStream.close();
				}
				if(response.getOutputStream() != null){
					response.getOutputStream().close();
				}
				
			}
		} catch (Exception e) {
			eform.setMsg(getResources(request).getMessage(request.getLocale(),
					"Print.Fail"));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	public void printR000(ActionForm form, Map<String, Object> param)
			throws Exception {
		ReportForm eform = (ReportForm) form;

		ProvinceHome pHome = new ProvinceHome();
		Province prov = new Province();
		prov.setThaiName("");
		if (eform.getProvinceNo() > 0)
			prov = pHome.searchByProvinceNo(eform.getProvinceNo());

		FarmerGroupTeamHome fgtHome = new FarmerGroupTeamHome();
		List<FarmerGroupTeam> farmerGroupTeam = new ArrayList<FarmerGroupTeam>();
		if (eform.getCooperativeId() > 0) {
			if (eform.getFarmerGroupId() > 0) {
				farmerGroupTeam = fgtHome.getTop3ByFarmerGroupId(eform
						.getFarmerGroupId());
			}
		}

		FarmerGroupHome fgHome = new FarmerGroupHome();
		FarmerGroup coo = new FarmerGroup();
		FarmerGroup fg = new FarmerGroup();
		coo.setFarmerGroupName("");
		fg.setFarmerGroupName("");
		if (eform.getCooperativeId() > 0)
			coo = fgHome.findByFarmerGroupId(eform.getCooperativeId());
		if (eform.getFarmerGroupId() > 0)
			fg = fgHome.findByFarmerGroupId(eform.getFarmerGroupId());

		BreedTypeHome btHome = new BreedTypeHome();
		BreedType breedType = new BreedType();
		breedType.setBreedTypeName("");
		if (eform.getBreedTypeId() > 0)
			breedType = btHome.findByBreedTypeId(eform.getBreedTypeId());

		Calendar endDay = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("th", "TH"));
		endDay.setTime(sdFormat.parse(eform.getEndDate()));
		String endDate = new SimpleDateFormat("yyyy/MM/dd").format(endDay
				.getTime());

		param.put("IN_DATE", endDate);
		param.put("IN_THAIDATE", Utility.getDateThai(endDay.getTime()));
		param.put("IN_PROVINCE", prov.getThaiName());
		param.put("IN_BREEDTYPE", breedType.getBreedTypeName());
		param.put("IN_COOPERATIVEID", (int) eform.getCooperativeId());
		param.put("IN_FARMERGROUPID", (int) eform.getFarmerGroupId());
		param.put("IN_COOPERATIVE", coo.getFarmerGroupName());
		param.put("IN_FARMERGROUP", fg.getFarmerGroupName());
		if (farmerGroupTeam.size() > 0) {
			for (int i = 0; i < farmerGroupTeam.size(); i++) {
				FarmerGroupTeam fgt = farmerGroupTeam.get(i);
				param.put("IN_MEMBER" + (i + 1), fgt.getFirstName() + " "
						+ fgt.getLastName());
			}
		}
	}

	public void printR001(ActionForm form, Map<String, Object> param)
			throws Exception {
		ReportForm eform = (ReportForm) form;

		ProvinceHome pHome = new ProvinceHome();
		Province prov = new Province();
		prov.setThaiName("");
		if (eform.getProvinceNo() > 0)
			prov = pHome.searchByProvinceNo(eform.getProvinceNo());

		FarmerGroupHome fgHome = new FarmerGroupHome();
		FarmerGroup coo = new FarmerGroup();
		FarmerGroup fg = new FarmerGroup();
		coo.setFarmerGroupName("");
		fg.setFarmerGroupName("");
		if (eform.getCooperativeId() > 0)
			coo = fgHome.findByFarmerGroupId(eform.getCooperativeId());
		if (eform.getFarmerGroupId() > 0)
			fg = fgHome.findByFarmerGroupId(eform.getFarmerGroupId());

		BreedTypeHome btHome = new BreedTypeHome();
		BreedType breedType = new BreedType();
		breedType.setBreedTypeName("");
		if (eform.getBreedTypeId() > 0)
			breedType = btHome.findByBreedTypeId(eform.getBreedTypeId());

		Calendar endDay = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("th", "TH"));
		endDay.setTime(sdFormat.parse(eform.getEndDate()));
		String endDate = new SimpleDateFormat("yyyy/MM/dd").format(endDay
				.getTime());

		param.put("IN_DATE", endDate);
		param.put("IN_THAIDATE", Utility.getDateThai(endDay.getTime()));
		param.put("IN_PROVINCE", prov.getThaiName());
		param.put("IN_BREEDTYPE", breedType.getBreedTypeName());
		param.put("IN_COOPERATIVEID", (int) eform.getCooperativeId());
		param.put("IN_FARMERGROUPID", (int) eform.getFarmerGroupId());
		param.put("IN_COOPERATIVE", coo.getFarmerGroupName());
		param.put("IN_FARMERGROUP", fg.getFarmerGroupName());
	}

	public void printR003(ActionForm form, Map<String, Object> param)
			throws Exception {
		ReportForm eform = (ReportForm) form;

		ProvinceHome pHome = new ProvinceHome();
		Province prov = new Province();
		prov.setThaiName("");
		if (eform.getProvinceNo() > 0)
			prov = pHome.searchByProvinceNo(eform.getProvinceNo());

		FarmerGroupHome fgHome = new FarmerGroupHome();
		FarmerGroup coo = new FarmerGroup();
		coo.setFarmerGroupName("");
		if (eform.getCooperativeId() > 0)
			coo = fgHome.findByFarmerGroupId(eform.getCooperativeId());

		BreedTypeHome btHome = new BreedTypeHome();
		BreedType breedType = new BreedType();
		breedType.setBreedTypeName("");
		if (eform.getBreedTypeId() > 0)
			breedType = btHome.findByBreedTypeId(eform.getBreedTypeId());

		Calendar endDay = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("th", "TH"));
		endDay.setTime(sdFormat.parse(eform.getEndDate()));
		String endDate = new SimpleDateFormat("yyyy/MM/dd").format(endDay
				.getTime());

		param.put("IN_DATE", endDate);
		param.put("IN_THAIDATE", Utility.getDateThai(endDay.getTime()));
		param.put("IN_PROVINCE", prov.getThaiName());
		param.put("IN_BREEDTYPE", breedType.getBreedTypeName());
		param.put("IN_COOPERATIVEID", (int) eform.getCooperativeId());
		param.put("IN_COOPERATIVE", coo.getFarmerGroupName());
	}

	public void printR007(ActionForm form, Map<String, Object> param)
			throws Exception {
		ReportForm eform = (ReportForm) form;

		ProvinceHome pHome = new ProvinceHome();
		Province prov = new Province();
		prov.setThaiName("");
		if (eform.getProvinceNo() > 0)
			prov = pHome.searchByProvinceNo(eform.getProvinceNo());

		FarmerGroupHome fgHome = new FarmerGroupHome();
		FarmerGroup coo = new FarmerGroup();
		coo.setFarmerGroupName("");
		if (eform.getCooperativeId() > 0)
			coo = fgHome.findByFarmerGroupId(eform.getCooperativeId());

		BreedTypeHome btHome = new BreedTypeHome();
		BreedType breedType = new BreedType();
		breedType.setBreedTypeName("");
		if (eform.getBreedTypeId() > 0)
			breedType = btHome.findByBreedTypeId(eform.getBreedTypeId());

		Calendar startDay = Calendar.getInstance();
		startDay.set(eform.getForecastYearStart(), eform
				.getForecastMonthStart(), 1);
		Calendar endDay = Calendar.getInstance();
		endDay.set(eform.getForecastYearEnd(), eform.getForecastMonthEnd(), 1);

		Calendar sqlStartDay = Calendar.getInstance();
		sqlStartDay.set(eform.getForecastYearStart(), eform
				.getForecastMonthStart(), 1);
		Calendar sqlEndDay = Calendar.getInstance();
		sqlEndDay.set(eform.getForecastYearEnd(),
				eform.getForecastMonthEnd() + 1, 1);
		sqlEndDay.add(Calendar.DAY_OF_MONTH, -1);

		SimpleDateFormat sqlDate = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("th", "TH"));
		String startDate = sqlDate.format(sqlStartDay.getTime());
		String endDate = sqlDate.format(sqlEndDay.getTime());

		ReportHome rHome = new ReportHome();
		List<?> r008List = rHome.findR008ByCriteria(startDate, endDate, eform
				.getPlantYear(), eform.getProvinceNo(), eform
				.getCooperativeId(), eform.getBreedTypeId(), eform.getSeason(),
				eform.getObjective(), eform.getQualification(), eform.getFta());
		if (eform.getRep().equals("R008") && r008List.size() > 0)
			param.put("IN_REPORT", "r008");
		else
			param.put("IN_REPORT", "r007");

		param.put("IN_PROVINCE", prov.getThaiName());
		param.put("IN_BREEDTYPE", breedType.getBreedTypeName());
		param.put("IN_START", startDate);
		param.put("IN_END", endDate);
		param.put("IN_THAISTART", Utility.getMonthYearThai(startDay.getTime()));
		param.put("IN_THAIEND", Utility.getMonthYearThai(endDay.getTime()));
		param.put("IN_COOPERATIVEID", (int) eform.getCooperativeId());
		param.put("IN_COOPERATIVE", coo.getFarmerGroupName());
	}

	public void printR011(ActionForm form, Map<String, Object> param)
			throws Exception {
		ReportForm eform = (ReportForm) form;

		ProvinceHome pHome = new ProvinceHome();
		Province prov = new Province();
		prov.setThaiName("");
		if (eform.getProvinceNo() > 0)
			prov = pHome.searchByProvinceNo(eform.getProvinceNo());

		FarmerGroupHome fgHome = new FarmerGroupHome();
		FarmerGroup coo = new FarmerGroup();
		FarmerGroup fg = new FarmerGroup();
		coo.setFarmerGroupName("");
		fg.setFarmerGroupName("");
		if (eform.getCooperativeId() > 0)
			coo = fgHome.findByFarmerGroupId(eform.getCooperativeId());
		if (eform.getFarmerGroupId() > 0)
			fg = fgHome.findByFarmerGroupId(eform.getFarmerGroupId());

		BreedTypeHome btHome = new BreedTypeHome();
		BreedType breedType = new BreedType();
		breedType.setBreedTypeName("");
		if (eform.getBreedTypeId() > 0)
			breedType = btHome.findByBreedTypeId(eform.getBreedTypeId());

		Calendar endDay = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("th", "TH"));
		endDay.setTime(sdFormat.parse(eform.getEndDate()));
		String endDate = new SimpleDateFormat("yyyy/MM/dd").format(endDay
				.getTime());
		/*
		if (eform.getRep().equals("R012")){
			param.put("IN_REPORT", "r012");
		}
		else {
			param.put("IN_REPORT", "r011");
		}*/

		param.put("IN_DATE", endDate);
		param.put("IN_THAIDATE", Utility.getDateThai(endDay.getTime()));
		param.put("IN_PROVINCE", prov.getThaiName());
		param.put("IN_BREEDTYPE", breedType.getBreedTypeName());
		param.put("IN_COOPERATIVEID", (int) eform.getCooperativeId());
		param.put("IN_FARMERGROUPID", (int) eform.getFarmerGroupId());
		param.put("IN_COOPERATIVE", coo.getFarmerGroupName());
		param.put("IN_FARMERGROUP", fg.getFarmerGroupName());
		param.put("IN_PLANTNO", (int)eform.getPlantNo());
		param.put("IN_HUMIDTYPE", (int)eform.getHumidType()); // 0 : ข้าวสดก่อนลดความชื้น , 1 : ข้าวหลังจากลดความชื้น
	}

	public ActionForward printItext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Document document = null;
		PdfWriter writer = null;
		try {
			sf.getCurrentSession().beginTransaction();

			String path = getServlet().getServletContext().getRealPath("/");
			response.setContentType("application/pdf");
			document = new Document(PageSize.A4.rotate(), 20, 20, 20, 10);
			writer = PdfWriter.getInstance(document, output);
			document.open();

			BaseFont basefont = BaseFont.createFont(path
					+ "WEB-INF/classes/THSarabunNew.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			Font font_title = new Font(basefont, 14F, Font.BOLD);
			Font font_criteria = new Font(basefont, 14F);
			Font font_body = new Font(basefont, 12F);
			Font font_italic = new Font(basefont, 11F, Font.ITALIC);

			document.newPage();

			Integer objective = 0, qualification = 0, fta = 0;
			try {
				int startM = eform.getForecastMonthStart();
				int startY = eform.getForecastYearStart();
				int endM = eform.getForecastMonthEnd();
				int endY = eform.getForecastYearEnd();
				Calendar startDay = Calendar.getInstance();
				startDay.set(startY, startM, 1);
				Calendar endDay = Calendar.getInstance();
				endDay.set(endY, endM, 1);

				Calendar sqlStartDay = Calendar.getInstance();
				sqlStartDay.set(startY + 543, startM, 1);
				Calendar sqlEndDay = Calendar.getInstance();
				sqlEndDay.set(endY + 543, endM + 1, 1);
				sqlEndDay.add(Calendar.DAY_OF_MONTH, -1);

				String[] strObjective = eform.getObjective();
				if (strObjective != null && strObjective.length > 0) {
					for (int i = 0; i < strObjective.length; i++) {
						objective += Integer.parseInt(strObjective[i]);
					}
				}
				String[] strQualifi = eform.getQualification();
				if (strQualifi != null && strQualifi.length > 0) {
					for (int i = 0; i < strQualifi.length; i++) {
						qualification += Integer.parseInt(strQualifi[i]);
					}
				}
				String[] strFta = eform.getFta();
				if (strFta != null && strFta.length > 0) {
					for (int i = 0; i < strFta.length; i++) {
						fta += Integer.parseInt(strFta[i]);
					}
				}

				ReportHome rhome = new ReportHome();
				RegionHome regHome = new RegionHome();
				BreedTypeHome btHome = new BreedTypeHome();
				BreedGroupHome bgHome = new BreedGroupHome();
				FarmerGroupHome fHome = new FarmerGroupHome();
				List<FarmerGroup> cgList = new ArrayList<FarmerGroup>();
				if (userLogin.getLevel() < 3 && eform.getProvinceNo() > 0) {
					cgList = fHome.findByProvinceNo(eform.getProvinceNo());
				} else {
					long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, farmerGroupId = 0;
					farmerGroupId = userLogin.getFarmerGroupId();
					if (userLogin.getLevel() > 1)
						regionNo = userLogin.getRegionNo();
					if (userLogin.getLevel() > 2)
						provinceNo = userLogin.getProvinceNo();
					if (userLogin.getLevel() > 3)
						districtNo = userLogin.getDistrictNo();
					if (userLogin.getLevel() > 4)
						subDistrictNo = userLogin.getSubDistrictNo();
					if (eform.getProvinceNo() != 0)
						provinceNo = eform.getProvinceNo();
					cgList = fHome.searchByAnauthorize("C", regionNo,
							provinceNo, districtNo, subDistrictNo,
							farmerGroupId);
				}
				String cooArr = "";
				if (cgList.size() > 0) {
					for (int i = 0; i < cgList.size(); i++) {
						FarmerGroup cGroup = cgList.get(i);
						cooArr += String.valueOf(cGroup.getFarmerGroupId());
						if (i < cgList.size() - 1)
							cooArr += ", ";
					}
				}
				String cooperative = "(" + cooArr + ")";
				List<?> dataList = null;
				int months = (((endY - startY) * 12) - startM) + (endM + 1);
				if (eform.getRep().equals("R009"))
					dataList = rhome.findR009ByCriteria(sqlStartDay.getTime(),
							sqlEndDay.getTime(), months, eform.getRegionNo(),
							cooperative, eform.getBreedType(), eform
									.getBreedGroup(), strObjective, strQualifi,
							strFta);
				else if (eform.getRep().equals("R010")) {
					dataList = rhome.findR010ByCriteria(sqlStartDay.getTime(),
							sqlEndDay.getTime(), months, eform.getRegionNo(),
							cooperative, eform.getBreedType(), eform
									.getBreedGroup(), strObjective, strQualifi,
							strFta);
					if (dataList.size() == 0)
						dataList = rhome.findR009ByCriteria(sqlStartDay
								.getTime(), sqlEndDay.getTime(), months, eform
								.getRegionNo(), cooperative, eform
								.getBreedType(), eform.getBreedGroup(),
								strObjective, strQualifi, strFta);
				}
				int jj = 0, No = 0, tmpNo = 0;
				String region = "ไม่ระบุ", breedType = "ไม่ระบุ", breedGroup = "ไม่ระบุ";
				if (eform.getRegionNo() > 0) {
					Region reg = regHome.searchByNo(eform.getRegionNo());
					region = reg.getRegionName();
				}
				if (eform.getBreedType() > 0) {
					BreedType bt = btHome.findByBreedTypeId(eform
							.getBreedType());
					breedType = bt.getBreedTypeName();
				}
				if (eform.getBreedGroup() > 0) {
					BreedGroup bg = bgHome.findByBreedGroupId(eform
							.getBreedGroup());
					breedGroup = bg.getBreedGroupName();
				}
				boolean pass = true;
				float totalProv = 0, totalCoop = 0, sumRecord = 0;
				float[] sumCooperative = new float[12];
				float[] sumProvince = new float[12];
				Arrays.fill(sumCooperative, 0);
				Arrays.fill(sumProvince, 0);
				do {
					// Title
					String title = "";
					if (eform.getRep().equals("R009"))
						title = "รายงานปริมาณผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)";
					else if (eform.getRep().equals("R010"))
						title = "รายงานปริมาณผลผลิตที่คาดว่าจะออกสู่ตลาด (แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)";
					Paragraph head = new Paragraph(title, font_title);
					head.setAlignment(Element.ALIGN_CENTER);
					document.add(head);

					if (dataList != null && dataList.size() > 0) {
						Object[] obj = (Object[]) dataList.get(jj);
						region = obj[1].toString();
						// breedType = obj[2].toString();
						// breedGroup = obj[3].toString();
					}

					Paragraph head_date = new Paragraph("ตั้งแต่เดือน   "
							+ Utility.getMonthYearThai(startDay.getTime())
							+ "   ถึงเดือน   "
							+ Utility.getMonthYearThai(endDay.getTime()),
							font_title);
					head_date.setAlignment(Element.ALIGN_CENTER);
					head_date.setSpacingBefore(1);
					document.add(head_date);

					PdfPTable header = new PdfPTable(7);
					header.setSpacingBefore(10);
					header.setWidthPercentage(100);
					header.setWidths(new float[] { 6, 16, 4.5F, 15, 24, 6.5F,
							20 });
					PdfPCell sCell;
					sCell = new PdfPCell(new Phrase("ภูมิภาค", font_title));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase(": " + region,
							font_criteria));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("", font_body));
					sCell.setColspan(5);
					sCell.setBorder(0);
					header.addCell(sCell);

					sCell = new PdfPCell(new Phrase("ชนิดพืช", font_title));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase(": " + breedType,
							font_criteria));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("", font_body));
					sCell.setColspan(4);
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("FTA           ไม่ใช่ FTA",
							font_criteria));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setBorder(0);
					header.addCell(sCell);

					sCell = new PdfPCell(new Phrase("ประเภทข้าว", font_title));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase(": " + breedGroup,
							font_criteria));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("ปลูกเพื่อ", font_title));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase(
							":     บริโภค      ทำเมล็ดพันธุ์", font_criteria));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("", font_body));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(new Phrase("การผลิตแบบ", font_title));
					sCell.setBorder(0);
					header.addCell(sCell);
					sCell = new PdfPCell(
							new Phrase(
									":     อินทรีย์แท้       อินทรีย์       ไม่อินทรีย์",
									font_criteria));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setBorder(0);
					header.addCell(sCell);

					document.add(header);

					PdfContentByte canvas = writer.getDirectContent();
					// state 1: Create rectangle
					canvas.setRGBColorFill(0x00, 0x00, 0x00);
					canvas.saveState();
					// fill and stroke a rectangle in บริโภค
					canvas.rectangle(258, PageSize.A4.getWidth() - 126, 9, 9);
					canvas.stroke();
					// fill and stroke a rectangle in ทำเมล็ดพันธุ์
					canvas.rectangle(301, PageSize.A4.getWidth() - 126, 9, 9);
					canvas.stroke();

					// fill and stroke a rectangle in FTA
					canvas.rectangle(PageSize.A4.getHeight() - 119, PageSize.A4
							.getWidth() - 108, 9, 9);
					canvas.stroke();
					// fill and stroke a rectangle in ไม่ FTA
					canvas.rectangle(PageSize.A4.getHeight() - 71, PageSize.A4
							.getWidth() - 108, 9, 9);
					canvas.stroke();

					canvas.restoreState();
					// fill and stroke a rectangle in อินทรีย์แท้
					canvas.rectangle(PageSize.A4.getHeight() - 178, PageSize.A4
							.getWidth() - 126, 9, 9);
					canvas.stroke();
					// fill and stroke a rectangle in อินทรีย์
					canvas.rectangle(PageSize.A4.getHeight() - 119, PageSize.A4
							.getWidth() - 126, 9, 9);
					canvas.stroke();
					// fill and stroke a rectangle in ไม่อินทรีย์
					canvas.rectangle(PageSize.A4.getHeight() - 71, PageSize.A4
							.getWidth() - 126, 9, 9);
					canvas.stroke();
					// End Title

					// Header
					Table table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.setBorder(0);
					float[] colWidth = new float[] { 1, 2, 1, 1, 6, 0.7f };
					String mLenght = "";
					for (int i = 0; i < 12; i++) {
						String thaiMonthShort[] = new String[] { "ม.ค.",
								"ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.",
								"ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค." };
						int yy = (startY + 543) - 2500;
						if ((startM + i) > 11)
							yy++;
						String mm = thaiMonthShort[(startM + i) % 12];
						mLenght += "|" + mm + " " + yy;
					}
					String[] headTable = new String[] {
							"จังหวัดที่ตั้ง",
							"ชื่อสหกรณ์",
							"อำเภอที่ตั้งแปลง",
							"ตำบลที่ตั้งแปลง",
							"|ประมาณผลผลิตที่คาดว่าจะได้  หน่วย : ตัน"
									+ mLenght, "ผลผลิตรวม\n(ตัน)" };
					document.add(headerBand(headTable, colWidth, font_body));
					// End Header

					// Detail
					PdfPCell cell;
					if (dataList == null || dataList.size() < 1) {
						PdfPTable noData = new PdfPTable(1);
						noData.setWidthPercentage(100);
						cell = new PdfPCell(new Phrase("ไม่มีข้อมูล",
								font_title));
						cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						cell.setVerticalAlignment(Cell.ALIGN_TOP);
						cell.setFixedHeight(24);
						noData.addCell(cell);
						document.add(noData);
						pass = false;
					} else {
						String[] detailTable;
						String districtTmp = "", provinceTmp = "", cooperativeTmp = "";

						for (int i = jj; i < dataList.size(); i++) {
							Object[] obj = (Object[]) dataList.get(i);
							detailTable = new String[6];
							if (provinceTmp.equals(obj[4].toString())) {
								detailTable[0] = "";
							} else {
								provinceTmp = obj[4].toString();
								detailTable[0] = provinceTmp;
							}
							if (cooperativeTmp.equals(obj[5].toString())) {
								detailTable[1] = "";
							} else {
								cooperativeTmp = obj[5].toString();
								detailTable[1] = cooperativeTmp;
							}
							if (districtTmp.equals(obj[6].toString())) {
								detailTable[2] = "";
							} else {
								districtTmp = obj[6].toString();
								detailTable[2] = districtTmp;
							}
							detailTable[3] = obj[7].toString();
							detailTable[4] = "";

							double forecastRecord = 0;
							DecimalFormat df = new DecimalFormat("0.00");
							for (int y = 0; y < months; y++) {
								forecastRecord = (float) Math
										.round(Double.parseDouble(obj[21 + y]
												.toString()) * 100) / 100;
								detailTable[4] += "|"
										+ df.format(forecastRecord);
								sumCooperative[y] += forecastRecord;
								sumProvince[y] += forecastRecord;
								sumRecord += forecastRecord;
								totalProv += forecastRecord;
								totalCoop += forecastRecord;
							}
							for (int z = months; z < 12; z++) {
								detailTable[4] += "|" + df.format(0);
							}

							detailTable[5] = df.format(sumRecord);
							document.add(detailBand(detailTable, colWidth,
									font_body));
							No++;
							tmpNo++;
							sumRecord = 0;

							// Total
							Object[] sum = null;
							String ssCoo = "", ssPro = "";
							if ((i + 1) < dataList.size())
								sum = (Object[]) dataList.get(i + 1);
							else
								pass = false;
							for (int a = 0; a < sumCooperative.length; a++) {
								ssCoo += "|" + df.format(sumCooperative[a]);
								ssPro += "|" + df.format(sumProvince[a]);
							}
							if (sum == null) {
								String[] totalTable = new String[] {
										"ยอดรวมตามสหกรณ์", ssCoo,
										df.format(totalCoop) };
								document.add(summaryBand(totalTable, colWidth,
										font_body, 1));
								String[] total2Table = new String[] {
										"ยอดรวมตามจังหวัด", ssPro,
										df.format(totalProv) };
								document.add(summaryBand(total2Table, colWidth,
										font_body, 0));
								totalProv = 0;
								totalCoop = 0;
								Arrays.fill(sumCooperative, 0);
								Arrays.fill(sumProvince, 0);
								tmpNo = 1;
							} else {
								if (!sum[5].toString().equals(cooperativeTmp)) {
									String[] totalTable = new String[] {
											"ยอดรวมตามสหกรณ์", ssCoo,
											df.format(totalCoop) };
									document.add(summaryBand(totalTable,
											colWidth, font_body, 2));
									totalCoop = 0;
									Arrays.fill(sumCooperative, 0);
								}
								if (!sum[4].toString().equals(provinceTmp)) {
									String[] total2Table = new String[] {
											"ยอดรวมตามจังหวัด", ssPro,
											df.format(totalProv) };
									document.add(summaryBand(total2Table,
											colWidth, font_body, 0));
									totalProv = 0;
									Arrays.fill(sumProvince, 0);
									jj = i + 1;
									tmpNo = 0;
									document.newPage();
									break;
								}
							}
							// End Total

							if (tmpNo % 15 == 0) {
								jj = i + 1;
								tmpNo = 0;
								PdfPTable footer = new PdfPTable(1);
								footer.setWidthPercentage(100);
								PdfPCell fCell;
								fCell = new PdfPCell(new Phrase("", font_title));
								fCell.setBorder(0);
								fCell.setBorderWidthTop(0.5F);
								footer.addCell(fCell);
								document.add(footer);
								document.newPage();
								break;
							}
						}
					}
					// End Detail
				} while (pass);

			} catch (Exception e) {
				document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด",
						font_title));
				e.printStackTrace();
				log.error(e, e);
			} finally {
				sf.getCurrentSession().close();
			}

			document.close();

			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(output.toByteArray());
				PdfStamper stamper = new PdfStamper(reader, out);
				int n = reader.getNumberOfPages();
				PdfContentByte cbq;
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 543);
				Date timePrint = cal.getTime();
				for (int i = 1; i <= n; i++) {
					cbq = stamper.getOverContent(i);
					ColumnText ct = new ColumnText(cbq);

					ct.setSimpleColumn(PageSize.A4.getHeight() - 25,
							PageSize.A4.getWidth() - 30, 25, 0, 0,
							Element.ALIGN_RIGHT);
					ct.addElement(numPage(i, n, timePrint, font_italic));
					ct.go();

					ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
							.getWidth() - 30, 25, 0, 0, Element.ALIGN_RIGHT);
					ct.addElement(new Paragraph("[F-" + eform.getRep() + "]",
							font_italic));
					ct.go();
					if (objective == 1 || objective == 3) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 104, 261, 50, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if (objective >= 2) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 104, 304, 0, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if (fta == 1 || fta == 3) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 86,
								PageSize.A4.getHeight() - 116, 0, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if (fta >= 2) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 86, PageSize.A4.getHeight() - 68,
								0, 0, Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if ((qualification == 1 || qualification == 3)
							|| (qualification == 5 || qualification == 7)) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 104,
								PageSize.A4.getHeight() - 175, 0, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if ((qualification > 1 && qualification < 4)
							|| (qualification > 5 && qualification < 8)) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 104,
								PageSize.A4.getHeight() - 116, 0, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
					if (qualification >= 4) {
						ct.setSimpleColumn(PageSize.A4.getHeight(), PageSize.A4
								.getWidth() - 104,
								PageSize.A4.getHeight() - 68, 0, 0,
								Element.ALIGN_RIGHT);
						ct.addElement(new Paragraph("/", font_criteria));
						ct.go();
					}
				}
				// Close the stamper
				stamper.close();
				reader.close();
				response.setContentLength(out.size());
				OutputStream os = response.getOutputStream();
				out.writeTo(os);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			eform.setMsg(getResources(request).getMessage(request.getLocale(),
					"Print.Fail"));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	private PdfPTable headerBand(String[] text, float[] widths, Font iFont)
			throws DocumentException {
		int cols = text.length;
		PdfPTable table = new PdfPTable(cols);
		table.setWidthPercentage(100);
		table.setWidths(widths);
		table.setSpacingBefore(10);

		PdfPCell cell;
		Color iColor = new CMYKColor(44, 30, 33, 0);

		for (int i = 0; i < cols; i++) {
			if (text[i].indexOf("|") > -1) {
				String[] inner = text[i].split("\\|");
				int innerCols = inner.length;

				PdfPTable innerTable = new PdfPTable(innerCols - 2);
				PdfPCell innerCell;
				for (int j = 1; j < innerCols; j++) {
					innerCell = new PdfPCell(new Phrase(inner[j], iFont));
					innerCell.setFixedHeight(22);
					innerCell.setBackgroundColor(iColor);
					innerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					innerCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
					if (j == 1)
						innerCell.setColspan(innerCols - 2);
					innerTable.addCell(innerCell);
				}
				cell = new PdfPCell(innerTable);
				cell.setBorder(0);
				cell.setPadding(0);
			} else {
				cell = new PdfPCell(new Phrase(text[i], iFont));
				cell.setBackgroundColor(iColor);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			}
			table.addCell(cell);
		}

		return table;
	}

	private PdfPTable detailBand(String[] text, float[] widths, Font iFont)
			throws DocumentException {
		int cols = text.length;
		PdfPTable table = new PdfPTable(cols);
		table.setWidthPercentage(100);
		table.setWidths(widths);

		PdfPCell cell;

		for (int i = 0; i < cols; i++) {
			if (text[i].indexOf("|") > -1) {
				String[] inner = text[i].split("\\|");
				int innerCols = inner.length;

				PdfPTable innerTable = new PdfPTable(innerCols - 1);
				PdfPCell innerCell;
				for (int j = 1; j < innerCols; j++) {
					innerCell = new PdfPCell(new Phrase(inner[j], iFont));
					innerCell.setFixedHeight(22);
					innerCell.setPaddingRight(3);
					innerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					innerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					innerTable.addCell(innerCell);
				}
				cell = new PdfPCell(innerTable);
				cell.setBorder(0);
				cell.setPadding(0);
			} else {
				cell = new PdfPCell(new Phrase(text[i], iFont));
				cell.setBorderWidth(0.5F);
				if (i >= 0 && i <= 2) {
					cell.setBorderWidthBottom(0);
					if (text[i].equals(""))
						cell.setBorderWidthTop(0);
				}
				if (i < 4) {
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setPaddingLeft(3);
				} else {
					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					cell.setPaddingRight(3);
				}
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			}
			table.addCell(cell);
		}

		return table;
	}

	private PdfPTable summaryBand(String[] text, float[] widths, Font iFont,
			long ending) throws DocumentException {
		int cols = text.length;
		PdfPTable table = new PdfPTable(cols + 3);
		table.setWidthPercentage(100);
		table.setWidths(widths);

		PdfPCell cell;

		for (int i = 0; i < cols; i++) {
			if (text[i].indexOf("|") > -1) {
				String[] inner = text[i].split("\\|");
				int innerCols = inner.length;

				PdfPTable innerTable = new PdfPTable(innerCols - 1);
				PdfPCell innerCell;
				for (int j = 1; j < innerCols; j++) {
					innerCell = new PdfPCell(new Phrase(inner[j], iFont));
					innerCell.setFixedHeight(22);
					innerCell.setPaddingRight(3);
					innerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					innerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
					innerTable.addCell(innerCell);
				}
				cell = new PdfPCell(innerTable);
				cell.setBorder(0);
				cell.setPadding(0);
			} else {
				if (i == 0) {
					cell = new PdfPCell();
					cell.setColspan(2);
					cell.setBorder(0);
					if (ending >= 1)
						cell.setBorderWidthTop(0.5F);
					if (ending == 2)
						cell.setBorderWidthBottom(0.5F);
					table.addCell(cell);
				}
				cell = new PdfPCell(new Phrase(text[i], iFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				cell.setPaddingRight(3);
				if (i == 0) {
					cell.setColspan(2);
				}
			}
			table.addCell(cell);
		}

		return table;
	}

	public static Paragraph numPage(int pageCount, int totalPage,
			Date timePrint, Font font_body) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy เวลา HH:mm น.");
		String dateStr = format.format(timePrint);
		Paragraph numPage = new Paragraph("Page :  " + pageCount + "  of  "
				+ totalPage + "\nวัน/เวลาพิมพ์ " + dateStr, font_body);
		numPage.setAlignment(Element.ALIGN_RIGHT);
		return numPage;
	}

	public static String dateThai(String strDate, boolean notDay) {
		String Months[] = { "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
				"พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน",
				"ตุลาคม", "พฤศจิกายน", "ธันวาคม" };

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		int year = 0, month = 0, day = 0;
		try {
			Date date = df.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DATE);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (notDay) {
			return String.format("%s %s", Months[month], year);
		} else {
			return String.format("%s %s %s", day, Months[month], year);
		}
	}

	public static Date dateMonths(String strMonth, String strYear, boolean start) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String Months[] = { "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
				"พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน",
				"ตุลาคม", "พฤศจิกายน", "ธันวาคม" };
		for (int i = 0; i < Months.length; i++) {
			if (Months[i].equals(strMonth)) {
				try {
					if (start) {
						return df.parse("01/" + (i + 1) + "/" + strYear);
					} else {
						Calendar c = Calendar.getInstance();
						c.setTime(df.parse("01/" + (i + 1) + "/" + strYear));
						c.add(Calendar.MONTH, 1);
						c.set(Calendar.DAY_OF_MONTH, 1);
						c.add(Calendar.DATE, -1);
						return c.getTime();
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	public static int getMonths(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int month = 0;
		try {
			Date date = df.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			month = c.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month + 1;
	}

	public static String convertDate(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cvDate = "";
		try {
			Date date = df.parse(strDate);
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			cvDate = df2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cvDate;
	}

	// Ajax Method
	public ActionForward getBreedGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		try {
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();
			List<BreedGroup> breedGroup = null;
			if (!"".equals(eform.getBreedTypeId()))
				breedGroup = home.findByBreedTypeId(eform.getBreedTypeId());
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setExcludes(new String[] { "breedTypeId", "period",
							"plantPeriodFrom", "plantPeriodTo",
							"forcastPeriodFrom", "forcastPeriodTo",
							"lastUpdateDate", "lastUpdateBy", "breedType",
							"checkBox", "linkImageEdit", "breedTypeName" });
			String result = JSONArray.fromObject(breedGroup, jsonConfig)
					.toString();
			result = result.trim().replaceAll(" ", " ");
			wt.print(result);
			wt.flush();
			wt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	// Ajax Method
	public ActionForward getCooperative(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		try {
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			List<FarmerGroup> farmerGroup = null;
			long districtNo = 0, subDistrictNo = 0;
			ArrayList<String> farmerGroupId = new ArrayList<String>();
			ArrayList<String> regionNo = new ArrayList<String>();
			ArrayList<String> provinceNo = new ArrayList<String>();
			//farmerGroupId = userLogin.getFarmerGroupId();
			for(UserFarmerGroup u : userLogin.getUserFarmerGroup()){
				if(u!=null)
					farmerGroupId.add(String.valueOf(u.getFarmerGroupId()));
			}
			if (userLogin.getLevel() > 1) {
				//regionNo = userLogin.getUserRegion();
				for(UserRegion u : userLogin.getUserRegion()){
					if(u!=null)
						regionNo.add(String.valueOf(u.getRegionNo()));
				}
			}
			if (userLogin.getLevel() > 2){
				//provinceNo = userLogin.getUserProvince();
				for(UserProvince u : userLogin.getUserProvince()){
					if(u!=null)
						provinceNo.add(String.valueOf(u.getProvinceNo()));
				}
			}
			if (userLogin.getLevel() > 3)
				districtNo = userLogin.getDistrictNo();
			if (userLogin.getLevel() > 4)
				subDistrictNo = userLogin.getSubDistrictNo();
			if (eform.getProvinceNo() != 0) {
				provinceNo.clear();
				provinceNo.add(String.valueOf(eform.getProvinceNo()));
			}
			farmerGroup = home.searchByAnauthorizeFilteredByCriteria("C", regionNo, provinceNo,
					districtNo, subDistrictNo, farmerGroupId);
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.setExcludes(new String[] { "branchCode",
					"lastUpdateDate", "lastUpdateBy", "target", "strTarget",
					"farmerGroupType", "objective", "addressNo", "moo",
					"village", "soi", "road", "mobile", "tel", "fax",
					"createDate", "createBy", "farmerGroupFarmer",
					"farmerGroupAddress", "farmerGroupChild",
					"farmerGroupTeam", "checkBox", "linkImageEdit",
					"linkImageFarmerGroupEdit", "provinceName", "districtName",
					"subDistrictName", "joinCooperative", "countFarmer" });
			String result = JSONArray.fromObject(farmerGroup, jsonConfig)
					.toString();
			result = result.trim().replaceAll(" ", " ");
			wt.print(result);
			wt.flush();
			wt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	// Ajax Method
	public ActionForward getFarmerGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		try {
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			List<FarmerGroup> farmerGroup = null;
			if (eform.getCooperativeId() != 0)
				farmerGroup = home
						.findByCooperativeId(eform.getCooperativeId());
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.setExcludes(new String[] { "branchCode",
					"lastUpdateDate", "lastUpdateBy", "target", "strTarget",
					"farmerGroupType", "objective", "addressNo", "moo",
					"village", "soi", "road", "mobile", "tel", "fax",
					"createDate", "createBy", "farmerGroupFarmer",
					"farmerGroupAddress", "farmerGroupChild",
					"farmerGroupTeam", "checkBox", "linkImageEdit",
					"linkImageFarmerGroupEdit", "provinceName", "districtName",
					"subDistrictName", "joinCooperative", "countFarmer" });
			String result = JSONArray.fromObject(farmerGroup, jsonConfig)
					.toString();
			result = result.trim().replaceAll(" ", " ");
			wt.print(result);
			wt.flush();
			wt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}
}