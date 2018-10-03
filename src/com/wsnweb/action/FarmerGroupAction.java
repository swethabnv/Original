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
import com.wsndata.data.FarmerGroupChild;
import com.wsndata.data.FarmerGroupTeam;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupAddressHome;
import com.wsndata.dbaccess.FarmerGroupChildHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.FarmerGroupTeamHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.FarmerGroupForm;
import com.wsnweb.util.Utility;

public class FarmerGroupAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerGroupAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		FarmerGroupForm eform = (FarmerGroupForm) form;
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
		long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0;
		try{
			FarmerGroupHome home = new FarmerGroupHome();
			ProvinceHome provHome = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();

			userLevel = userLogin.getLevel();
			farmerGroupId = userLogin.getFarmerGroupId();
			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();
			
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
			//cooperativeList = home.findByFarmerGroupType("C");
			cooperativeList = home.searchByAnauthorize("C", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId);
			cooperativeList.add(0,coop);
			
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("cooperativeList", cooperativeList);
			session.setAttribute("farmerGroupTeamList", null);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("farmerGroup");
	}

	private String convertToDecimal(double target){
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern("###,###.00");
		String targetStr = formatter.format(target);
		return targetStr;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmerGroupForm eform = (FarmerGroupForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try{			
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			FarmerGroup farmerGroupObj = home.findByFarmerGroupId(eform.getFarmerGroupId());
			if(farmerGroupObj!=null){  //Edit Case
				//เช็คซ้ำ
				FarmerGroup haveObj = home.findByFarmerGroupNameAndAddress(eform.getFarmerGroupName(),eform.getAddressNo(),
						eform.getSubDistrictNo(),eform.getDistrictNo(),eform.getProvinceNo(),"F");
				if (haveObj != null && eform.getFarmerGroupId()!=haveObj.getFarmerGroupId()) {
					//if(false) {
					msg = Utility.get("SAVE_DUPLICATE"); // "มีข้อมูลนี้อยู่แล้ว";
				} else {

					farmerGroupObj.setFarmerGroupId(eform.getFarmerGroupId());
					eform.loadToBean(farmerGroupObj);
					//farmerGroupObj.setFarmerGroupType("F");
					farmerGroupObj.setLastUpdateBy(userLogin.getUserName());
					farmerGroupObj.setLastUpdateDate(new Date());
					
					FarmerGroupChildHome fcHome = new FarmerGroupChildHome();
					fcHome.deleteChildByFarmerGroupId(eform.getFarmerGroupId());
		            if(eform.getChildFarmerGroupId() > 0){
			            fcHome.saveFarmerGroupChild(eform.getFarmerGroupId(), eform.getChildFarmerGroupId());
		            }

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
		            		FarmerGroupTeam farmerGroupTeamObj = null;
		            		if(teamId[i].equals("")) {
		            			farmerGroupTeamObj = new FarmerGroupTeam();
		            		} else {
		            			farmerGroupTeamObj = ftHome.findByFarmerGroupTeamId(Long.parseLong(teamId[i]));
		            		}
		    				String[] name = teamName[i].split(" ",2);
		    				farmerGroupTeamObj.setFirstName(name[0]);
		    				farmerGroupTeamObj.setLastName(name[1]);
		    				farmerGroupTeamObj.setPosition(teamPosition[i]);
		            		
		            		if(teamId[i].equals("")) {
		            			ftHome.saveFarmerGroupTeam(name[0], name[1], teamPosition[i], eform.getFarmerGroupId());
		            		} else {
		            			ftHome.update(farmerGroupTeamObj);
		            		}
		            	}
		            }
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");

					home.update(farmerGroupObj);
				}
			}else{  //Add New Case
				// create New FarmerGroup
				FarmerGroup haveObj = home.findByFarmerGroupNameAndAddress(eform.getFarmerGroupName(),eform.getAddressNo(),
						eform.getSubDistrictNo(),eform.getDistrictNo(),eform.getProvinceNo(),"F");
				if (haveObj != null) {
					//if(false) {
					msg = Utility.get("SAVE_DUPLICATE"); // "มีข้อมูลนี้อยู่แล้ว";
				}else{
					farmerGroupObj = new FarmerGroup();
					eform.loadToBean(farmerGroupObj);
					farmerGroupObj.setFarmerGroupType("F");
					farmerGroupObj.setLastUpdateBy(userLogin.getUserName());
					farmerGroupObj.setLastUpdateDate(new Date());
					farmerGroupObj.setCreateBy(userLogin.getUserName());
					farmerGroupObj.setCreateDate(new Date());
					
					Set<FarmerGroupAddress> farmerGroupAddressSet = new HashSet<FarmerGroupAddress>();
		            if(eform.getSubDistrictNo() !=0){
		            	SubDistrictHome sHome = new SubDistrictHome();
		            	SubDistrict subDistrict = sHome.findBySubDistrictNo(eform.getSubDistrictNo());
		            	FarmerGroupAddress farmerGroupAddress = new FarmerGroupAddress();
		            	farmerGroupAddress.setRegionNo(subDistrict.getRegionNo());
		            	farmerGroupAddress.setProvinceNo(subDistrict.getProvinceNo());
		            	farmerGroupAddress.setDistrictNo(subDistrict.getDistrictNo());
		            	farmerGroupAddress.setSubDistrictNo(subDistrict.getSubDistrictNo());
		            	farmerGroupAddress.setFarmerGroup(farmerGroupObj);
		            	farmerGroupAddressSet.add(farmerGroupAddress);
		            }
		            farmerGroupObj.setFarmerGroupAddress(farmerGroupAddressSet);
		            
		            if(eform.getChildFarmerGroupId() > 0){
						Set<FarmerGroupChild> farmerGroupChildSet = new HashSet<FarmerGroupChild>();
						FarmerGroupChild farmerGroupChild = new FarmerGroupChild();
						farmerGroupChild.setChildFarmerGroupId(eform.getChildFarmerGroupId());
						farmerGroupChild.setFarmerGroup(farmerGroupObj);
						farmerGroupChildSet.add(farmerGroupChild);
			            farmerGroupObj.setFarmerGroupChild(farmerGroupChildSet);
		            }

		    		String[] teamName = eform.getFarmerGroupTeamName();
		    		String[] teamPosition = eform.getFarmerGroupTeamPosition();
		    		if((teamName !=null && teamName.length>0) &&  (teamPosition !=null && teamPosition.length>0)){
		    			List<FarmerGroupTeam> farmerGroupTeamList = new ArrayList();
		    			for(int i=0;i<teamName.length;i++) {
		    				FarmerGroupTeam farmerGroupTeam = new FarmerGroupTeam();
		    				String[] name = teamName[i].split(" ",2);
		    				farmerGroupTeam.setFirstName(name[0]);
		    				farmerGroupTeam.setLastName(name[1]);
		    				farmerGroupTeam.setPosition(teamPosition[i]);
		    				farmerGroupTeam.setFarmerGroup(farmerGroupObj);
		    				farmerGroupTeamList.add(farmerGroupTeam);
		            	}
		    			farmerGroupObj.setFarmerGroupTeam(farmerGroupTeamList);
		    		}
		    		
					//home.saveOrUpdate(farmerGroupObj);	  childFarmerGroupId
					home.persist(farmerGroupObj);
					
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(Utility.get("SAVE_FAIL"));
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("farmerGroup");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmerGroupForm eform = (FarmerGroupForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List farmerGroupTeamList = new ArrayList();
		FarmerGroup farmerGroupList = new FarmerGroup();
		try{			
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			FarmerGroupTeamHome thome = new FarmerGroupTeamHome();
			farmerGroupList = home.findByFarmerGroupId(eform.getFarmerGroupId());
			
			eform.loadFromBean(farmerGroupList);

			Set<FarmerGroupAddress> farmerGroupAddressSet = farmerGroupList.getFarmerGroupAddress();
            List<FarmerGroupAddress> farmAddressList = new ArrayList<FarmerGroupAddress>(farmerGroupAddressSet);
            for(int i=0; i< farmAddressList.size(); i++){
            	FarmerGroupAddress farmAddress = (FarmerGroupAddress)farmAddressList.get(i);
            	eform.setProvinceNo(farmAddress.getProvinceNo());
            	eform.setDistrictNo(farmAddress.getDistrictNo());
            	eform.setSubDistrictNo(farmAddress.getSubDistrictNo());
            }

			Set<FarmerGroupChild> farmerGroupChildSet = farmerGroupList.getFarmerGroupChild();
			if(farmerGroupChildSet!=null) {
	            List<FarmerGroupChild> farmChildList = new ArrayList<FarmerGroupChild>(farmerGroupChildSet);
	            for(int i=0; i< farmChildList.size(); i++){
	            	FarmerGroupChild farmTeam = (FarmerGroupChild)farmChildList.get(i);
	    			eform.setChildFarmerGroupId(farmTeam.getChildFarmerGroupId());
	            }
			}
            
            farmerGroupTeamList = thome.getByFarmerGroupId(eform.getFarmerGroupId());
			session.setAttribute("farmerGroupTeamList", farmerGroupTeamList);
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("farmerGroup");
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		FarmerGroupForm eform = (FarmerGroupForm)form;
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
		FarmerGroupForm eform = (FarmerGroupForm)form;
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
		FarmerGroupForm eform = (FarmerGroupForm)form;
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
