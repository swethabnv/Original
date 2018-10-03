package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.wsndata.data.BreedType;
import com.wsndata.data.PlantMethod;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.PlantMethodHome;
import com.wsnweb.form.PlantMethodForm;
import com.wsnweb.util.Utility;

public class PlantMethodAction extends Action {

	private static final Logger log = Logger.getLogger(PlantMethodAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PlantMethodForm eform = (PlantMethodForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return save(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				return edit(mapping, form, request, response);
			} else if ("GetBreedGroup".equals(eform.getCmd())) {
				return getBreedGroupInfo(mapping, eform, request, response);
			} else {
				return load(mapping, form, request, response);
			}
		}
	}

	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PlantMethodForm eform = (PlantMethodForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantMethodHome pHome = new PlantMethodHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			PlantMethod pmObj = pHome.findByPlantMethodId(eform.getPlantMethodId());
			if (pmObj != null) {
				PlantMethod Obj = pHome.findByPlantMethodName(eform.getPlantMethodName());
				if(Obj!=null && !eform.getPlantMethodName().equals(pmObj.getPlantMethodName())) {
					eform.setMsg(Utility.get("SAVE_DUPLICATE"));
					sf.getCurrentSession().getTransaction().rollback();
					return mapping.findForward("plantMethod");
				}
				eform.loadToBean(pmObj);
				pHome.update(pmObj);
			} else {
				PlantMethod Obj = pHome.findByPlantMethodName(eform.getPlantMethodName());
				if(Obj!=null) {
					eform.setMsg(Utility.get("SAVE_DUPLICATE"));
					sf.getCurrentSession().getTransaction().rollback();
					return mapping.findForward("plantMethod");
				}
				pmObj = new PlantMethod();

				eform.loadToBean(pmObj);
				pHome.persist(pmObj);
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}

		return mapping.findForward("plantMethod");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		PlantMethodForm eform = (PlantMethodForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			PlantMethodHome home = new PlantMethodHome();
			PlantMethod pmObj = home.findByPlantMethodId(eform.getPlantMethodId());
			eform.loadFromBean(pmObj);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("plantMethod");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PlantMethodForm eform = (PlantMethodForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		List<BreedType> breedTypeList = new ArrayList<BreedType>();
		List<BreedGroup> breedGroupList = new ArrayList<BreedGroup>();
		List<PlantMethod> plantMethodList = new ArrayList<PlantMethod>();
		try {
			sf.getCurrentSession().beginTransaction();
			BreedTypeHome breedTypeHome = new BreedTypeHome();

			//--- ดึงชนิดพืช ออกมา --- //
			BreedType breedType = new BreedType();
			breedType.setBreedTypeId(0);
			breedType.setBreedTypeName("กรุณาเลือก");
			breedTypeList = breedTypeHome.findAll();
			if(breedTypeList !=null && breedTypeList.size()>0)
				breedTypeList.add(0,breedType);
			//--- ดึงพันธุ์พืช ออกมา --- //
			BreedGroup breedGroup = new BreedGroup();
			breedGroup.setBreedGroupId(0);
			breedGroup.setBreedGroupName("กรุณาเลือก");
			breedGroupList.add(0,breedGroup);

			PlantMethodHome plantMethodHome = new PlantMethodHome();
			plantMethodList = plantMethodHome.findAll();
			
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("breedGroupPlantList", breedGroupList);
			session.setAttribute("plantMethodList", plantMethodList);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("plantMethod");
	}
	
	public ActionForward getBreedGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantMethodForm eform = (PlantMethodForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();		
			List<BreedGroup> breedGroup = null;
			if(!"".equals(eform.getBreedTypeId()))
				breedGroup = home.findByBreedTypeId(eform.getBreedTypeId());
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"breedTypeId", "period", "plantPeriodFrom", "plantPeriodTo", "forcastPeriodFrom", "forcastPeriodTo", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});
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
	
}
