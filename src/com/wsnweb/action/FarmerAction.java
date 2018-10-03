package com.wsnweb.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.wsndata.data.District;
import com.wsndata.data.Farmer;
import com.wsndata.data.Prefix;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.PrefixHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.FarmerForm;
import com.wsnweb.util.Utility;


public class FarmerAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		FarmerForm eform = (FarmerForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 load(mapping, eform, request, response);
				 return edit(mapping, form, request, response);
			 } else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			 } else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			 } else if ("GetPostCode".equals(eform.getCmd())) {
				 return getPostCode(mapping, eform, request, response);
			 } else {
				 return load(mapping, eform, request, response);
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmerForm eform = (FarmerForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerHome home = new FarmerHome();
		ProvinceHome phome = new ProvinceHome();
		String msg = Utility.get("SAVE_FAIL");
		try{			
			sf.getCurrentSession().beginTransaction();			
			
			Farmer  fObj = new Farmer();
			eform.loadToBean(fObj);
			fObj.setEffectiveDate(new Date());
			fObj.setRegionNo(phome.searchByProvinceNo(eform.getProvinceNo()).getRegionNo());				
			fObj.setCreateBy(userLogin.getUserName());
			fObj.setCreateDate(new Date());
			
			home.saveOrUpdate(fObj);	  
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));
		}catch (Exception e) {
			eform.setMsg(msg);
			e.printStackTrace();
			sf.getCurrentSession().getTransaction().rollback();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("farmer");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmerForm eform = (FarmerForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
		try{			
			sf.getCurrentSession().beginTransaction();
			FarmerHome home = new FarmerHome();
			
			Farmer fObj = home.findByKey(eform.getIdCard(),df.parse(eform.getEffectiveDate()));
			eform.loadFromBean(fObj);
		
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));
			e.printStackTrace();
			sf.getCurrentSession().getTransaction().rollback();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("farmer");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		try{			
			sf.getCurrentSession().beginTransaction();
			PrefixHome prefHome = new PrefixHome();
			ProvinceHome provHome = new ProvinceHome();
			
			List<Prefix> prefixList = new ArrayList<Prefix>();
			List<Province> provinceList = new ArrayList<Province>();
			List<District> districtList = new ArrayList<District>();
			List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
			
			prefixList = prefHome.findAll();

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
			
			District dis = new District();
			dis.setDistrictNo(0);
			dis.setThaiName("กรุณาเลือก");
			districtList.add(0,dis);
			
			SubDistrict subDis = new SubDistrict();
			subDis.setSubDistrictNo(0);
			subDis.setThaiName("กรุณาเลือก");
			subDistrictList.add(0,subDis);
			
			session.setAttribute("prefixList", prefixList);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			
		}catch (Exception e) {
			e.printStackTrace();
			sf.getCurrentSession().getTransaction().rollback();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("farmer");
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		FarmerForm eform = (FarmerForm)form;
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
	public ActionForward getSubDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		FarmerForm eform = (FarmerForm)form;
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
	public ActionForward getPostCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerForm eform = (FarmerForm)form;
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
	
	
	      
	
}
