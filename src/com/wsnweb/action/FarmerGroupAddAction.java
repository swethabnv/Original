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
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerGroupFarmerHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsnweb.form.FarmerGroupAddForm;
import com.wsnweb.util.Utility;

public class FarmerGroupAddAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerGroupAddAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FarmerGroupAddForm eform = (FarmerGroupAddForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Add".equals(eform.getCmd())){
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
		FarmerGroupAddForm eform = (FarmerGroupAddForm)form;
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
			FarmerGroupHome ghome = new FarmerGroupHome();
			
			sf.getCurrentSession().beginTransaction();
			
			String idCard = eform.getIdCard()==null?"":eform.getIdCard();
			String firstName = eform.getFirstName()==null?"":eform.getFirstName();
			String lastName = eform.getLastName()==null?"":eform.getLastName();
			groupfList = fghome.findByfarmerGroup(eform.getFarmerGroupId());
			getList=home.findByCriteria(idCard,firstName,lastName,branchList,0);
			
			for (int i = 0; i < getList.size(); i++) 
			{
				Object[] farmerObj = (Object[]) getList.get(i);
				FarmerGroupFarmer farmer = new FarmerGroupFarmer();
				farmer.setIdCard(farmerObj[1].toString());
				farmer.setEffectiveDate(df.parse(farmerObj[2].toString()));
				farmer.setFirstName(farmerObj[4].toString());
				farmer.setLastName(farmerObj[5].toString());
				farmerList.add(farmer); //FarmerGroupFarmer
				
				for (int j = 0; j < groupfList.size(); j++) 
				{
					FarmerGroupFarmer fg = (FarmerGroupFarmer)groupfList.get(j);
					if (fg.getIdCard().equals(farmerObj[1].toString()))
					{
						groupfList.remove(j);
						farmerGroupList.add(farmer);
						farmerList.remove(farmer);
						break;
					}
				}
			}
			
			eform.setFarmerGroupName(ghome.findByFarmerGroupId(eform.getFarmerGroupId()).getFarmerGroupName());
			session.setAttribute("farmerGroupList", farmerGroupList);
			session.setAttribute("farmerList", farmerList);
			session.setAttribute("countFarmer", farmerList!=null?farmerList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("farmerGroupAdd");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmerGroupAddForm eform = (FarmerGroupAddForm)form;
        HttpSession session = request.getSession();
        List farmerList = (List) session.getAttribute("farmerList");
        Collections.sort(farmerList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
        GridUtil.applyCheckValue(farmerList, eform.getDelFarmer(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        return mapping.findForward("farmerGroupAdd");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerGroupAddForm eform = (FarmerGroupAddForm)form;
		HttpSession session = request.getSession();
		List farmerList = (List) session.getAttribute("farmerList");
		GridUtil.applyCheckValue(farmerList, eform.getDelFarmer(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		try {
			sf.getCurrentSession().beginTransaction();	
			FarmerGroupFarmerHome home = new FarmerGroupFarmerHome();
			for (int i=0; i<farmerList.size(); i++) 
			{
				FarmerGroupFarmer farmerGroup = (FarmerGroupFarmer)farmerList.get(i);
				if (farmerGroup.getCheckBox() == true)
				{
					farmerGroup.setFarmerGroupId(eform.getFarmerGroupId());
					home.delete(farmerGroup);
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));
		} catch(Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		} finally {
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}
	
	private ActionForward addFarmerToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerGroupAddForm eform = (FarmerGroupAddForm)form;
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
		return mapping.findForward("farmerGroupAdd");
	}
}
