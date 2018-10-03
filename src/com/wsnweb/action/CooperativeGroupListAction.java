package com.wsnweb.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import com.dcs.util.DCSCompare;
import com.dcs.util.GridUtil;
import com.wsndata.data.District;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.FarmerGroupChild;
import com.wsndata.data.Plant;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupChildHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;

import com.wsnweb.form.CooperativeGroupListForm;

public class CooperativeGroupListAction extends Action {
	private static final Logger log = Logger.getLogger(CooperativeGroupListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			 } else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List<?> getList = null;
		List<FarmerGroup> cooperativeGroupList = new ArrayList<FarmerGroup>();
		List<Province> provinceList = new ArrayList<Province>();
		List<District> districtList = new ArrayList<District>();
		List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
		String farmerGroupName= "", cooperativeType = "";
		long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0;
		try{
			FarmerGroupHome home = new FarmerGroupHome();
			ProvinceHome provHome = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();
			farmerGroupName = eform.getFarmerGroupName()==null?"":eform.getFarmerGroupName();
			cooperativeType = eform.getCooperativeType()==null?"":eform.getCooperativeType();
			provinceNo = eform.getProvinceNo();
			districtNo = eform.getDistrictNo();
			subDistrictNo = eform.getSubDistrictNo();

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

			getList=home.searchByCriteria("C", farmerGroupName, cooperativeType, regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, userLogin.getUserName(), 0);
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
					FarmerGroup farmerGroup = new FarmerGroup();
					Object[] obj = (Object[])getList.get(i);
					farmerGroup.setFarmerGroupId(Long.parseLong(obj[0].toString()));
					farmerGroup.setFarmerGroupName(obj[1].toString());
					if(obj[2]!=null)
						farmerGroup.setFarmerGroupType(obj[2].toString());
					else
						farmerGroup.setFarmerGroupType("-");
					if(obj[3]!=null || obj[4]!=null || obj[5]!=null) {
						farmerGroup.setProvinceName(obj[3].toString());
						farmerGroup.setDistrictName(obj[4].toString());
						farmerGroup.setSubDistrictName(obj[5].toString());
					}
					farmerGroup.setCountFarmer(Integer.parseInt(obj[6].toString(), 10));
					farmerGroup.setStrTarget(convertToDecimal(Double.parseDouble(obj[7].toString())/1000));
					farmerGroup.setTarget(Double.parseDouble(obj[7].toString()));
					cooperativeGroupList.add(farmerGroup);
				}
				Collections.sort(cooperativeGroupList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			
			session.setAttribute("cooperativeGroupMasterList", cooperativeGroupList);
			session.setAttribute("countCooperativeGroup", cooperativeGroupList!=null?cooperativeGroupList.size():0);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("cooperativeGroupList");
	}

	private String convertToDecimal(double target){
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern("###,###.00");
		if(target<1) formatter.applyPattern("0.00");
		String targetStr = formatter.format(target);
		return targetStr;
		
	}
	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
        HttpSession session = request.getSession();
        List cooperativeGroupList = (List) session.getAttribute("cooperativeGroupMasterList");
      
        Collections.sort(cooperativeGroupList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
        GridUtil.applyCheckValue(cooperativeGroupList, eform.getDelfarmerGroupName(), "farmerGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        
        return mapping.findForward("cooperativeGroupList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
		HttpSession session = request.getSession();
		List cooperativeGroupList = (List) session.getAttribute("cooperativeGroupMasterList");
		GridUtil.applyCheckValue(cooperativeGroupList, eform.getDelfarmerGroupName(), "farmerGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try{
			sf.getCurrentSession().beginTransaction();
			PlantHome pHome = new PlantHome(); //Plant
			FarmerGroupChildHome fgHome = new FarmerGroupChildHome(); //FarmerGroupChild
			FarmerGroupHome home = new FarmerGroupHome();
			for (int i=0; i<cooperativeGroupList.size(); i++) {
				FarmerGroup farmerGroup = (FarmerGroup)cooperativeGroupList.get(i);
				if (farmerGroup.getCheckBox()==true){

					List<FarmerGroupChild> farmerGCList = fgHome.findByChildFarmerGroupId(farmerGroup.getFarmerGroupId());
					if (farmerGCList !=null && farmerGCList.size() > 0) {
						eform.setErrMessage(farmerGroup.getFarmerGroupName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					List<Plant> uPltList = pHome.findByFamerGroupId2(farmerGroup.getFarmerGroupId());
					if (uPltList !=null && uPltList.size() > 0) {
						eform.setErrMessage(farmerGroup.getFarmerGroupName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					else{
						home.deleteFarmerGroup(farmerGroup.getFarmerGroupId());
					}
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบสหกรณ์ไปทั้งสิ้น " + eform.getDelfarmerGroupName().length + " records");
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
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
		CooperativeGroupListForm eform = (CooperativeGroupListForm)form;
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
}
