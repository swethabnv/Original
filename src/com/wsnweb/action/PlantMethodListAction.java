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
import com.wsndata.data.BreedType;
import com.wsndata.data.PlantDetail;
import com.wsndata.data.PlantMethod;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.PlantDetailHome;
import com.wsndata.dbaccess.PlantMethodHome;
import com.wsnweb.form.PlantMethodListForm;

public class PlantMethodListAction extends Action {
	private static final Logger log = Logger.getLogger(PlantMethodListAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PlantMethodListForm eform = (PlantMethodListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		}
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PlantMethodListForm eform = (PlantMethodListForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List breedTypeList = new ArrayList();
		List plantMethodList = new ArrayList();
		List getPlantMethodList = new ArrayList();
		String plantMethodName = "";
		long breedTypeId=0, breedGroupId=0;
		try {
			sf.getCurrentSession().beginTransaction();
			BreedTypeHome breedTypeHome = new BreedTypeHome();
			PlantMethodHome plantMethodHome = new PlantMethodHome();

			plantMethodName = eform.getPlantMethodName()==null?"":eform.getPlantMethodName();
			breedTypeId = eform.getBreedTypeId();
			breedGroupId = eform.getBreedGroupId();

			//--- ดึงชนิดพืช ออกมา --- //
			BreedType breedType = new BreedType();
			breedType.setBreedTypeId(0);
			breedType.setBreedTypeName("กรุณาเลือก");
			breedTypeList = breedTypeHome.findAll();
			if(breedTypeList !=null && breedTypeList.size()>0)
				breedTypeList.add(0,breedType);
			
			getPlantMethodList = plantMethodHome.searchByCriteria(plantMethodName, breedTypeId, breedGroupId);
			plantMethodList = new ArrayList();
			if(getPlantMethodList !=null && getPlantMethodList.size() > 0){
				for(int i=0; i< getPlantMethodList.size(); i++){
					PlantMethod plantMethod = new PlantMethod();
					Object[] obj = (Object[])getPlantMethodList.get(i);
					plantMethod.setPlantMethodId(Long.parseLong(obj[0].toString()));
					plantMethod.setPlantMethodName(obj[1].toString());
					//plantMethod.setBreedTypeName(obj[2].toString());
					//plantMethod.setBreedGroupName(obj[3].toString());
					plantMethodList.add(plantMethod);
				}
				Collections.sort(plantMethodList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}

			session.setAttribute("plantMethodList", plantMethodList);
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("countPlantMethod", plantMethodList!=null?plantMethodList.size():0);
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("plantMethodList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlantMethodListForm eform = (PlantMethodListForm)form;
        HttpSession session = request.getSession();
        List plantMethodList = (List) session.getAttribute("plantMethodList");
        GridUtil.applyCheckValue(plantMethodList, eform.getDelPlantMethod(), "plantMethodId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(plantMethodList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("plantMethodList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantMethodListForm eform = (PlantMethodListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List plantMethodList = (List) session.getAttribute("plantMethodList");
		GridUtil.applyCheckValue(plantMethodList, eform.getDelPlantMethod(), "plantMethodId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		PlantDetailHome plantDetailHome = new PlantDetailHome();//User
		try{
			sf.getCurrentSession().beginTransaction();	
			PlantMethodHome home = new PlantMethodHome();
			for (int i=0; i<plantMethodList.size(); i++) {
				PlantMethod plantMethod = (PlantMethod)plantMethodList.get(i);
				if (plantMethod.getCheckBox()==true){
					List<PlantDetail> plantDetailList = plantDetailHome.findByPlantMethod(String.valueOf(plantMethod.getPlantMethodId()));
					if(plantDetailList != null && plantDetailList.size() > 0){
						eform.setErrMessage(plantMethod.getPlantMethodName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
						home.delete(plantMethod);
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบการจัดการแปลงก่อนเตรียมดินไปทั้งสิ้น " + eform.getDelPlantMethod().length + " records");
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
