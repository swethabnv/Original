package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Branch;

import com.wsndata.data.District;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.BranchForm;
import com.wsnweb.util.Utility;

public class BranchAction extends Action {
	private static final Logger log = Logger.getLogger(BranchAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BranchForm eform = (BranchForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return saveBranch(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				load(mapping, form, request, response);
				return edit(mapping, form, request, response);
			} else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			 } else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			 } else if ("GetPostCode".equals(eform.getCmd())) {
				 return getPostCode(mapping, eform, request, response);
			 } else {
				return load(mapping, form, request, response);
			}
		}
	}

	private ActionForward saveBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BranchForm eform = (BranchForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		BranchHome bHome = new BranchHome();
		ProvinceHome pHome = new ProvinceHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			Branch bObj = bHome.findByBranchCode(eform.getBranchCode());
			eform.setBranchName(eform.getBranchName().trim());
			if (bObj != null) {
				//if (!bObj.getBranchName().equals(eform.getBranchName())) {
					Branch bObjTmp = bHome.findByBranchNameAndAddress(eform.getBranchCode(),eform.getBranchName(),
							eform.getAddress(),eform.getProvinceNo(),eform.getDistrictNo(),eform.getSubDistrictNo());
					if (bObjTmp != null) {
						msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
						eform.setCmd("Edit");
						eform.setMsg(msg);
						return mapping.findForward("branch");
					}
				//}
				bObj.setRegionNo(pHome.searchByProvinceNo(eform.getProvinceNo()).getRegionNo());
				bObj.setLastUpdateBy(userLogin.getUserName());
				bObj.setLastUpdateDate(new Date());
				eform.loadToBean(bObj);
				bHome.saveOrUpdate(bObj);
			} else {
				bObj = bHome.findByBranchNameAndAddress(eform.getBranchCode(),eform.getBranchName(),
						eform.getAddress(),eform.getProvinceNo(),eform.getDistrictNo(),eform.getSubDistrictNo());
				if (bObj != null) {
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					eform.setMsg(msg);
					return mapping.findForward("branch");
				}
				bObj = new Branch();
				if (eform.getPbranchCode()==0) {
					bObj.setPbranchCode(1);
				}else{
					bObj.setPbranchCode(eform.getPbranchCode());
				}
				bObj.setSeq(bHome.getSeqbyPbranchCode(eform.getPbranchCode())+1);
				bObj.setRegionNo(pHome.searchByProvinceNo(eform.getProvinceNo()).getRegionNo());
				bObj.setLastUpdateBy(userLogin.getUserName());
				bObj.setLastUpdateDate(new Date());
				eform.loadToBean(bObj);
				bHome.persist(bObj);
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("branch");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BranchForm eform = (BranchForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			BranchHome home = new BranchHome();
			Branch bObj = home.findByBranchCode(eform.getBranchCode());
			eform.loadFromBean(bObj);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("branch");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BranchForm eform = (BranchForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		try {
			sf.getCurrentSession().beginTransaction();
			
			List branchList = (List) session.getAttribute("userBranchList");
			session.setAttribute("branchList", branchList);
			eform.setPbranchCode(Long.parseLong(userLogin.getBranchCode()));
			
			List provinceList = new ArrayList();
			ProvinceHome phome = new ProvinceHome();
			if(userLogin.getLevel()>2) {
				Province province = phome.searchByProvinceNo(userLogin.getProvinceNo());
				provinceList.add(province);
			} else if(userLogin.getLevel()>1)
				provinceList = phome.retrieveByRegionNo(userLogin.getRegionNo());
			else
				provinceList = phome.findAll();
			session.setAttribute("branchProvinceList", provinceList);
			
			List districtList = new ArrayList();
			//DistrictHome dHome = new DistrictHome();
			//districtList = dHome.findAll();
			District dis = new District();
			dis.setThaiName("กรุณาเลือก");
			districtList.add(0,dis);
			session.setAttribute("branchDistrictList", districtList);
			
			List subDistrictList = new ArrayList();
			//SubDistrictHome sHome = new SubDistrictHome();
			//subDistrictList = sHome.findAll();
			SubDistrict subDis = new SubDistrict();
			subDis.setThaiName("กรุณาเลือก");
			subDistrictList.add(0,subDis);
			session.setAttribute("branchSubDistrictList", subDistrictList);
			
			session.setAttribute("BranchForm", eform);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("branch");
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BranchForm eform = (BranchForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List<ProvinceDistrict> dtrcList = new ArrayList<ProvinceDistrict>();
			if(eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(eform.getProvinceNo());
			}

			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","subDistrictNo","provinceThai","provinceEng","subDistrictThai","subDistrictEng","postCode"});   

		    String result = JSONArray.fromObject(dtrcList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
		    wt.print(result);
		    wt.flush();
		    wt.close();
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}
	
	//Ajax Method
	public ActionForward getSubDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BranchForm eform = (BranchForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List<ProvinceDistrict> subList = new ArrayList<ProvinceDistrict>();
			if(eform.getDistrictNo() > 0 && eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(eform.getDistrictNo(), eform.getProvinceNo());
			}
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng"});   

		    String result = JSONArray.fromObject(subList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
		    wt.print(result);
		    wt.flush();
		    wt.close();
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}
	
	//Ajax Method
	public ActionForward getPostCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BranchForm eform = (BranchForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			ProvinceDistrict provinceDistrict = new ProvinceDistrict();
			if(eform.getDistrictNo()>0 && eform.getProvinceNo()>0 && eform.getSubDistrictNo()>0)
				provinceDistrict = home.getPostCode(eform.getDistrictNo(), eform.getProvinceNo(), eform.getSubDistrictNo() );
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","regionName","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng", "subDistrictThai", "subDistrictEng", "subDistrictNo"});   

		    String result = JSONArray.fromObject(provinceDistrict, jsonConfig).toString();
		    result = result.trim().replaceAll("&nbsp;", " ");
		    wt.print(result);
		    wt.flush();
		    wt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}
}
