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
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.Buyer;
import com.wsndata.data.District;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.BuyerForm;
import com.wsnweb.util.Utility;

public class BuyerAction extends Action {
	private static final Logger log = Logger.getLogger(BuyerAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerForm eform = (BuyerForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return save(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				return edit(mapping, form, request, response);
			} else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			} else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			 } else if ("GetPostCode".equals(eform.getCmd())) {
				 return getPostCode(mapping, eform, request, response);
			} else if ("GetDistrict2".equals(eform.getCmd())) {
				 return getDistrictInfo2(mapping, eform, request, response);
			} else if ("GetSubDistrict2".equals(eform.getCmd())) {
				 return getSubDistrictInfo2(mapping, eform, request, response);
			 } else if ("GetPostCode2".equals(eform.getCmd())) {
				 return getPostCode2(mapping, eform, request, response);
			 } else if ("GetBreedGroup".equals(eform.getCmd())){
				 return getBreedGroupInfo(mapping, eform, request, response);
			} else {
				return load(mapping, form, request, response);
			}
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerForm eform = (BuyerForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			BuyerHome home = new BuyerHome();
			ProvinceHome phome = new ProvinceHome();
			Buyer Obj = home.findByBuyerId(eform.getBuyerId());
			if (Obj != null) {
				if (!Obj.getBuyerName().equals(eform.getBuyerName())) {
					Buyer haveObj = home.findByBuyer(eform.getBuyerName(),eform.getProvinceNo(), eform.getDistrictNo(), eform.getSubDistrictNo(), eform.getAddressNo());
					if (haveObj != null) {
						sf.getCurrentSession().getTransaction().commit();
						msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
						eform.setCmd("Edit");
						eform.setMsg(msg);
						return mapping.findForward("buyer");
					}
				}
				Obj.setRegionNo(phome.searchByProvinceNo(eform.getProvinceNo()).getRegionNo());
				Obj.setPlaceRegionNo(phome.searchByProvinceNo(eform.getPlaceProvinceNo()).getRegionNo());
				Obj.setLastUpdateBy(userLogin.getUserName());
				Obj.setLastUpdateDate(new Date());
				/*Obj.setBreedTypeId(("".equals(eform.getBreedTypeId()))?0:eform.getBreedTypeId());
				Obj.setBreedGroupId(("".equals(eform.getBreedGroupId()))?0:eform.getBreedGroupId());
				Obj.setQualification(eform.getQualification());
				Obj.setQuantity(("".equals(eform.getQuantity()))?"0.00":eform.getQuantity());
				Obj.setPaymentCondition(eform.getPaymentCondition());*/

			} else {
				// create New Buyer
				Obj = home.findByBuyer(eform.getBuyerName(),eform.getProvinceNo(), eform.getDistrictNo(), eform.getSubDistrictNo(), eform.getAddressNo());
				if (Obj != null) {
					sf.getCurrentSession().getTransaction().commit();
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					eform.setMsg(msg);
					return mapping.findForward("buyer");
				}
				Obj = new Buyer();
				Obj.setRegionNo(phome.searchByProvinceNo(eform.getProvinceNo()).getRegionNo());
				Obj.setPlaceRegionNo(phome.searchByProvinceNo(eform.getPlaceProvinceNo()).getRegionNo());
				Obj.setLastUpdateBy(userLogin.getUserName());
				Obj.setLastUpdateDate(new Date());
				/*Obj.setQuantity(eform.getQuantity());
				Obj.setBreedTypeId(("".equals(eform.getBreedTypeId()))?0:eform.getBreedTypeId());
				Obj.setBreedGroupId(("".equals(eform.getBreedGroupId()))?0:eform.getBreedGroupId());
				Obj.setQualification(eform.getQualification());
				Obj.setPaymentCondition(eform.getPaymentCondition());*/
			}
			eform.loadToBean(Obj);
			home.saveOrUpdate(Obj);
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL"));
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("buyer");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		load(mapping, form, request, response);
		BuyerForm eform = (BuyerForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		List<Province> provinceList = new ArrayList<Province>();
		List<District> districtList = new ArrayList<District>();
		List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
		try {
			sf.getCurrentSession().beginTransaction();
			BuyerHome home = new BuyerHome();
			ProvinceHome provHome = new ProvinceHome();
			DistrictHome distHome = new DistrictHome();
			SubDistrictHome subdHome = new SubDistrictHome();
			ProvinceDistrictHome ddHome = new ProvinceDistrictHome();
			
			Buyer Obj = home.findByBuyerId(eform.getBuyerId());
			eform.loadFromBean(Obj);
			long level = userLogin.getLevel();

			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			ProvinceDistrict pd = ddHome.getPostCode(eform.getPlaceDistrictNo(),eform.getPlaceProvinceNo(),eform.getPlaceSubDistrictNo());
			long placeRegionNo = pd.getRegionNo();
			if(level<2)
				provinceList = provHome.findAll();
			else if(level==2 && userLogin.getRegionNo()==placeRegionNo)
				provinceList = provHome.retrieveByRegionNo(userLogin.getRegionNo());
			else {
				Province province = provHome.searchByProvinceNo(eform.getPlaceProvinceNo());
				provinceList.add(province);
			}
			if(provinceList !=null && provinceList.size()>0)
				provinceList.add(0,prov);
			//--- ดึงอำเภอ ออกมา --- //
			District district = new District();
			district.setDistrictNo(0);
			district.setProvinceNo(0);
			district.setThaiName("กรุณาเลือก");
			if(level==3 && userLogin.getProvinceNo()==eform.getPlaceProvinceNo())
				districtList = distHome.findByProvinceNo(eform.getPlaceProvinceNo());
			else {
				District dist = distHome.findByDistrictNo(eform.getPlaceDistrictNo());
				districtList.add(dist);
			}
			districtList.add(0,district);
			//--- ดึงตำบล ออกมา --- //
			SubDistrict subDistrict = new SubDistrict();
			subDistrict.setProvinceNo(0);
			subDistrict.setDistrictNo(0);
			subDistrict.setSubDistrictNo(0);
			subDistrict.setThaiName("กรุณาเลือก");
			if(level==4 && userLogin.getDistrictNo()==eform.getPlaceDistrictNo())
				subDistrictList = subdHome.findByDistrictNo(eform.getPlaceDistrictNo());
			else {
				SubDistrict subDist = subdHome.findBySubDistrictNo(eform.getPlaceSubDistrictNo());
				subDistrictList.add(subDist);
			}
			subDistrictList.add(0,subDistrict);

			session.setAttribute("placeProvinceList", provinceList);
			session.setAttribute("placeDistrictList", districtList);
			session.setAttribute("placeSubDistrictList", subDistrictList);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //Load Data failed.
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("buyer");
	}

	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerForm eform = (BuyerForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List<Province> provinceList = new ArrayList<Province>();
		List<District> districtList = new ArrayList<District>();
		List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
		List<BreedType> breedTypeList = new ArrayList<BreedType>();
		List<BreedGroup> breedGroupList = new ArrayList<BreedGroup>();
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			ProvinceHome provHome = new ProvinceHome();
			BreedTypeHome breedTypeHome = new BreedTypeHome();

			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			if(userLogin.getLevel()>2) {
				Province province = provHome.searchByProvinceNo(userLogin.getProvinceNo());
				provinceList.add(province);
			} else if(userLogin.getLevel()>1)
				provinceList = provHome.retrieveByRegionNo(userLogin.getRegionNo());
			else
				provinceList = provHome.findAll();
			if(provinceList !=null && provinceList.size()>0)
				provinceList.add(0,prov);
			//--- ดึงอำเภอ ออกมา --- //
			District district = new District();
			district.setDistrictNo(0);
			district.setProvinceNo(0);
			district.setThaiName("กรุณาเลือก");
			districtList.add(0,district);
			//--- ดึงตำบล ออกมา --- //
			SubDistrict subDistrict = new SubDistrict();
			subDistrict.setProvinceNo(0);
			subDistrict.setDistrictNo(0);
			subDistrict.setSubDistrictNo(0);
			subDistrict.setThaiName("กรุณาเลือก");
			subDistrictList.add(0,subDistrict);

			//--- ดึงชนิดพืช ออกมา --- //
			BreedType breedType = new BreedType();
			breedType.setBreedTypeId(0);
			breedType.setBreedTypeName("กรุณาเลือก");
			breedTypeList = breedTypeHome.findAll();
			if(breedTypeList !=null && breedTypeList.size()>0)
				breedTypeList.add(0,breedType);
			//--- ดึงพันธุ์พืช ออกมา --- //
			BreedGroup breedGroup = new BreedGroup();
			breedGroup.setBreedGroupId(0);
			breedGroup.setBreedGroupName("กรุณาเลือก");
			breedGroupList.add(0,breedGroup);
			
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("placeProvinceList", provinceList);
			session.setAttribute("placeDistrictList", districtList);
			session.setAttribute("placeSubDistrictList", subDistrictList);
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("breedGroupPlantList", breedGroupList);

		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //Load Data failed.
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("buyer");
	}

	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BuyerForm eform = (BuyerForm)form;
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
		    result = result.trim().replaceAll(" ", "&nbsp;");
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
		BuyerForm eform = (BuyerForm)form;
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
		    result = result.trim().replaceAll(" ", "&nbsp;");
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
	public ActionForward getDistrictInfo2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BuyerForm eform = (BuyerForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List<ProvinceDistrict> dtrcList = new ArrayList<ProvinceDistrict>();
			if(eform.getPlaceProvinceNo() > 0) {
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(eform.getPlaceProvinceNo());
			}
		    
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","subDistrictNo","provinceThai","provinceEng","subDistrictThai","subDistrictEng","postCode"});   

		    String result = JSONArray.fromObject(dtrcList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", "&nbsp;");
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
	public ActionForward getSubDistrictInfo2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BuyerForm eform = (BuyerForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List<ProvinceDistrict> subList = new ArrayList<ProvinceDistrict>();
			if(eform.getPlaceDistrictNo() > 0 && eform.getPlaceProvinceNo() > 0) {
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(eform.getPlaceDistrictNo(), eform.getPlaceProvinceNo());
			}
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng"});   

		    String result = JSONArray.fromObject(subList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", "&nbsp;");
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
		BuyerForm eform = (BuyerForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			ProvinceDistrict provinceDistrict = new ProvinceDistrict();
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo()) &&  !"".equals(eform.getSubDistrictNo()))
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
	
	//Ajax Method
	public ActionForward getPostCode2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BuyerForm eform = (BuyerForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			ProvinceDistrict provinceDistrict = new ProvinceDistrict();
			if(!"".equals(eform.getPlaceDistrictNo()) && !"".equals(eform.getPlaceProvinceNo()) &&  !"".equals(eform.getPlaceSubDistrictNo()))
				provinceDistrict = home.getPostCode(eform.getPlaceDistrictNo(), eform.getPlaceProvinceNo(), eform.getPlaceSubDistrictNo() );
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
	
	public ActionForward getBreedGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BuyerForm eform = (BuyerForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();		
			List<BreedGroup> breedGroup = null;
			if(!"".equals(eform.getBreedTypeId()))
				breedGroup = home.findByBreedTypeId(eform.getBreedTypeId());
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"breedTypeId", "period", "plantPeriodFrom", "plantPeriodTo", "forcastPeriodFrom", "forcastPeriodTo", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});
		    String result = JSONArray.fromObject(breedGroup, jsonConfig).toString();
		    result = result.trim().replaceAll("&nbsp;", " ");
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
	
}
