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
import com.wsndata.data.BreedType;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsnweb.form.BreedTypeListForm;

public class BreedTypeListAction extends Action {
	private static final Logger log = Logger.getLogger(BreedTypeListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 BreedTypeListForm eform = (BreedTypeListForm)form;
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
		BreedTypeListForm eform = (BreedTypeListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List breedTypeList = new ArrayList();
		String breedTypeName= "";
		try{
			BreedTypeHome home = new BreedTypeHome();
			sf.getCurrentSession().beginTransaction();
			breedTypeName = eform.getBreedTypeName()==null?"":eform.getBreedTypeName();
			if("".equals(breedTypeName))
				breedTypeList=home.findAll();  			    
			else
				breedTypeList=home.searchByBreedTypeName(breedTypeName); 
			
			Collections.sort(breedTypeList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("breedTypeList", breedTypeList);
			session.setAttribute("countBreedType", breedTypeList!=null?breedTypeList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e.getMessage());
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("breedTypeList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BreedTypeListForm eform = (BreedTypeListForm)form;
        HttpSession session = request.getSession();
        List breedTypeList = (List) session.getAttribute("breedTypeList");
        GridUtil.applyCheckValue(breedTypeList, eform.getDelBreedType(), "breedTypeId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(breedTypeList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("breedTypeList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedTypeListForm eform = (BreedTypeListForm)form;
		HttpSession session = request.getSession();
		List breedTypeList = (List) session.getAttribute("breedTypeList");
		GridUtil.applyCheckValue(breedTypeList, eform.getDelBreedType(), "breedTypeId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			BreedTypeHome home = new BreedTypeHome();
			for (int i=0; i<breedTypeList.size(); i++) {
				BreedType breedType = (BreedType)breedTypeList.get(i);
				if (breedType.getCheckBox()==true){
					if (home.findbyTable("PlantDetail", breedType.getBreedTypeId())
							&& home.findbyTable("BreedGroup", breedType.getBreedTypeId())
							&& home.findbyTable("EconomicBreed", breedType.getBreedTypeId())
							&& home.findbyTable("ProductionForecast", breedType.getBreedTypeId())) {
						home.delete(breedType);
					}else{
						eform.setErrMessage(breedType.getBreedTypeName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
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
