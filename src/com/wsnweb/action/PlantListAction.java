package com.wsnweb.action;

import java.io.PrintWriter;
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
import com.dcs.util.StringUtil;
import com.wsndata.data.Branch;
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.District;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.Plant;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User; 
import com.wsndata.data.UserFarmerGroup;
import com.wsndata.data.UserProvince;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.PlantListForm;

public class PlantListAction extends Action {
	private static final Logger log = Logger.getLogger(PlantListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		PlantListForm eform = (PlantListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if("Delete".equals(eform.getCmd())){
				return deletePlant(mapping, eform, request, response);
			} else if("DirtyList".equals(eform.getCmd())){
				return dirtylist(mapping, form, request, response);
			} else if ("GetDistrict".equals(eform.getCmd())) {
				 return getDistrictInfo(mapping, eform, request, response);
			 } else if ("GetSubDistrict".equals(eform.getCmd())) {
				 return getSubDistrictInfo(mapping, eform, request, response);
			 } else {
				return searchPlant(mapping, form, request, response);
			}
		}
	}
	
	public ActionForward dirtylist(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		PlantListForm eform = (PlantListForm) form;
		HttpSession session = request.getSession();
		List plantList = (List) session.getAttribute("plantList");
		GridUtil.applyCheckValue(plantList, eform.getDelPlantId(),"plantId", eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		Collections.sort(plantList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
		return mapping.findForward("plantList");
	}

	private ActionForward searchPlant(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		PlantListForm eform = (PlantListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BreedGroupHome groupHome = new BreedGroupHome();
		BreedTypeHome typeHome = new BreedTypeHome();
		PlantHome home = new PlantHome();
		ProvinceHome provHome = new ProvinceHome();
		FarmerGroupHome farmerGroupHome = new FarmerGroupHome();
		UserHome uHome = new UserHome();
		
		//----- search list after submit status popup-------//
		if("Save".equals(eform.getCmd())){
			eform.setIdCard("");
			eform.setPlantStatus("");
		}
		//--------------------------------------------------//
		
		List<?> getPlantList = null;
		List<Plant> plantList = new ArrayList<Plant>();
		
		List<BreedType> breedTypeList = new ArrayList<BreedType>(); 
		List<BreedGroup> breedGroupList = new ArrayList<BreedGroup>(); // ข้อมูลเกษตรกรผู้เพาะปลูก กลุ่มพันธุ์
		List<Branch> branchList = (List) session.getAttribute("userBranchList"); // ธกส สาขา
		//List<Branch> branchList = (List) session.getAttribute("branchList"); // ธกส สาขา
		List<Plant> plantYearList = new ArrayList<Plant>(); // ปีการผลิต
		
		List<Province> searchProvinceList = new ArrayList<Province>();
		List<UserProvince> searchUserProvinceList = null;
		List<UserFarmerGroup> searchUserFarmerGroupList = null;
		
		
		List<District> searchDistrictList = new ArrayList<District>();
		List<SubDistrict> searchSubDistrictList = new ArrayList<SubDistrict>();
		
		List<?> getCooperativeList = null;
		List<FarmerGroup> cooperativeGroupList = new ArrayList<FarmerGroup>();
		
		long breedTypeId= 0 , regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0, cooperativeGroupId = 0;
		int plantYear = 0, plantNo = 0;
		String firstName ="", lastName="", idCard = "", plantStatus = "";
		try{
			sf.getCurrentSession().beginTransaction();
			
			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			
			
			
			// --- start : added by yatphiroon.p on 10/01/2017
			/*if(userLogin.getLevel()==1){  //comment on 09/08/2017
				searchProvinceList = provHome.findAll(); // Added on 09/02/2017
			} else if(userLogin.getLevel()==2){
				 List<UserRegion> userRegionList  = new ArrayList<UserRegion>(userLogin.getUserRegion());
				 List<Long> paramList = new ArrayList<Long>();
				 for (UserRegion rObj : userRegionList) {
						if(rObj != null){
							paramList.add(rObj.getRegionNo());
						}
				}
				searchProvinceList = provHome.retrieveByRegionNoList(paramList); // Added on 09/02/2017
			} else{
				searchUserProvinceList  = new ArrayList<UserProvince>(userLogin.getUserProvince());
				if(searchUserProvinceList != null && searchUserProvinceList.size() > 0){
					for (UserProvince userProv : searchUserProvinceList) {
						Province pObj = provHome.searchByProvinceNo(userProv.getProvinceNo());
						if(pObj != null)
							searchProvinceList.add(pObj);
					}
				}
			}*/
			// --- finish : added by yatphiroon.p on 10/01/2017
			
			searchProvinceList = provHome.findAll(); //test 09/08/2017
			
			
			// ---- closed by yatphiroon.p on 10/01/2017 --- 
			/*
			if(userLogin.getLevel()>2) {
				 Province province = provHome.searchByProvinceNo(userLogin.getProvinceNo()); 
				 searchProvinceList.add(province); 
			} else if(userLogin.getLevel()>1)
				searchProvinceList = provHome.retrieveByRegionNo(userLogin.getRegionNo());
			else
				searchProvinceList = provHome.findAll();
			*/
			// ---- closed by yatphiroon.p on 10/01/2017 --- 
			
			//if(searchProvinceList !=null && searchProvinceList.size()>0)
			//	searchProvinceList.add(0,prov);
			
			//--- ดึงอำเภอ ออกมา --- //
			District district = new District();
			district.setDistrictNo(0);
			district.setProvinceNo(0);
			district.setThaiName("กรุณาเลือก");
			searchDistrictList.add(0,district);
			//--- ดึงตำบล ออกมา --- //
			SubDistrict subDistrict = new SubDistrict();
			subDistrict.setProvinceNo(0);
			subDistrict.setDistrictNo(0);
			subDistrict.setSubDistrictNo(0);
			subDistrict.setThaiName("กรุณาเลือก");
			searchSubDistrictList.add(0,subDistrict);
			
			if(!"".equals(eform.getBreedTypeId()))
				breedTypeId = Long.parseLong(eform.getBreedTypeId()==null?"0":eform.getBreedTypeId());
			
			idCard = eform.getIdCard()==null ? "":eform.getIdCard();
			
			if(!"".equals(eform.getPlantYear()))
				plantYear = Integer.parseInt(eform.getPlantYear()==null?"0":eform.getPlantYear(), 10);
			
			if(!"".equals(eform.getPlantNo()))
				plantNo = Integer.parseInt(eform.getPlantNo()==null?"0":eform.getPlantNo(), 10);
			provinceNo = (eform.getProvinceNo()==0?0:eform.getProvinceNo());
			districtNo = (eform.getDistrictNo()==0?0:eform.getDistrictNo());
			subDistrictNo = (eform.getSubDistrictNo()==0?0:eform.getSubDistrictNo());
			
		//	if(eform.getFarmerGroupId()()!=null)
			cooperativeGroupId = (eform.getFarmerGroupId()==0?0:eform.getFarmerGroupId());
			
			userLevel = userLogin.getLevel();
			
			// --- farmergroupId from userLogin
			// farmerGroupId = userLogin.getFarmerGroupId(); -- closed by Yatphiroon.P
			// --- start : added by Yatphiroon.P on 10/1/2017
			List<String> farmerGroupIdList = new ArrayList<String>();
			List<UserFarmerGroup> fGroupIdList =  new ArrayList<UserFarmerGroup>(userLogin.getUserFarmerGroup());
			if(fGroupIdList != null && fGroupIdList.size() > 0){
				for (UserFarmerGroup fObj : fGroupIdList) {
					if(fObj!=null)
						farmerGroupIdList.add(String.valueOf(fObj.getFarmerGroupId()));
				}
			}
			// --- finish: added by Yatphiroon.P on 10/1/2017
			
			/* Close by Thanaput.s 14/09/2017
			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();
			*/
			
			firstName = eform.getFirstName()==null?"":eform.getFirstName();
			lastName = eform.getLastName()==null?"":eform.getLastName();
			plantStatus = eform.getPlantStatus()==null?"":eform.getPlantStatus();
			
			
			
			// --- ProvinceNo : added by Yatphiroon.P on 10/1/2017 --
			List<String> provinceList = new ArrayList<String>();
			String provNo = String.valueOf(eform.getUserProvinceNo()==null?"":eform.getUserProvinceNo());
			if (!"".equals(provNo)) {
				if (provNo.indexOf(",") > -1) {
					String[] items = provNo.split(",");
					for (String item : items) {
						provinceList.add(item);
					}
				} else {
					provinceList.add(provNo);
				}
			}
			
			// FarmerGroupId from search : added by Yatphiroon.P on 10/1/2017 --
			List<String> cooperativeGroupIdList = new ArrayList<String>();
			String fGrpId = String.valueOf(eform.getUserFarmerGroupId()==null?"":eform.getUserFarmerGroupId());
			if (!"".equals(fGrpId)) {
				if (fGrpId.indexOf(",") > -1) {
					String[] items = fGrpId.split(",");
					for (String item : items) {
						cooperativeGroupIdList.add(item);
					}
				} else {
					cooperativeGroupIdList.add(fGrpId);
				}
			}
			
			
			// changed by Yatphiroon.P on 10/1/2017
			getPlantList = home.searchPlantByCriteria(breedTypeId, plantYear, plantNo, idCard, firstName, lastName, regionNo, provinceList, districtNo, subDistrictNo, farmerGroupIdList, plantStatus, cooperativeGroupIdList);
			
			//getPlantList = home.searchByCriteria(breedTypeId, plantYear, plantNo, idCard, firstName, lastName, regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, plantStatus, cooperativeGroupId);
			
			if(getPlantList !=null && getPlantList.size() > 0){
				for(int i=0; i< getPlantList.size(); i++){
					Plant plant = new Plant();
					Object[] obj = (Object[])getPlantList.get(i);
					plant.setPlantId(Long.parseLong(obj[0].toString()));
					plant.setIdCard(obj[1].toString());
					plant.setFirstName(StringUtil.beString(obj[3]));
					plant.setLastName(StringUtil.beString(obj[4]));
					plant.setFullName(StringUtil.beString(obj[2]) + " " + plant.getFirstName() + " " +  plant.getLastName());
					plant.setDistrictName(obj[5].toString());
					
					if(obj[6]!=null)
						plant.setBranchCode(Long.parseLong(obj[6].toString()));
					plant.setBranchName(StringUtil.beString(obj[7]));
					if(obj[8]!=null)
						plant.setBreedTypeId(Long.parseLong(obj[8].toString()));
					if(obj[9]!=null)
						plant.setBreedTypeName(StringUtil.beString(obj[9]));
					plant.setPlantYear(Integer.parseInt(obj[10].toString(), 10));
					plant.setPlantNo(Integer.parseInt(obj[11].toString(), 10));
					if(obj[12] != null)
						plant.setRefPlantId(Long.parseLong(obj[12].toString()));
					if(obj[14]!= null){
						if("1".equals(obj[14].toString()))
							plant.setPlantStatus("ผ่าน");
						if("0".equals(obj[14].toString()))
							plant.setPlantStatus("ไม่ผ่าน");
						}
					if(obj[13]!= null)
						plant.setReason(StringUtil.beString(obj[13]));
					if(obj[15] != null)
						plant.setStatus(obj[15].toString());
					if(obj[16] != null)
						plant.setRemark(obj[16].toString());
					
					plant.setStatusPopup("กำหนดสถานะ");
					plantList.add(plant);
				}
				Collections.sort(plantList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
			}
			 // --- ดึงปีการผลิตออกมา --- // 
			List tempList = home.getPlantYear();
			if(tempList != null && tempList.size()>0){
			  for(int a=0; a<tempList.size(); a++){
				  if(tempList.get(a) !=null){
					  Plant pYear = new Plant();
					  pYear.setPlantYear(Integer.parseInt(tempList.get(a).toString(), 10)); 
					  plantYearList.add(pYear);
				  }
			  }
			}
		
			breedTypeList = typeHome.findAll();   //ประเภทพันธุ์พืช
			breedGroupList = groupHome.findAll(); //กลุ่มพันธุ์พืช 
			
			//-- ดึงกลุ่มสหกรณ์ --//
			/*
			 *  closed by Yatphiroon.P on 10/1/2016
			FarmerGroup cooperateGroup = new FarmerGroup();
			cooperateGroup.setFarmerGroupId(0);
			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
			cooperativeGroupList.add(0,cooperateGroup);
			*/
//			if("กรุณาเลือก".equals(cooperativeName)){
//				cooperateGroup.setFarmerGroupName("");
//				cooperativeName = cooperateGroup.getFarmerGroupName();
//			}
			
			searchUserFarmerGroupList = new ArrayList<UserFarmerGroup>(userLogin.getUserFarmerGroup());
			
			
			if(searchUserFarmerGroupList != null && searchUserFarmerGroupList.size() > 0){ 
				for (UserFarmerGroup farmerGrp : searchUserFarmerGroupList) {
					FarmerGroup farmerGroup = farmerGroupHome.findByFarmerGroupId(farmerGrp.getFarmerGroupId());
					if(farmerGroup!=null){
						farmerGroup.setFarmerGroupId(farmerGroup.getFarmerGroupId());
						farmerGroup.setFarmerGroupName(farmerGroup.getFarmerGroupName());
						cooperativeGroupList.add(farmerGroup);
					}
				}
			}else{
				 // In case of select nothing = select all -- Added by Yatphiroon.P on 09/02/2017
				cooperativeGroupList = farmerGroupHome.findGroup("C");
			}
			
			
			/*
			getCooperativeList = farmerGroupHome.searchByCriteria("C", "", "", userLogin.getRegionNo(), userLogin.getProvinceNo(), userLogin.getDistrictNo(), userLogin.getSubDistrictNo(), farmerGroupId, userLogin.getUserName(), 0);
			if(getCooperativeList!=null && getCooperativeList.size()>0){
				for(int i=0; i< getCooperativeList.size(); i++){
					FarmerGroup farmerGroup = new FarmerGroup();
					Object[] obj = (Object[])getCooperativeList.get(i);
					farmerGroup.setFarmerGroupId(Long.parseLong(obj[0].toString()));
					farmerGroup.setFarmerGroupName(obj[1].toString());
					cooperativeGroupList.add(farmerGroup);
				}
			
				//Collections.sort(cooperativeList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
//				cooperateGroup.setFarmerGroupName("กรุณาเลือก");
//				cooperativeGroupList.add(0,cooperateGroup);
			}*/
			//------//
			
			session.setAttribute("plantList", plantList);
			session.setAttribute("countPlant", plantList!=null?plantList.size():0);
			session.setAttribute("plantYearList", plantYearList);
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("breedGroupPlantList", breedGroupList);
			session.setAttribute("branchPlantList", branchList);
			session.setAttribute("searchProvinceList", searchProvinceList);
			session.setAttribute("searchDistrictList", searchDistrictList);
			session.setAttribute("searchSubDistrictList", searchSubDistrictList);
			session.setAttribute("cooperativeGroupMasterList", cooperativeGroupList);
			
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("plantList");
	}

	private ActionForward deletePlant(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantListForm eform = (PlantListForm)form;
		HttpSession session = request.getSession();
		List plantList = (List) session.getAttribute("plantList");
		GridUtil.applyCheckValue(plantList, eform.getDelPlantId(), "plantId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		PlantHome home = new PlantHome();
		try{
			sf.getCurrentSession().beginTransaction();	
			for (int i=0; i<plantList.size(); i++) {
				Plant plant = (Plant)plantList.get(i);
				if (plant.getCheckBox()==true){
					List<Plant> pList = home.findByRefId(plant.getRefPlantId());
					if(pList !=null && pList.size() > 0){
						for(int j = 0; j < pList.size(); j++){
							Plant refPlant = (Plant)pList.get(j);
							home.deletePlant(refPlant.getPlantId());
						}
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ทำการลบข้อมูลสาขาไปทั้งสิ้น " + eform.getDelPlantId().length + " records");
			
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return searchPlant(mapping, eform, request, response);
	}
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		PlantListForm eform = (PlantListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();
			List<ProvinceDistrict> dtrcList = new ArrayList<ProvinceDistrict>();
			
		
			// --- start : added by Yatphiroon.P on 10/01/2017 ---
			List<String> provinceList = new ArrayList<String>();
			String provinceNo = String.valueOf(eform.getUserProvinceNo());
			

			if (!"".equals(provinceNo)) {  //comment on 09/08/2017
			
					if (provinceNo.indexOf(",") > -1) {
						String[] items = provinceNo.split(",");
						for (String item : items) {
							provinceList.add(item);
						}
					} else {
						provinceList.add(provinceNo);
					}
					dtrcList = home.filterByProvinceNoList(provinceList);
				
			}
			// --- finish : added by Yatphiroon.P on 10/01/2017 ---
			
			
			// --- closed by Yatphiroon.P on 10/01/2017
			/*  
			if(eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(eform.getProvinceNo());
			}*/

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
			  log.error(e,e);
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
		//User userLogin = (User) session.getAttribute("userLogin");
		PlantListForm eform = (PlantListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List<ProvinceDistrict> subList = new ArrayList<ProvinceDistrict>();		
			
			
			// --------- Added by Yatphiroon.P on 10/1/2017 ------
			long districtNo = 0;
			List<String> provinceList = new ArrayList<String>();
			String provinceNo = String.valueOf(eform.getUserProvinceNo());
			if (!"".equals(provinceNo)) {
				if (provinceNo.indexOf(",") > -1) {
					String[] items = provinceNo.split(",");
					for (String item : items) {
						provinceList.add(item);
					}
				} else {
					provinceList.add(provinceNo);
				}
			}
			
			if(!"".equals(eform.getDistrictNo()) && !"".equals(provinceNo)) {
				districtNo = eform.getDistrictNo();
				subList = home.filterByProvinceAndDistrictNo(districtNo, provinceList);
			}
			
			// ---------- Finished Added by Yatphiroon.P on 10/1/2017 --
			/*
			if(eform.getDistrictNo() > 0 && eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(eform.getDistrictNo(), eform.getProvinceNo());
			}*/
			
			
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
			  log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}
}
