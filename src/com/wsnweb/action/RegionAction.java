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
import com.wsndata.data.Region;
import com.wsndata.data.User;
import com.wsndata.dbaccess.RegionHome;
import com.wsnweb.form.RegionForm;
import com.wsnweb.util.Utility;

public class RegionAction extends Action {
	private static final Logger log = Logger.getLogger(RegionAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		RegionForm eform = (RegionForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 return edit(mapping, form, request, response);
			 } else if ("GetRegion".equals(eform.getCmd())){
				 return getRegion(mapping, form, request, response);
		     }else{
				 return mapping.findForward("region"); 
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		RegionForm eform = (RegionForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		RegionHome home = new RegionHome();
		String regionName = "";
		long regionNo = 0;
		try{			
			sf.getCurrentSession().beginTransaction();			
			regionName = eform.getRegionName() == null?"":eform.getRegionName().trim();
			if(!"".equals(eform.getRegionNo()))
			    regionNo = Long.parseLong(eform.getRegionNo()==null?"0":eform.getRegionNo());
			Region regionList = home.findByName(regionName);
			if(regionList!=null && regionName.equals(regionList.getRegionName())) {
				eform.setMsg("ไม่สามารถบันทึกได้ มี"+regionName+"อยู่แล้วในระบบ!");
			} else {
				Region regionObj = home.searchByNo(regionNo);
				if(regionObj ==null)
					regionObj = new Region();
				regionObj.setRegionName(regionName);
			    regionObj.setLastUpdateBy(userLogin.getUserName());
				regionObj.setLastUpdateDate(new Date());
				
				home.saveOrUpdate(regionObj);	  
				sf.getCurrentSession().getTransaction().commit();
				eform.setMsg(Utility.get("SAVE_SUCCESS")); //eform.setMsg("Save Region Successful.");
			}
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL")); //eform.setMsg("Save failed.");
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("region");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		RegionForm eform = (RegionForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try{			
			sf.getCurrentSession().beginTransaction();
			eform.setRegionName(eform.getRegionName());
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //eform.setMsg("Load Data failed.");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("region");
	}
	
	//Ajax Method
	public ActionForward getRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			RegionForm eform = (RegionForm)form;
			long regionNo = 0;
			try{
				Region reg = null, regObj = null;
				sf.getCurrentSession().beginTransaction();
				RegionHome home = new RegionHome();;
			    
				regionNo = Long.parseLong(eform.getRegionNo().equals("")?"0":eform.getRegionNo());
				if(regionNo != 0)
					reg = home.searchByNo(regionNo);
				if((reg!=null && !reg.getRegionName().equals(eform.getRegionName())) || regionNo == 0)
					regObj = home.findByName(eform.getRegionName());
			      
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{"lastUpdateDate", "lastUpdateBy", "province", "checkBox", "linkImageEdit", "linkImageDel"});
			    String result = JSONArray.fromObject(regObj, jsonConfig).toString();
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
