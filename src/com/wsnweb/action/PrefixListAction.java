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
import com.wsndata.data.Prefix;
import com.wsndata.data.User;
import com.wsndata.dbaccess.PrefixHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.PrefixListForm;

public class PrefixListAction extends Action {
	private static final Logger log = Logger.getLogger(PrefixListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 PrefixListForm eform = (PrefixListForm)form;
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
		PrefixListForm eform = (PrefixListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List prefixList = new ArrayList();
		String prefixName= "";
		try{
			PrefixHome home = new PrefixHome();
			sf.getCurrentSession().beginTransaction();
			prefixName = eform.getPrefix()==null?"":eform.getPrefix();
			if("".equals(prefixName))
				prefixList=home.findAll();  			    
			else
				prefixList=home.searchByAbbrPrefix(prefixName); 
			
			Collections.sort(prefixList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("prefixList", prefixList);
			session.setAttribute("countPrefix", prefixList!=null?prefixList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("prefixList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrefixListForm eform = (PrefixListForm)form;
        HttpSession session = request.getSession();
        List prefixList = (List) session.getAttribute("prefixList");
        GridUtil.applyCheckValue(prefixList, eform.getDelPrefix(), "abbrPrefix", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(prefixList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("prefixList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		PrefixListForm eform = (PrefixListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List prefixList = (List) session.getAttribute("prefixList");
		GridUtil.applyCheckValue(prefixList, eform.getDelPrefix(), "abbrPrefix", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		UserHome userHome = new UserHome();//User
		try{
			sf.getCurrentSession().beginTransaction();	
			PrefixHome home = new PrefixHome();
			for (int i=0; i<prefixList.size(); i++) {
				Prefix prefix = (Prefix)prefixList.get(i);
				if (prefix.getCheckBox()==true){
					if (home.findBy(prefix.getAbbrPrefix()).size()>0) {
						eform.setErrMessage(prefix.getAbbrPrefix()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					List<User> uUserList = userHome.findByPrefix(prefix.getAbbrPrefix());
					if(uUserList != null && uUserList.size() > 0){
						eform.setErrMessage(prefix.getAbbrPrefix()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
						
						home.delete(prefix);
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบคำนำหน้าไปทั้งสิ้น " + eform.getDelPrefix().length + " records");
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
