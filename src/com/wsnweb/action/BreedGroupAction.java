package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.wsndata.data.BreedGroup;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsnweb.form.BreedGroupForm;
import com.wsnweb.util.Utility;

public class BreedGroupAction extends Action {
	private static final Logger log = Logger.getLogger(BreedGroupAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		BreedGroupForm eform = (BreedGroupForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 return edit(mapping, form, request, response);
			 } else if( "GetBreedGroup".equals(eform.getCmd()) ){
				 return getBreedGroup(mapping, form, request, response);
			 } else if( "GetBreedGroupList".equals(eform.getCmd())){
				 return getBreedGroupList(mapping, form, request, response);
			 } else{
				 return mapping.findForward("breedGroup"); 
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		BreedGroupForm eform = (BreedGroupForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = ""; // Utility.get("SAVE_FAIL");  "การบันทึกล้มเหลว";
		try{			
			sf.getCurrentSession().beginTransaction();			
			BreedGroupHome home = new BreedGroupHome();
			BreedGroup bgObj = null;
			if(eform.getBreedGroupId()>0)
				bgObj = home.findByBreedGroupId(eform.getBreedGroupId());
			else
				bgObj = home.findByBreedGroupName(eform.getBreedGroupName(), eform.getBreedTypeId());
			
			// breedgroup has value
			if(bgObj !=null)
			{
					int canSave = 1;
					
					if(bgObj.getBreedTypeId() != eform.getBreedTypeId()){
						if (home.findbyTable("PlantDetail", bgObj.getBreedGroupId())
								&& home.findbyTable("ProductionForecast", bgObj.getBreedGroupId())) {
							//home.delete(bgObj);
							sf.getCurrentSession().flush();
							home.deleteBreedGroup(bgObj.getBreedTypeId(), bgObj.getBreedGroupId());
							sf.getCurrentSession().getTransaction().commit();
							sf.getCurrentSession().beginTransaction();
							//canSave++;
						}else{
							canSave = 0;
							msg = bgObj.getBreedGroupName() +"ที่อยู่ในประเภท" +bgObj.getBreedType().getBreedTypeName()+" มีการใช้งานอยู่ในขณะนี้ จึงไม่สามารถเปลี่ยนชนิดพืชได้";
						}		
					}else {
						 // breedtypeId is the same, breed group name is the same but breedgroupid is different
					     //	BreedGroup bgObj1 = home.findByBreedGroupName(eform.getBreedGroupName(), eform.getBreedTypeId());
					     //	if(bgObj1 != null){
						//	canSave = 0;
						//	msg = "ไม่สามารถบันทึกได้เนื่องจากมีกลุ่มพันธุ์ '" + bgObj.getBreedGroupName() +"' อยู่แล้วในระบบ";
						//}
					}

			
					eform.loadToBean(bgObj);
					if (!"0".equals(eform.getPlantPeriodFrom())) {
						bgObj.setPlantPeriodFrom(eform.getPlantPeriodFrom());
					}
					if (!"0".equals(eform.getPlantPeriodTo())) {
						bgObj.setPlantPeriodTo(eform.getPlantPeriodTo());
					}
					if (!"0".equals(eform.getForcastPeriodFrom())) {
						bgObj.setForcastPeriodFrom(eform.getForcastPeriodFrom());
					}
					if (!"0".equals(eform.getForcastPeriodTo())) {
						bgObj.setForcastPeriodTo(eform.getForcastPeriodTo());
					}
					
					bgObj.setLastUpdateBy(userLogin.getUserName());
					bgObj.setLastUpdateDate(new Date());
					
					if(canSave > 0){
						home.saveOrUpdate(bgObj);
						sf.getCurrentSession().getTransaction().commit();
						msg = Utility.get("SAVE_SUCCESS");
					}
					//else{
						//msg = bgObj.getBreedGroupName() +"ที่อยู่ในประเภท" +bgObj.getBreedType().getBreedTypeName()+" มีการใช้งานอยู่ในขณะนี้ จึงไม่สามารถเปลี่ยนชนิดพืชได้";
					//}
			}else{
				bgObj = home.findByBreedGroupName(eform.getBreedGroupName(),eform.getBreedTypeId());
				if(bgObj !=null){
					msg = Utility.get("SAVE_DUPLICATE");
				}else{
				  // create New BreedGroup
					bgObj = new BreedGroup();
					eform.loadToBean(bgObj);
					bgObj.setBreedGroupId(home.getMaxId()+1);
					if (!"0".equals(eform.getPlantPeriodFrom())) {
						bgObj.setPlantPeriodFrom(eform.getPlantPeriodFrom());
					}
					if (!"0".equals(eform.getPlantPeriodTo())) {
						bgObj.setPlantPeriodTo(eform.getPlantPeriodTo());
					}
					if (!"0".equals(eform.getForcastPeriodFrom())) {
						bgObj.setForcastPeriodFrom(eform.getForcastPeriodFrom());
					}
					if (!"0".equals(eform.getForcastPeriodTo())) {
						bgObj.setForcastPeriodTo(eform.getForcastPeriodTo());
					}
					bgObj.setLastUpdateBy(userLogin.getUserName());
					bgObj.setLastUpdateDate(new Date());
				
					home.saveOrUpdate(bgObj);	  
					sf.getCurrentSession().getTransaction().commit();
					msg = Utility.get("SAVE_SUCCESS"); 
				}
			}
			eform.setMsg(msg); // alert message
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL"));
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("breedGroup");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		BreedGroupForm eform = (BreedGroupForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try{			
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome bhome = new  BreedGroupHome();
			BreedGroup obj = bhome.findByBreedGroupId(eform.getBreedGroupId());
			eform.loadFromBean(obj);
			eform.setPlantPeriodFrom(obj.getPlantPeriodFrom()!=null?obj.getPlantPeriodFrom():"0");
			eform.setPlantPeriodTo(obj.getPlantPeriodTo()!=null?obj.getPlantPeriodTo():"0");
			eform.setForcastPeriodFrom(obj.getForcastPeriodFrom()!=null?obj.getForcastPeriodFrom():"0");
			eform.setForcastPeriodTo(obj.getForcastPeriodTo()!=null?obj.getForcastPeriodTo():"0");
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("breedGroup");
	}
	
	//Ajax Method
	public ActionForward getBreedGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedGroupForm eform = (BreedGroupForm)form;
		BreedGroup bGroup = null, breedGroup = null;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();
			
			if(eform.getBreedGroupId() != 0)
				bGroup = home.findByBreedGroupId(eform.getBreedGroupId());
			
			
			
			if(eform.getBreedGroupId()==0 || (bGroup!=null && (!bGroup.getBreedGroupName().equals(eform.getBreedGroupName()) || bGroup.getBreedTypeId()!=eform.getBreedTypeId())))
					breedGroup = home.findByBreedGroupName(eform.getBreedGroupName(), eform.getBreedTypeId());
			
			
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"breedGroupId","period", "plantPeriodFrom", "plantPeriodTo", "forcastPeriodFrom", "forcastPeriodTo", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});
		    String result = JSONArray.fromObject(breedGroup, jsonConfig).toString();
		
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
	
	
	
	//Ajax Method
	public ActionForward getBreedGroupList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedGroupForm eform = (BreedGroupForm)form;
		List<BreedGroup> bGroupList = new ArrayList<BreedGroup>();
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();
		
			
			if(eform.getBreedTypeId() > 0 )
				bGroupList = home.findByBreedTypeId(eform.getBreedTypeId());
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"period", "plantPeriodFrom", "plantPeriodTo", "forcastPeriodFrom", "forcastPeriodTo", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});
		    String result = JSONArray.fromObject(bGroupList, jsonConfig).toString();
		    
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
