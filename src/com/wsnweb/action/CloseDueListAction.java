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
import com.wsndata.data.CloseDue;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.Plant;
import com.wsndata.data.User;
import com.wsndata.dbaccess.CloseDueHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsnweb.form.CloseDueListForm;

public class CloseDueListAction extends Action {
	private static final Logger log = Logger.getLogger(CloseDueListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CloseDueListForm eform = (CloseDueListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		CloseDueListForm eform = (CloseDueListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List<CloseDue> closeDueList = new ArrayList<CloseDue>();
		long plantYear=0, plantNo=0, farmerGroupId=0;
		try{
			CloseDueHome home = new CloseDueHome();
			PlantHome pHome = new PlantHome();
			FarmerGroupHome fgHome = new FarmerGroupHome();
			sf.getCurrentSession().beginTransaction();
			if(eform.getPlantYear()!=null) {
				plantYear = eform.getPlantYear().equals("")?0:Long.parseLong(eform.getPlantYear());
			}
			if(eform.getPlantNo()!=null) {
				plantNo = eform.getPlantNo().equals("")?0:Long.parseLong(eform.getPlantNo());
			}
			if(eform.getFarmerGroupId()!=null) {
				farmerGroupId = eform.getFarmerGroupId().equals("")?0:Long.parseLong(eform.getFarmerGroupId());
				if(farmerGroupId == 0 && userLogin.getFarmerGroupId() > 0){
					//farmerGroupId == 0 user not chose farmerGroup in criteria
					//userLogin.getFarmerGroupId() > 0 user assigned to some farmerGroup
					farmerGroupId = userLogin.getFarmerGroupId();
				}
			}else{//initial screen
				if(userLogin.getFarmerGroupId() > 0){
					//userLogin.getFarmerGroupId() > 0 user assigned to some farmerGroup
					farmerGroupId = userLogin.getFarmerGroupId();
				}
			}
			
			List<?> getCloseDueList = home.searchByCriteria(plantYear, plantNo, farmerGroupId);
			if(getCloseDueList != null && getCloseDueList.size() > 0) {
				for(int i=0; i< getCloseDueList.size(); i++){
					CloseDue closeDue = new CloseDue();
					Object[] obj = (Object[])getCloseDueList.get(i);
					closeDue.setCloseDueId(Long.parseLong(obj[0].toString()));
					closeDue.setPlantYear(Long.parseLong(obj[1].toString()));
					closeDue.setPlantNo(Long.parseLong(obj[2].toString()));
					closeDue.setFarmerGroupId(Long.parseLong(obj[3].toString()));
					closeDue.setFarmerGroupName(obj[4].toString());
					closeDueList.add(closeDue);
				}
			}
//			if(plantYear>0 || plantNo>0 || farmerGroupId>0)
//			else
//				closeDueList = home.findAll();
			
			int countCloseDue = 0;
			if(closeDueList != null)
				countCloseDue = closeDueList.size();
			
			List<Plant> plantYearList = new ArrayList<Plant>();
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

			List<?> getCooperativeList = null;
			List<FarmerGroup> cooperativeGroupList = new ArrayList<FarmerGroup>();
			//-- ดึงกลุ่มสหกรณ์ --//
			FarmerGroup cooperateGroup = new FarmerGroup();
			cooperateGroup.setFarmerGroupId(0);
			cooperateGroup.setFarmerGroupName("กรุณาเลือก");
			cooperativeGroupList.add(0,cooperateGroup);
			
			getCooperativeList = fgHome.searchByCriteria("C", "", "", userLogin.getRegionNo(), userLogin.getProvinceNo(), userLogin.getDistrictNo(), userLogin.getSubDistrictNo(), userLogin.getFarmerGroupId(), userLogin.getUserName(), 0);
			if(getCooperativeList!=null && getCooperativeList.size()>0){
				for(int i=0; i< getCooperativeList.size(); i++){
					FarmerGroup farmerGroup = new FarmerGroup();
					Object[] obj = (Object[])getCooperativeList.get(i);
					farmerGroup.setFarmerGroupId(Long.parseLong(obj[0].toString()));
					farmerGroup.setFarmerGroupName(obj[1].toString());
					cooperativeGroupList.add(farmerGroup);
				}
			}
			//------//
			
			Collections.sort(closeDueList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("plantYearList", plantYearList);
			session.setAttribute("closeDueList", closeDueList);
			session.setAttribute("countCloseDue", countCloseDue);
			session.setAttribute("cooperativeGroupMasterList", cooperativeGroupList);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("closeDueList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CloseDueListForm eform = (CloseDueListForm)form;
        HttpSession session = request.getSession();
        List closeDueList = (List) session.getAttribute("closeDueList");
        GridUtil.applyCheckValue(closeDueList, eform.getDelCloseDue(), "closeDueId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(closeDueList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("closeDueList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		CloseDueListForm eform = (CloseDueListForm)form;
		HttpSession session = request.getSession();
		List closeDueList = (List) session.getAttribute("closeDueList");
		GridUtil.applyCheckValue(closeDueList, eform.getDelCloseDue(), "closeDueId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try{
			sf.getCurrentSession().beginTransaction();	
			CloseDueHome home = new CloseDueHome();
			for (int i=0; i<closeDueList.size(); i++) {
				CloseDue closeDue = (CloseDue)closeDueList.get(i);
				if (closeDue.getCheckBox()==true){
					home.delete(closeDue);
				}
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบข้อมูลการปิดยอดไปทั้งสิ้น " + eform.getDelCloseDue().length + " records");
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}
}
