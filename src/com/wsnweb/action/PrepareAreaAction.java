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
import com.wsndata.data.BreedType;
import com.wsndata.data.PrepareArea;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.PrepareAreaHome;
import com.wsnweb.form.PrepareAreaForm;
import com.wsnweb.util.Utility;

public class PrepareAreaAction extends Action {

	private static final Logger log = Logger.getLogger(PrepareAreaAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrepareAreaForm eform = (PrepareAreaForm) form;
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
		PrepareAreaForm eform = (PrepareAreaForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		PrepareAreaHome pHome = new PrepareAreaHome();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			PrepareArea bObj = pHome.findByPrepareAreaId(eform.getPrepareAreaId());
			if (bObj != null) {
				List<PrepareArea> prepareAreaList = null;
				//PrepareArea Obj = pHome.findByPrepareAreaName(eform.getPrepareAreaName(), eform.getPprepareAreaId());
				if(eform.getPrepareAreaId()==eform.getPprepareAreaId())
					prepareAreaList = pHome.searchByPrepareAreaName(eform.getPrepareAreaName(), 0);
				else
					prepareAreaList = pHome.searchByPrepareAreaName(eform.getPrepareAreaName(), eform.getPprepareAreaId());
				
				if((prepareAreaList!=null && prepareAreaList.size()>0) && (!bObj.getPrepareAreaName().equals(eform.getPrepareAreaName()) && bObj.getPprepareAreaId()!=eform.getPprepareAreaId())) {
					eform.setMsg(Utility.get("SAVE_DUPLICATE"));
					sf.getCurrentSession().getTransaction().rollback();
					return mapping.findForward("prepareArea");
				}
				eform.loadToBean(bObj);
				bObj.setLastUpdateBy(userLogin.getUserName());
				bObj.setLastUpdateDate(new Date());

				pHome.update(bObj);
			} else {
				List<PrepareArea> paList = pHome.searchByPrepareAreaName(eform.getPrepareAreaName(), eform.getPprepareAreaId());
				if(paList!=null && paList.size()>0) {
					eform.setMsg(Utility.get("SAVE_DUPLICATE"));
					sf.getCurrentSession().getTransaction().rollback();
					return mapping.findForward("prepareArea");
				}
				
				bObj = new PrepareArea();
				eform.loadToBean(bObj);
				
				long maxPrepareAreaId = pHome.getMaxPrepareAreaId();
				bObj.setPrepareAreaId(maxPrepareAreaId);
				
				if(eform.getPprepareAreaId()==0){
					bObj.setPprepareAreaId(maxPrepareAreaId);
				}else{
					bObj.setPprepareAreaId(eform.getPprepareAreaId());
				}
				
				bObj.setLastUpdateBy(userLogin.getUserName());
				bObj.setLastUpdateDate(new Date());

				pHome.persist(bObj);
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

		return mapping.findForward("prepareArea");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		PrepareAreaForm eform = (PrepareAreaForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			PrepareAreaHome home = new PrepareAreaHome();
			PrepareArea pObj = home.findByPrepareAreaId(eform.getPrepareAreaId());
			eform.loadFromBean(pObj);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("prepareArea");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrepareAreaForm eform = (PrepareAreaForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		List<BreedType> breedTypeList = new ArrayList<BreedType>();
		List<BreedGroup> breedGroupList = new ArrayList<BreedGroup>();
		List<PrepareArea> prepareAreaList = new ArrayList<PrepareArea>();
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

			PrepareAreaHome prepareAreaHome = new PrepareAreaHome();
			prepareAreaList = prepareAreaHome.findAll();
			
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("breedGroupPlantList", breedGroupList);
			session.setAttribute("prepareAreaList", prepareAreaList);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("prepareArea");
	}
	
	public ActionForward getBreedGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PrepareAreaForm eform = (PrepareAreaForm)form;
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
