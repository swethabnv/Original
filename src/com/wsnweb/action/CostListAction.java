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
import com.wsndata.data.Cost;
import com.wsndata.data.CostDetail;
import com.wsndata.data.User;
import com.wsndata.dbaccess.CostHome;
import com.wsnweb.form.CostListForm;

public class CostListAction extends Action {
	private static final Logger log = Logger.getLogger(CostListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 CostListForm eform = (CostListForm)form;
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
		CostListForm eform = (CostListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List costList = new ArrayList();
		String costName= "";
		try{
			CostHome home = new CostHome();
			sf.getCurrentSession().beginTransaction();
			costName = eform.getCostName()==null?"":eform.getCostName();
			if("".equals(costName))
				costList=home.findAll();  			    
			else
				costList=home.searchByCostName(costName); 
			
			Collections.sort(costList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("costList", costList);
			session.setAttribute("countCost", costList!=null?costList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("costList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CostListForm eform = (CostListForm)form;
        HttpSession session = request.getSession();
        List costList = (List) session.getAttribute("costList");
        GridUtil.applyCheckValue(costList, eform.getDelCost(), "costId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(costList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("costList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		CostListForm eform = (CostListForm)form;
		HttpSession session = request.getSession();
		List costList = (List) session.getAttribute("costList");
		GridUtil.applyCheckValue(costList, eform.getDelCost(), "costId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		CostHome costHome = new CostHome();//User
		try{
			sf.getCurrentSession().beginTransaction();	
			CostHome home = new CostHome();
			for (int i=0; i<costList.size(); i++) {
				Cost cost = (Cost)costList.get(i);
				if (cost.getCheckBox()==true){
					List<CostDetail> costUsedList = costHome.findInCostDetail(cost.getCostId());
					if(costUsedList != null && costUsedList.size() > 0){
						eform.setErrMessage(cost.getCostName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
						home.delete(cost);
					}
				}
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบข้อมูลค่าใช้จ่ายไปทั้งสิ้น " + eform.getDelCost().length + " records");
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
