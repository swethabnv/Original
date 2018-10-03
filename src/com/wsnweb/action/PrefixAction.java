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
import com.wsndata.data.Prefix;
import com.wsndata.data.User;
import com.wsndata.dbaccess.PrefixHome;
import com.wsnweb.form.PrefixForm;
import com.wsnweb.util.Utility;

public class PrefixAction extends Action {
	private static final Logger log = Logger.getLogger(PrefixAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrefixForm eform = (PrefixForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return save(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				return edit(mapping, form, request, response);
			} else if("GetPrefix".equals(eform.getCmd())){
				return getPrefix(mapping, eform, request, response);
			} else {
				return mapping.findForward("prefix");
			}
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrefixForm eform = (PrefixForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		PrefixHome home = new PrefixHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			Prefix prefixObj = home.findByAbbrPrefix(eform.getPrefix());
			if(eform.getPrevPrefix()!=null && !eform.getPrevPrefix().equals("")) {
				if(!eform.getPrefix().equals(eform.getPrevPrefix())) {
					Prefix prefixOld = home.findByAbbrPrefix(eform.getPrevPrefix());
					home.delete(prefixOld);
				}
			}
			if (prefixObj != null) {
				if ("Edit".equals(eform.getCmd())) {
					//home.delete(prefixObj);
					//sf.getCurrentSession().getTransaction().commit();
					if(eform.getPrefix().equals(eform.getPrevPrefix())) {
						eform.loadToBean(prefixObj);
						prefixObj.setLastUpdateBy(userLogin.getUserName());
						prefixObj.setLastUpdateDate(new Date());
						home.saveOrUpdate(prefixObj);
						sf.getCurrentSession().getTransaction().commit();
						msg = Utility.get("SAVE_SUCCESS");//msg = "บันทึกสำเร็จ";
					} else {
						msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					}
				}else{
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
				}
				eform.setPrevPrefix(eform.getPrevPrefix());
			} else {
				// create New Prefix
				prefixObj = new Prefix();
				eform.loadToBean(prefixObj);
				prefixObj.setLastUpdateBy(userLogin.getUserName());
				prefixObj.setLastUpdateDate(new Date());
				home.saveOrUpdate(prefixObj);
				sf.getCurrentSession().getTransaction().commit();
				msg = Utility.get("SAVE_SUCCESS");//msg = "บันทึกสำเร็จ";
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
		return mapping.findForward("prefix");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrefixForm eform = (PrefixForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			PrefixHome pfHome = new PrefixHome();
			Prefix pfObj = pfHome.findByAbbrPrefix(eform.getPrefix());
			eform.loadFromBean(pfObj);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("prefix");
	}
	
	//Ajax Method
	public ActionForward getPrefix(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			PrefixForm eform = (PrefixForm)form;
			Prefix pref = null;
			try{
				sf.getCurrentSession().beginTransaction();
				PrefixHome home = new PrefixHome();
			    
				pref = home.findByAbbrPrefix(eform.getPrefix());
			      
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{"lastUpdateDate", "lastUpdateBy", "checkBox", "linkImageEdit"});
			    String result = JSONArray.fromObject(pref, jsonConfig).toString();
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
