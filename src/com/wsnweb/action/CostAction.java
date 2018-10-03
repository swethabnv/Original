package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Cost;
import com.wsndata.data.User;
import com.wsndata.dbaccess.CostHome;
import com.wsnweb.form.CostForm;
import com.wsnweb.util.Utility;

public class CostAction extends Action {
	private static final Logger log = Logger.getLogger(CostAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		CostForm eform = (CostForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 return edit(mapping, form, request, response);
			 } else if( "GetCost".equals(eform.getCmd())){
				 return getCost(mapping, form, request, response);
			 } else{
				 return mapping.findForward("cost"); 
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CostForm eform = (CostForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		CostHome home = new CostHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try{			
			sf.getCurrentSession().beginTransaction();			
			Cost btObj = home.findByCostId(eform.getCostId());
			if(btObj !=null){
				Cost Obj = home.findByCostName(eform.getCostName());
				if(Obj !=null && !eform.getCostName().equals(btObj.getCostName())){
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					throw new Exception();
				}
				btObj.setCostName(eform.getCostName());
				btObj.setLastUpdateBy(userLogin.getUserName());
				btObj.setLastUpdateDate(new Date());
			}else{
				// create New Cost
				btObj = home.findByCostName(eform.getCostName());
				if(btObj !=null){
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					throw new Exception();
				}
				btObj = new Cost();
				btObj.setCostName(eform.getCostName());
				btObj.setLastUpdateBy(userLogin.getUserName());
				btObj.setLastUpdateDate(new Date());
			}
			home.saveOrUpdate(btObj);	  
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");	
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("cost");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CostForm eform = (CostForm)form;
		try{			
			request.setAttribute("CostForm", eform);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}
		return mapping.findForward("cost");
	}
	
	//Ajax Method
	public ActionForward getCost(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			CostForm eform = (CostForm)form;
			Cost cost = null, costObj = null;
			try{

				sf.getCurrentSession().beginTransaction();
				CostHome home = new CostHome();
				if(eform.getCostId()!=0)
					costObj = home.findByCostId(eform.getCostId());
				if(eform.getCostId()==0 || (costObj!=null && !costObj.getCostName().equals(eform.getCostName())))
					cost = home.findByCostName(eform.getCostName());
			
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();
			    jsonConfig.setExcludes(new String[]{ "lastUpdateDate", "lastUpdateBy", "costDetail", "checkBox", "linkImageEdit"});
			    String result = JSONArray.fromObject(cost, jsonConfig).toString();
			    result = result.trim().replaceAll("&nbsp;", " ");
			    wt.print(result);
			    wt.flush();
			    wt.close();
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}finally{
				sf.getCurrentSession().close();
			}
			return null;
	}
}
