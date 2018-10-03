package com.wsnweb.action;

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
import com.wsndata.data.Bank;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BankBranchHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.BankHome;
import com.wsnweb.form.BankListForm;

public class BankListAction extends Action {
	private static final Logger log = Logger.getLogger(BankListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 BankListForm eform = (BankListForm)form;
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
		BankListForm eform = (BankListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = null;
		String bankName= "";
		try{
			BankHome home = new BankHome();
			sf.getCurrentSession().beginTransaction();
			bankName = eform.getBankName()==null?"":eform.getBankName();
			eform.setBankName(bankName);
			if("".equals(bankName))
				getList=home.findAll();  			    
			else
				getList=home.searchByBankName(bankName); 
			Collections.sort(getList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("bankList", getList);
			session.setAttribute("countBank", getList!=null?getList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("bankList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BankListForm eform = (BankListForm)form;
        HttpSession session = request.getSession();
        List bankList = (List) session.getAttribute("bankList");
        GridUtil.applyCheckValue(bankList, eform.getDelBankId(), "bankId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(bankList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("bankList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		BankListForm eform = (BankListForm)form;
		HttpSession session = request.getSession();
		List bankList = (List) session.getAttribute("bankList");
		GridUtil.applyCheckValue(bankList, eform.getDelBankId(), "bankId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			BankBranchHome bHome = new BankBranchHome();
			PlantHome pHome = new PlantHome();//Plant
			
			for (int i=0; i<bankList.size(); i++) {
				Bank bank = (Bank)bankList.get(i);
				if (bank.getCheckBox()==true){
					//สร้างList
					
					List bankBranchList = bHome.searchByBankId(bank.getBankId());
					
					if(bankBranchList !=null && bankBranchList.size() > 0){
						List plantBankList =  pHome.findByBankId(bank.getBankId());
						if(plantBankList !=null && plantBankList.size() > 0) {
							eform.setErrMessage(bank.getBankName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}else{
							bHome.delete(bank);
						}
					}
				}
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบธนาคารไปทั้งสิ้น " + eform.getDelBankId().length + " records");
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
