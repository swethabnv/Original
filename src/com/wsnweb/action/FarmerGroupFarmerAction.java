package com.wsnweb.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.wsndata.data.Branch;
import com.wsndata.data.FarmerGroupFarmer;
import com.wsndata.data.Plant;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupFarmerHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsnweb.form.FarmerGroupFarmerForm;
import com.wsnweb.util.Utility;

public class FarmerGroupFarmerAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerGroupFarmerAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FarmerGroupFarmerForm eform = (FarmerGroupFarmerForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("Add".equals(eform.getCmd())){
				 return addFarmerToGroup(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		FarmerGroupFarmerForm eform = (FarmerGroupFarmerForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = new ArrayList();
		List farmerGroupList = new ArrayList();
		List farmerList = new ArrayList();
		List groupfList = new ArrayList();
		List<Branch> branchList = (List) session.getAttribute("userBranchList");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		try{
			FarmerHome home = new FarmerHome();
			FarmerGroupFarmerHome fghome = new FarmerGroupFarmerHome();
			sf.getCurrentSession().beginTransaction();
			getList=home.findByCriteria2("","","");
			groupfList = fghome.findByfarmerGroup(eform.getFarmerGroupId());
			for (int i = 0; i < getList.size(); i++) 
			{
				Object[] farmer = (Object[]) getList.get(i);
				FarmerGroupFarmer fgf = new FarmerGroupFarmer();
				String memberNo = "00000000";
				if(farmer[1]!=null) memberNo = farmer[1].toString();
				else if(farmer[2]!=null) memberNo = farmer[2].toString();
				fgf.setMemberNo(memberNo);
				fgf.setIdCard(farmer[3].toString());
				fgf.setEffectiveDate(df.parse(farmer[4].toString()));
				fgf.setFirstName(farmer[6].toString());
				fgf.setLastName(farmer[7].toString());
				fgf.setProvinceName(farmer[8]!=null?farmer[8].toString():"-");
				fgf.setDistrictName(farmer[9]!=null?farmer[9].toString():"-");
				fgf.setSubDistrictName(farmer[10]!=null?farmer[10].toString():"-");
				fgf.setFarmerGroupName(farmer[11]!=null?farmer[11].toString():"-");
				
				farmerList.add(fgf);
				
				for (int j = 0; j < groupfList.size(); j++) 
				{
					FarmerGroupFarmer fg = (FarmerGroupFarmer)groupfList.get(j);
					if (fg.getIdCard().equals(farmer[3].toString())) 
					{
						groupfList.remove(j);
						farmerGroupList.add(fgf);
						farmerList.remove(fgf);
						break;
					}
				}
			}
			session.setAttribute("farmerGroupList", farmerGroupList);
			session.setAttribute("farmerList", farmerList);
			session.setAttribute("countFarmerGroup", farmerGroupList!=null?farmerGroupList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("farmerGroupFarmer");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FarmerGroupFarmerForm eform = (FarmerGroupFarmerForm)form;
        HttpSession session = request.getSession();
        List farmerGroupList = (List) session.getAttribute("farmerGroupList");
        GridUtil.applyCheckValue(farmerGroupList, eform.getDelfarmerGroupName(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(farmerGroupList, new DCSCompare(eform.getSortColumn(),false));
        return mapping.findForward("farmerGroupFarmer");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerGroupFarmerForm eform = (FarmerGroupFarmerForm)form;
		HttpSession session = request.getSession();
		List farmerGroupList = (List) session.getAttribute("farmerGroupList");
		GridUtil.applyCheckValue(farmerGroupList, eform.getDelfarmerGroupName(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try{
			FarmerGroupFarmerHome home = new FarmerGroupFarmerHome();
			PlantHome pHome = new PlantHome();
			Plant plant = null;
			sf.getCurrentSession().beginTransaction();	
			int canDel = 0;
			for (int i=0; i<farmerGroupList.size(); i++) {
				FarmerGroupFarmer farmerGroup = (FarmerGroupFarmer)farmerGroupList.get(i);
				if (farmerGroup.getCheckBox()==true){
					farmerGroup.setFarmerGroupId(eform.getFarmerGroupId());
					plant = pHome.findByFarmerGroupId(farmerGroup.getFarmerGroupId(),farmerGroup.getIdCard());
					if( plant != null ){
						eform.setMsg("กลุ่มเกษตรกร " + eform.getFarmerGroupName() + "มีการใช้งานอยู่ในขณะนี้");
						log.info("กลุ่มเกษตรกร " + eform.getFarmerGroupName() + "มีการใช้งานอยู่ในขณะนี้");
						canDel = 0;
						break;
					} else {
						home.delFarmerGroup(farmerGroup.getFarmerGroupId(),farmerGroup.getIdCard());
						canDel++;
					}
				}		
			}
			
			if(canDel > 0){
				sf.getCurrentSession().getTransaction().commit();
				log.info("ลบกลุ่มเกษตรกร " + (eform.getDelfarmerGroupName().length - 1) + " records");
			}
			
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}
	
	private ActionForward addFarmerToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerGroupFarmerForm eform = (FarmerGroupFarmerForm)form;
		HttpSession session = request.getSession();
		List farmerList = (List) session.getAttribute("farmerList");
		GridUtil.applyCheckValue(farmerList, eform.getDelFarmer(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try{
			sf.getCurrentSession().beginTransaction();	
			FarmerGroupFarmerHome home = new FarmerGroupFarmerHome();
			for (int i=0; i<farmerList.size(); i++) {
				FarmerGroupFarmer farmerGroup = (FarmerGroupFarmer)farmerList.get(i);
				if (farmerGroup.getCheckBox()==true){
					farmerGroup.setFarmerGroupId(eform.getFarmerGroupId());
					home.save(farmerGroup);
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));
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
