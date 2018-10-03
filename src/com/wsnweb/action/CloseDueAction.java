package com.wsnweb.action;

import java.util.Date;

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
import com.wsndata.data.CloseDue;
import com.wsndata.data.User;
import com.wsndata.dbaccess.CloseDueHome;
import com.wsnweb.form.CloseDueForm;
import com.wsnweb.util.Utility;

public class CloseDueAction extends Action {
	private static final Logger log = Logger.getLogger(CloseDueAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		CloseDueForm eform = (CloseDueForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 return edit(mapping, form, request, response);
			 } else{
				 return mapping.findForward("closeDue"); 
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		CloseDueForm eform = (CloseDueForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		CloseDueHome home = new CloseDueHome();
		CloseDue cdObj = null;
		long closeDueId = 0, plantYear = 0, plantNo = 0, farmerGroupId = 0;
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try{
			sf.getCurrentSession().beginTransaction();
			closeDueId = eform.getCloseDueId();
			plantYear = eform.getPlantYear();
			plantNo = eform.getPlantNo();
			farmerGroupId = eform.getFarmerGroupId();
			if(eform.getCloseDueId() > 0) {
				cdObj = home.findByCloseDueId(closeDueId);
				CloseDue checkDup = home.findByCriteria(plantYear, plantNo, farmerGroupId);
				if(checkDup != null && cdObj.getCloseDueId()!=checkDup.getCloseDueId()){
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
				} else {
					// Edit CloseDue
					cdObj.setPlantYear(plantYear);
					cdObj.setPlantNo(plantNo);
					cdObj.setFarmerGroupId(farmerGroupId);
					cdObj.setLastUpdateBy(userLogin.getUserName());
					cdObj.setLastUpdateDate(new Date());
					home.saveOrUpdate(cdObj);	
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");	
				}  
			} else {
				cdObj = home.findByCriteria(plantYear, plantNo, farmerGroupId);
				if(cdObj != null) {
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
				} else {
					// Create New CloseDue
					cdObj = new CloseDue();
					cdObj.setPlantYear(eform.getPlantYear());
					cdObj.setPlantNo(plantNo);
					cdObj.setFarmerGroupId(farmerGroupId);
					cdObj.setCreateBy(userLogin.getUserName());
					cdObj.setCreateDate(new Date());
					cdObj.setLastUpdateBy(userLogin.getUserName());
					cdObj.setLastUpdateDate(new Date());
					home.persist(cdObj);
					msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");	
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("closeDue");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CloseDueForm eform = (CloseDueForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		CloseDueHome home = new CloseDueHome();
		try{
			sf.getCurrentSession().beginTransaction();
			CloseDue cdObj = home.findByCloseDueId(eform.getCloseDueId());
			if(cdObj != null) {
				eform.setPlantYear(cdObj.getPlantYear());
				eform.setPlantNo(cdObj.getPlantNo());
				eform.setFarmerGroupId(cdObj.getFarmerGroupId());
			}
			request.setAttribute("CloseDueForm", eform);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}
		return mapping.findForward("closeDue");
	}
}
