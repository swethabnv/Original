package com.wsnweb.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.FarmerGroupAddress;
import com.wsndata.data.FarmerGroupTeam;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupAddressHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.FarmerGroupTeamHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.CooperativeGroupForm;
import com.wsnweb.util.Utility;

public class CooperativeGroupAction extends Action {
	private static final Logger log = Logger.getLogger(CooperativeGroupAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		CooperativeGroupForm eform = (CooperativeGroupForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())){ 
				return save(mapping, form, request, response);
			} else if( "Edit".equals(eform.getCmd()) ){
				load(mapping, form, request, response);
				return edit(mapping, form, request, response);
			} else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			} else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			} else if ("GetPostCode".equals(eform.getCmd())) {
				 return getPostCode(mapping, eform, request, response);
			} else{
				 return load(mapping, form, request, response);
			}
		}
	}

	private ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		List cooperativeList = new ArrayList();
		try{
			FarmerGroupHome home = new FarmerGroupHome();
			ProvinceHome provHome = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();
			
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
			
			//--- ดึงสหกรณ์ ออกมา --- //
			FarmerGroup coop=new FarmerGroup();
			coop.setFarmerGroupId(0);
			coop.setFarmerGroupName("กรุณาเลือก");
			cooperativeList = home.findByFarmerGroupType("C");
			if(cooperativeList !=null && cooperativeList.size()>0)
				cooperativeList.add(0,coop);
			
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("cooperativeList", cooperativeList);
			session.setAttribute("cooperativeGroupTeamList", null);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("cooperativeGroup");
	}

	private String convertToDecimal(double target){
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern("###,###.00");
		String targetStr = formatter.format(target);
		return targetStr;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CooperativeGroupForm eform = (CooperativeGroupForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try{			
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			FarmerGroup cooperativeGroupObj = home.findByFarmerGroupId(eform.getFarmerGroupId());
			if(cooperativeGroupObj!=null){  //Edit Case
				//เช็คซ้ำ
				FarmerGroup haveObj = home.findByFarmerGroupNameAndAddress(eform.getFarmerGroupName(),eform.getAddressNo(),
						eform.getSubDistrictNo(),eform.getDistrictNo(),eform.getProvinceNo(),"C");
				if (haveObj != null && eform.getFarmerGroupId()!=haveObj.getFarmerGroupId()) {
					//if(false) {
					msg = Utility.get("SAVE_DUPLICATE"); // "มีข้อมูลนี้อยู่แล้ว";
				} else {
					cooperativeGroupObj.setFarmerGroupId(eform.getFarmerGroupId());
					eform.loadToBean(cooperativeGroupObj);
					cooperativeGroupObj.setLastUpdateBy(userLogin.getUserName());
					cooperativeGroupObj.setLastUpdateDate(new Date());

	            	FarmerGroupAddressHome faHome = new FarmerGroupAddressHome();
	            	faHome.deleteAddressByFarmerGroupId(eform.getFarmerGroupId());
		            if(eform.getSubDistrictNo() !=0){
		            	SubDistrictHome sHome = new SubDistrictHome();
		            	SubDistrict subDistrict = sHome.findBySubDistrictNo(eform.getSubDistrictNo());
		            	faHome.saveFarmerGroupAddress(eform.getFarmerGroupId(),subDistrict.getRegionNo(),subDistrict.getProvinceNo(),subDistrict.getDistrictNo(),subDistrict.getSubDistrictNo());
		            }

	            	FarmerGroupTeamHome ftHome = new FarmerGroupTeamHome();
		    		String[] delTeamId = eform.getDelTeamId();
		    		if(delTeamId!=null) {
		    			for(int i=0;i<delTeamId.length;i++) {
			    			ftHome.deleteFarmerGroupTeam(Long.parseLong(delTeamId[i]));
		    			}
		    		}
		    		String[] teamId = eform.getFarmerGroupTeamId();
		    		String[] teamName = eform.getFarmerGroupTeamName();
		    		String[] teamPosition = eform.getFarmerGroupTeamPosition();
		            if(teamName!=null && teamPosition!=null){
		            	for(int i=0;i<teamName.length;i++) {
		            		FarmerGroupTeam cooperativeGroupTeamObj = null;
		            		if(teamId[i].equals("")) {
		            			cooperativeGroupTeamObj = new FarmerGroupTeam();
		            		} else {
		            			cooperativeGroupTeamObj = ftHome.findByFarmerGroupTeamId(Long.parseLong(teamId[i]));
		            		}
		    				String[] name = teamName[i].split(" ",2);
		    				cooperativeGroupTeamObj.setFirstName(name[0]);
		    				cooperativeGroupTeamObj.setLastName(name[1]);
		    				cooperativeGroupTeamObj.setPosition(teamPosition[i]);
		            		
		            		if(teamId[i].equals("")) {
		            			ftHome.saveFarmerGroupTeam(name[0], name[1], teamPosition[i], eform.getFarmerGroupId());
		            		} else {
		            			ftHome.update(cooperativeGroupTeamObj);
		            		}
		            	}
		            }
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");

					home.update(cooperativeGroupObj);
				}
			}else{  //Add New Case
				// create New FarmerGroup
				FarmerGroup haveObj = home.findByFarmerGroupNameAndAddress(eform.getFarmerGroupName(),eform.getAddressNo(),
						eform.getSubDistrictNo(),eform.getDistrictNo(),eform.getProvinceNo(),"C");
				if (haveObj != null) {
					//if(false) {
					msg = Utility.get("SAVE_DUPLICATE"); // "มีข้อมูลนี้อยู่แล้ว";
				}else{
					cooperativeGroupObj = new FarmerGroup();
					eform.loadToBean(cooperativeGroupObj);
					cooperativeGroupObj.setFarmerGroupType("C");
					cooperativeGroupObj.setLastUpdateBy(userLogin.getUserName());
					cooperativeGroupObj.setLastUpdateDate(new Date());
					cooperativeGroupObj.setCreateBy(userLogin.getUserName());
					cooperativeGroupObj.setCreateDate(new Date());
					
					Set<FarmerGroupAddress> cooperativeGroupAddressSet = new HashSet<FarmerGroupAddress>();
		            if(eform.getSubDistrictNo() !=0){
		            	SubDistrictHome sHome = new SubDistrictHome();
		            	SubDistrict subDistrict = sHome.findBySubDistrictNo(eform.getSubDistrictNo());
		            	FarmerGroupAddress cooperativeGroupAddress = new FarmerGroupAddress();
		            	cooperativeGroupAddress.setRegionNo(subDistrict.getRegionNo());
		            	cooperativeGroupAddress.setProvinceNo(subDistrict.getProvinceNo());
		            	cooperativeGroupAddress.setDistrictNo(subDistrict.getDistrictNo());
		            	cooperativeGroupAddress.setSubDistrictNo(subDistrict.getSubDistrictNo());
		            	cooperativeGroupAddress.setFarmerGroup(cooperativeGroupObj);
		            	cooperativeGroupAddressSet.add(cooperativeGroupAddress);
		            }
		            cooperativeGroupObj.setFarmerGroupAddress(cooperativeGroupAddressSet);

		    		String[] teamName = eform.getFarmerGroupTeamName();
		    		String[] teamPosition = eform.getFarmerGroupTeamPosition();
		    		if( (teamName !=null && teamName.length>0) &&  (teamPosition !=null && teamPosition.length>0)){
		    			List<FarmerGroupTeam> cooperativeGroupTeamList = new ArrayList();
		    			for(int i=0;i<teamName.length;i++) {
		    				FarmerGroupTeam cooperativeGroupTeam = new FarmerGroupTeam();
		    				String[] name = teamName[i].split(" ",2);
		    				cooperativeGroupTeam.setFirstName(name[0]);
		    				cooperativeGroupTeam.setLastName(name[1]);
		    				cooperativeGroupTeam.setPosition(teamPosition[i]);
		    				cooperativeGroupTeam.setFarmerGroup(cooperativeGroupObj);
		    				cooperativeGroupTeamList.add(cooperativeGroupTeam);
		            	}
		    			cooperativeGroupObj.setFarmerGroupTeam(cooperativeGroupTeamList);
		    		}
		    		
					//home.saveOrUpdate(cooperativeGroupObj);	  childFarmerGroupId
					home.persist(cooperativeGroupObj);
					
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("cooperativeGroup");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CooperativeGroupForm eform = (CooperativeGroupForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List cooperativeGroupTeamList = new ArrayList();
		FarmerGroup cooperativeGroupList = new FarmerGroup();
		try{			
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			FarmerGroupTeamHome thome = new FarmerGroupTeamHome();
			cooperativeGroupList = home.findByFarmerGroupId(eform.getFarmerGroupId());
			
			eform.loadFromBean(cooperativeGroupList);

			Set<FarmerGroupAddress> cooperativeGroupAddressSet = cooperativeGroupList.getFarmerGroupAddress();
            List<FarmerGroupAddress> farmAddressList = new ArrayList<FarmerGroupAddress>(cooperativeGroupAddressSet);
            for(int i=0; i< farmAddressList.size(); i++){
            	FarmerGroupAddress farmAddress = (FarmerGroupAddress)farmAddressList.get(i);
            	eform.setProvinceNo(farmAddress.getProvinceNo());
            	eform.setDistrictNo(farmAddress.getDistrictNo());
            	eform.setSubDistrictNo(farmAddress.getSubDistrictNo());
            }
            
            cooperativeGroupTeamList = thome.getByFarmerGroupId(eform.getFarmerGroupId());
			session.setAttribute("cooperativeGroupTeamList", cooperativeGroupTeamList);
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("cooperativeGroup");
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		CooperativeGroupForm eform = (CooperativeGroupForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
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
		CooperativeGroupForm eform = (CooperativeGroupForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List subList = new ArrayList();
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
		CooperativeGroupForm eform = (CooperativeGroupForm)form;
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
