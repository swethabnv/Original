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
import com.wsndata.data.Plant;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;

import com.wsnweb.form.FarmerGroupListForm;

public class FarmerGroupListAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerGroupListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else if("GetSubDistrict".equals(eform.getCmd())){
				 return getSubDistrictInfo(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List<?> getList = null;
		List<?> getCooperativeList = null;
		List<FarmerGroup> farmerGroupList = new ArrayList<FarmerGroup>();
		List<Province> provinceList = new ArrayList<Province>();
		List<District> districtList = new ArrayList<District>();
		List<SubDistrict> subDistrictList = new ArrayList<SubDistrict>();
		List<FarmerGroup> cooperativeList = new ArrayList<FarmerGroup>();
		String farmerGroupName= "", cooperativeType = "";
		long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0, cooperativeGroupId = 0;
		try{
			FarmerGroupHome home = new FarmerGroupHome();
			ProvinceHome provHome = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();
			farmerGroupName = eform.getFarmerGroupName()==null?"":eform.getFarmerGroupName();
			cooperativeType = eform.getCooperativeType()==null?"":eform.getCooperativeType();
			if(eform.getFarmerGroupId()!=null)
				cooperativeGroupId = Long.parseLong("".equals(eform.getFarmerGroupId())?"0":eform.getFarmerGroupId());
			if(eform.getProvinceNo()!=null)
				provinceNo = Long.parseLong("".equals(eform.getProvinceNo())?"0":eform.getProvinceNo());
			if(eform.getDistrictNo()!=null)
				districtNo = Long.parseLong("".equals(eform.getDistrictNo())?"0":eform.getDistrictNo());
			if(eform.getSubDistrictNo()!=null)
				subDistrictNo = Long.parseLong("".equals(eform.getSubDistrictNo())?"0":eform.getSubDistrictNo());

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
			
			FarmerGroup cooperateGroup = new FarmerGroup();
			cooperateGroup.setFarmerGroupId(0);
			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
			cooperativeList.add(0,cooperateGroup);
			
//			if("กรุณาเลือก".equals(cooperativeName)){
//				cooperateGroup.setFarmerGroupName("");
//				cooperativeName = cooperateGroup.getFarmerGroupName();
//			}

				getList=home.searchByCriteria("F", farmerGroupName, cooperativeType, regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, userLogin.getUserName(), cooperativeGroupId);

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
					farmerGroupList.add(farmerGroup);
				}
				Collections.sort(farmerGroupList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			
//			cooperateGroup.setFarmerGroupName("");
//			cooperativeName = cooperateGroup.getFarmerGroupName();
			
			getCooperativeList = home.searchByCriteria("C", farmerGroupName, cooperativeType, userLogin.getRegionNo(), userLogin.getProvinceNo(), userLogin.getDistrictNo(), userLogin.getSubDistrictNo(), farmerGroupId, userLogin.getUserName(), 0);
			
			if(getCooperativeList!=null && getCooperativeList.size()>0){
				for(int i=0; i< getCooperativeList.size(); i++){
					FarmerGroup farmerGroup = new FarmerGroup();
					Object[] obj = (Object[])getCooperativeList.get(i);
					farmerGroup.setFarmerGroupId(Long.parseLong(obj[0].toString()));
					farmerGroup.setFarmerGroupName(obj[1].toString());
					cooperativeList.add(farmerGroup);
				}
				
				//Collections.sort(cooperativeList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			
//			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
//			cooperativeList.add(0,cooperateGroup);
			
			session.setAttribute("cooperativeList", cooperativeList);
			session.setAttribute("farmerGroupMasterList", farmerGroupList);
			session.setAttribute("countFarmerGroup", farmerGroupList!=null?farmerGroupList.size():0);
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
		return mapping.findForward("farmerGroupList");
	}

	private String convertToDecimal(double target){
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern("###,###.00");
		if(target<1) formatter.applyPattern("0.00");
		String targetStr = formatter.format(target);
		return targetStr;
		
	}
	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
        HttpSession session = request.getSession();
        List farmerGroupList = (List) session.getAttribute("farmerGroupMasterList");
      
        Collections.sort(farmerGroupList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
        GridUtil.applyCheckValue(farmerGroupList, eform.getDelfarmerGroupName(), "farmerGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        
        return mapping.findForward("farmerGroupList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
		HttpSession session = request.getSession();
		List farmerGroupList = (List) session.getAttribute("farmerGroupMasterList");
		GridUtil.applyCheckValue(farmerGroupList, eform.getDelfarmerGroupName(), "farmerGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try{
			sf.getCurrentSession().beginTransaction();
			PlantHome pHome = new PlantHome(); //Plant
			FarmerGroupHome home = new FarmerGroupHome();
			for (int i=0; i<farmerGroupList.size(); i++) {
				FarmerGroup farmerGroup = (FarmerGroup)farmerGroupList.get(i);
				if (farmerGroup.getCheckBox()==true){
					List<Plant> uPltList = pHome.findByFamerGroupId(farmerGroup.getFarmerGroupId());
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
			log.info("ลบกลุ่มเกษตรกรไปทั้งสิ้น " + eform.getDelfarmerGroupName().length + " records");
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
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List<ProvinceDistrict> dtrcList = new ArrayList<ProvinceDistrict>();
			
			long provinceNo = 0;
			if(!"".equals(eform.getProvinceNo()))
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());

			if(provinceNo > 0) {
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(provinceNo);
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
		FarmerGroupListForm eform = (FarmerGroupListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List<ProvinceDistrict> subList = new ArrayList<ProvinceDistrict>();	
			long districtNo = 0;
			long provinceNo = 0;
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo())) {
				districtNo = Long.parseLong(eform.getDistrictNo()==null?"0":eform.getDistrictNo());
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
			}
			if(districtNo > 0 && provinceNo > 0) {
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(districtNo, provinceNo);
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
}
