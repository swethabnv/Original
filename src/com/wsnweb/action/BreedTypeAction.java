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
import com.wsndata.data.BreedType;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsnweb.form.BreedTypeForm;
import com.wsnweb.util.Utility;

public class BreedTypeAction extends Action {
	private static final Logger log = Logger.getLogger(BreedTypeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BreedTypeForm eform = (BreedTypeForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return save(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				return edit(mapping, form, request, response);
			} else if("GetBreedType".equals(eform.getCmd())){
				return getBreedType(mapping, form, request, response);
			} else {
				return mapping.findForward("breedType");
			}
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		BreedTypeForm eform = (BreedTypeForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedTypeHome home = new BreedTypeHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			BreedType btObj = home.findByBreedTypeId(eform.getBreedTypeId());
			if (btObj != null) {
				BreedType btName = home.findByBreedTypeName(eform.getBreedTypeName());
				if (btName != null && !eform.getBreedTypeName().equals(btObj.getBreedTypeName())) {
					msg = "มีพืชประเภท '"+eform.getBreedTypeName()+"' อยู่แล้วในระบบ";
				} else {
				eform.loadToBean(btObj);
				btObj.setLastUpdateBy(userLogin.getUserName());
				btObj.setLastUpdateDate(new Date());
				home.saveOrUpdate(btObj);
				sf.getCurrentSession().getTransaction().commit();
				msg = Utility.get("SAVE_SUCCESS");
				}
			} else {
				// create New BreedType
				BreedType btName = home.findByBreedTypeName(eform.getBreedTypeName());
				if (btName != null) {
					msg = "มีพืชประเภท '"+eform.getBreedTypeName()+"' อยู่แล้วในระบบ";
				} else {
				btObj = new BreedType();
				eform.loadToBean(btObj);
				btObj.setLastUpdateBy(userLogin.getUserName());
				btObj.setLastUpdateDate(new Date());
				home.saveOrUpdate(btObj);
				sf.getCurrentSession().getTransaction().commit();
				msg = Utility.get("SAVE_SUCCESS");
				}
			}
			eform.setMsg(msg);
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("breedType");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BreedTypeForm eform = (BreedTypeForm) form;
		try {
			request.setAttribute("BreedTypeForm", eform);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));
			e.printStackTrace();
			log.error(e, e);
		} 
		return mapping.findForward("breedType");
	}
	
	//Ajax Method
	public ActionForward getBreedType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedTypeForm eform = (BreedTypeForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedTypeHome home = new BreedTypeHome();		
			BreedType bType = home.findByBreedTypeName(eform.getBreedTypeName());
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"breedTypeId", "lastUpdateDate", "lastUpdateBy", "breedGroup", "maxPerYear"});
		    String result = JSONArray.fromObject(bType, jsonConfig).toString();
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
