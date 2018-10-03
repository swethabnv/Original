package com.wsnweb.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.dcs.util.DCSCompare;
import com.dcs.util.GridUtil;
import com.wsndata.data.CheckPeriod;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.LandCheck;
import com.wsndata.data.Plant;
import com.wsndata.data.User; 
import com.wsndata.data.UserFarmerGroup;
import com.wsndata.data.UserProvince;
import com.wsndata.data.UserRegion;
import com.wsndata.dbaccess.CheckPeriodHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.LandCheckHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsnweb.form.LandCheckListForm;

public class LandCheckListAction extends Action {
	private static final Logger log = Logger.getLogger(LandCheckListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		LandCheckListForm eform = (LandCheckListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if("Delete".equals(eform.getCmd())){
				return deleteLandCheck(mapping, eform, request, response);
			} else if("DirtyList".equals(eform.getCmd())){
				return dirtylist(mapping, form, request, response);
			 } else {
				return searchLandCheck(mapping, form, request, response);
			}
		}
	}
	
	public ActionForward dirtylist(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		LandCheckListForm eform = (LandCheckListForm) form;
		HttpSession session = request.getSession();
		List landCheckList = (List) session.getAttribute("landCheckList");
		GridUtil.applyCheckValue(landCheckList, eform.getDelLandCheckId(),"landCheckId", eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		Collections.sort(landCheckList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
		return mapping.findForward("landCheckList");
	}

	private ActionForward searchLandCheck(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		LandCheckListForm eform = (LandCheckListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List<?> getList = null;
		List<?> getCooperativeList = null;
		List<LandCheck> landCheckList = new ArrayList<LandCheck>();
		User userLogin = (User) session.getAttribute("userLogin");
		List<Plant> plantYearList = new ArrayList<Plant>(); // ปีการผลิต
		List<FarmerGroup> cooperativeList = new ArrayList<FarmerGroup>(); //สังกัดสหกรณ์
		
		int plantYear = 0, plantNo = 0;
		long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0, checkPeriodId = 0, cooperativeGroupId = 0;
		String firstName = "", lastName = "", idCard = "", startDate = "", endDate = "", result = "";
		try{
			sf.getCurrentSession().beginTransaction();

			idCard = eform.getIdCard()==null?"":eform.getIdCard();
			firstName = eform.getFirstName()==null?"":eform.getFirstName();
			lastName = eform.getLastName()==null?"":eform.getLastName();
			plantYear = eform.getPlantYear();
			plantNo = eform.getPlantNo();
			startDate = eform.getStartDate()==null?"":eform.getStartDate();
			endDate = eform.getEndDate()==null?"":eform.getEndDate();
			checkPeriodId = eform.getCheckPeriodId();
			result = eform.getResult()==null?"":eform.getResult();
			cooperativeGroupId = eform.getFarmerGroupId();

			userLevel = userLogin.getLevel();
			farmerGroupId = userLogin.getFarmerGroupId();
			if(farmerGroupId==0)
				farmerGroupId = cooperativeGroupId;

			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();

			PlantHome pHome = new PlantHome();
			CheckPeriodHome cpHome = new CheckPeriodHome();
			 // --- ดึงปีการผลิตออกมา --- // 
			List<?> tempList = pHome.getPlantYear();
			if(tempList != null && tempList.size()>0){
			  for(int a=0; a<tempList.size(); a++){
				  if(tempList.get(a) !=null){
					  Plant pYear = new Plant();
					  pYear.setPlantYear(Integer.parseInt(tempList.get(a).toString(), 10)); 
					  plantYearList.add(pYear);
				  }
			  }
			}
			 // --- ดึงระยะการปลูกออกมา --- // 
			List<CheckPeriod> checkPeriodList = cpHome.findAll();
			
			LandCheckHome lcHome = new LandCheckHome();
			startDate = startDate.equals("")?"":convertDate(startDate);
			endDate = endDate.equals("")?"":convertDate(endDate);
			result = result.equals("0")?"":result;
//			if(idCard.equals("") && plantYear==0 && plantNo==0 && startDate.equals("") && endDate.equals("") && checkPeriod.equals("") && result.equals("")) {
//				getList = lcHome.findAll();
//			} else {
				getList = lcHome.searchByCriteria(idCard, firstName, lastName, plantYear, plantNo, startDate, endDate, checkPeriodId, result, regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId);
//			}
			
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
					LandCheck landCheck = new LandCheck();
					Object[] obj = (Object[])getList.get(i);
					landCheck.setLandCheckId(Long.parseLong(obj[0]==null?"0":obj[0].toString()));
					landCheck.setIdCard(obj[1]==null?"":obj[1].toString());
					landCheck.setFarmerName(obj[2]==null?"":obj[2].toString());
					landCheck.setCheckDateTh(obj[3]==null?"-":obj[3].toString());
					landCheck.setCheckPeriod(obj[4]==null?"":obj[4].toString());
					landCheck.setLandRight(obj[5]==null?"-":obj[5].toString());
					landCheck.setCheckResult(obj[6].toString().equals("P")?"ผ่าน":"ไม่ผ่าน");
					landCheck.setPlantYear(Integer.parseInt(obj[7]==null?"":obj[7].toString()));
					landCheck.setPlantNo(Integer.parseInt(obj[8]==null?"":obj[8].toString()));
					landCheckList.add(landCheck);
				}
				Collections.sort(landCheckList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			/////ดึงสังกัดสหกรณื/////
			FarmerGroup cooperateGroup = new FarmerGroup();
			cooperateGroup.setFarmerGroupId(0);
			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
			cooperativeList.add(0,cooperateGroup);
			
			FarmerGroupHome fgHome = new FarmerGroupHome();
			//getCooperativeList = fgHome.searchByCriteria("C", "", "", userLogin.getRegionNo(), userLogin.getProvinceNo(), userLogin.getDistrictNo(), userLogin.getSubDistrictNo(), farmerGroupId, userLogin.getUserName(), 0);
			//Cooperate by role : Add by Music 20/09/2017
			ArrayList<String> rNoList = new ArrayList<String>();
			for(UserRegion u : userLogin.getUserRegion()){
				if(u!=null){
					rNoList.add(String.valueOf(u.getRegionNo()));
				}
			}
			ArrayList<String> pNoList = new ArrayList<String>();
			for(UserProvince u : userLogin.getUserProvince()){
				if(u!=null){
					pNoList.add(String.valueOf(u.getProvinceNo()));
				}
			}
			ArrayList<String> fgIdList = new ArrayList<String>();
			for(UserFarmerGroup u : userLogin.getUserFarmerGroup()){
				if(u!=null){
					fgIdList.add(String.valueOf(u.getFarmerGroupId()));
				}
			}
			cooperativeList = fgHome.searchByAnauthorizeFilteredByCriteria("C", rNoList, pNoList, userLogin.getDistrictNo(), userLogin.getSubDistrictNo(), fgIdList);
			cooperativeList.add(0,cooperateGroup);
			
			/*if(getCooperativeList!=null && getCooperativeList.size()>0){
				for(int i=0; i< getCooperativeList.size(); i++){
					FarmerGroup farmerGroup = new FarmerGroup();
					FarmerGroup obj = (FarmerGroup)getCooperativeList.get(i);
					farmerGroup.setFarmerGroupId(obj.getFarmerGroupId());
					farmerGroup.setFarmerGroupName(obj.getFarmerGroupName());
					cooperativeList.add(farmerGroup);
				}
			}*/
			//End Cooperate by role
			session.setAttribute("cooperativeList", cooperativeList);
			session.setAttribute("landCheckList", landCheckList);
			session.setAttribute("checkPeriodList", checkPeriodList);
			session.setAttribute("pYearList", plantYearList);

		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("landCheckList");
	}
	
	private String convertDate(String sDate) {
		String[] pDate;
		pDate = sDate.split("/");
		return (Integer.parseInt(pDate[2])-543)+"-"+pDate[1]+"-"+pDate[0];
	}

	private ActionForward deleteLandCheck(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckListForm eform = (LandCheckListForm)form;
		HttpSession session = request.getSession();
		List landCheckList = (List) session.getAttribute("landCheckList");
		GridUtil.applyCheckValue(landCheckList, eform.getDelLandCheckId(), "landCheckId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		LandCheckHome home = new LandCheckHome();
		try{
			sf.getCurrentSession().beginTransaction();	
			for (int i=0; i<landCheckList.size(); i++) {
				LandCheck landCheck = (LandCheck)landCheckList.get(i);
				if (landCheck.getCheckBox()==true){
					LandCheck objLandCheck = home.findByLandCheckId(landCheck.getLandCheckId());
					if(objLandCheck != null){
						home.deleteLandChecked(objLandCheck.getLandCheckId());
						System.out.println("Delete : "+landCheck.getLandCheckId());
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ทำการลบข้อมูลไปทั้งสิ้น " + eform.getDelLandCheckId().length + " records");
			
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return searchLandCheck(mapping, eform, request, response);
	}
}
