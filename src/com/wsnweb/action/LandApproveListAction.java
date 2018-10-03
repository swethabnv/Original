package com.wsnweb.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.LandRight;
import com.wsndata.data.Plant;
import com.wsndata.data.User;
import com.wsndata.data.UserFarmerGroup;
import com.wsndata.data.UserProvince;
import com.wsndata.data.UserRegion;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsnweb.form.LandApproveListForm;

public class LandApproveListAction extends Action {
	private static final Logger log = Logger.getLogger(LandCheckAction.class);
	DateFormat dfTimeMillisec = new SimpleDateFormat("ddMMyyHHmmssSSS");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		LandApproveListForm eform = (LandApproveListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if("DirtyList".equals(eform.getCmd())){
				return dirtylist(mapping, form, request, response);
			} else if("Approved".equals(eform.getCmd())){
				return setApproved(mapping, form, request, response);
			} else if("NotApproved".equals(eform.getCmd())){
				return setApproved(mapping, form, request, response); 
			} else {
				return searchLandApproveList(mapping, form, request, response);
			}
		}
	}	
	public ActionForward dirtylist(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		LandApproveListForm eform = (LandApproveListForm) form;
		HttpSession session = request.getSession();
		List<?> landApproveList = (List<?>) session.getAttribute("landApproveList");
		Collections.sort(landApproveList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
		return mapping.findForward("landApproveList");
	}

	public ActionForward searchLandApproveList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		LandApproveListForm eform = (LandApproveListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List<Plant> plantYearList = new ArrayList<Plant>(); // ปีการผลิต
		List<FarmerGroup> cooperativeList = new ArrayList<FarmerGroup>(); //สังกัดสหกรณ์
		List<?> getList = null;
		List<?> getCooperativeList = null;
		List<LandRight> landApproveList = new ArrayList<LandRight>();
		User userLogin = (User) session.getAttribute("userLogin");
		
		int plantYear = 0, plantNo = 0;
		long userLevel = 0, regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, cooperativeGroupId = 0, farmerGroupId = 0;
		String firstName = "", lastName = "", idCard = "", startDate = "", endDate = "", approvedDate = "", approved = "";
				
		try{
			sf.getCurrentSession().beginTransaction();
			PlantHome pHome = new PlantHome();
			
			idCard = eform.getIdCard()==null?"":eform.getIdCard();
			firstName = eform.getFirstName()==null?"":eform.getFirstName();
			lastName = eform.getLastName()==null?"":eform.getLastName();
			plantYear = eform.getPlantYear();
			plantNo = eform.getPlantNo();
			startDate = eform.getStartDate()==null?"":eform.getStartDate();
			endDate = eform.getEndDate()==null?"":eform.getEndDate();
			approvedDate = eform.getApprovedDate()==null?"":eform.getApprovedDate();
			approved = eform.getApproved()==null?"":eform.getApproved();
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
			
			LandRightHome lrHome = new LandRightHome();
		
			getList = lrHome.searchApproveList(idCard, firstName, lastName, plantYear, plantNo, startDate, endDate, approved, approvedDate, farmerGroupId);
			
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
					
					LandRight landApprove = new LandRight();
					Object[] obj = (Object[])getList.get(i);
					landApprove.setIdCard(obj[0]==null?"":obj[0].toString());
					landApprove.setPlantYear(Integer.parseInt(obj[1]==null?"":obj[1].toString()));
					landApprove.setPlantNo(Integer.parseInt(obj[2]==null?"":obj[2].toString()));
					landApprove.setFarmerName(obj[3]==null?"":obj[3].toString());
					landApprove.setLandRight(obj[4]==null?"":obj[4].toString());
					landApprove.setApproved(obj[5]==null?"":obj[5].toString());
					landApprove.setApprovedDate(obj[6]==null?"":obj[6].toString());
					landApprove.setApprovedBy(obj[7]==null?"":obj[7].toString());
					landApprove.setPlantId(Integer.parseInt(obj[8]==null?"":obj[8].toString()));
					landApprove.setSeq(Integer.parseInt(obj[9]==null?"":obj[9].toString()));
					landApprove.setTypeId(Integer.parseInt(obj[10]==null?"":obj[10].toString()));
					landApprove.setDocNo(obj[11]==null?"":obj[11].toString());
					landApprove.setLinkApproved("ผ่านเกณฑ์");
					landApprove.setLinkNotApproved("ไม่ผ่านเกณฑ์");
					if(landApprove.getApproved().equals("F")){
						landApprove.setApproved("ไม่ผ่านเกณฑ์");
						landApprove.setLinkApproved("");
						landApprove.setLinkNotApproved("");
					}
					else if(landApprove.getApproved().equals("P")){
						landApprove.setApproved("ผ่านเกณฑ์");	
						landApprove.setLinkApproved("");
						landApprove.setLinkNotApproved("");
					}
					else landApprove.setApproved("รอการอนุมัติ");
										
					landApproveList.add(landApprove);
				}
				Collections.sort(landApproveList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			/////ดึงสังกัดสหกรณื/////
			FarmerGroup cooperateGroup = new FarmerGroup();
			cooperateGroup.setFarmerGroupId(0);
			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
			cooperativeList.add(0,cooperateGroup);
			
			FarmerGroupHome fgHome = new FarmerGroupHome();
			//getCooperativeList = fgHome.searchByCriteria("C", "", "", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, userLogin.getUserName(), 0);
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
			session.setAttribute("landApproveList", landApproveList);
			session.setAttribute("pYearList", plantYearList);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("landApproveList");
	}
	
	public ActionForward setApproved(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		LandApproveListForm eform = (LandApproveListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		
		int plantId=0, SEQ=0, typeId=0;
		String docNo="", approveResult="", userName="",approvedDate="" ;
						
		try{
			sf.getCurrentSession().beginTransaction();
			LandRightHome lrHome = new LandRightHome();
			PlantHome pHome = new PlantHome();
			User userLogin = (User) session.getAttribute("userLogin");
			Calendar cal = Calendar.getInstance();
			
			plantId = eform.getPlantId();
			SEQ = eform.getSEQ();
			typeId = eform.getTypeId();
			docNo = eform.getDocNo()==null?"":eform.getDocNo();
			approveResult = eform.getApproveResult()==null?"":eform.getApproveResult();
			userName = userLogin.getUserName();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			approvedDate = df.format(cal.getTime());

			Plant plant = pHome.findById(plantId);
			if(plant != null) {
				plant.setCreateDate(cal.getTime());
				pHome.update(plant);
			}
			
			lrHome.setApproved(plantId, SEQ, typeId, docNo, approveResult, userName, approvedDate);
			sf.getCurrentSession().getTransaction().commit();
			searchLandApproveList(mapping, form, request, response);
		}catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }
		return mapping.findForward("landApproveList");
		}
	}
