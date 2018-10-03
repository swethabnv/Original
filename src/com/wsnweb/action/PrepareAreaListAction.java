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
import com.wsndata.data.PrepareArea;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.PlantDetailHome;
import com.wsndata.dbaccess.PrepareAreaHome;
import com.wsnweb.form.PrepareAreaListForm;

public class PrepareAreaListAction extends Action {
	private static final Logger log = Logger.getLogger(PrepareAreaListAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrepareAreaListForm eform = (PrepareAreaListForm) form;
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
		PrepareAreaListForm eform = (PrepareAreaListForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List breedTypeList = new ArrayList();
		List prepareAreaList = new ArrayList();
		List getPrepareAreaList = new ArrayList();
		String prepareAreaName = "";
		long breedTypeId=0, breedGroupId=0;
		try {
			sf.getCurrentSession().beginTransaction();
			BreedTypeHome breedTypeHome = new BreedTypeHome();
			PrepareAreaHome prepareAreaHome = new PrepareAreaHome();

			prepareAreaName = eform.getPrepareAreaName()==null?"":eform.getPrepareAreaName();
			breedTypeId = eform.getBreedTypeId();
			breedGroupId = eform.getBreedGroupId();

			//--- ดึงชนิดพืช ออกมา --- //
			BreedType breedType = new BreedType();
			breedType.setBreedTypeId(0);
			breedType.setBreedTypeName("กรุณาเลือก");
			breedTypeList = breedTypeHome.findAll();
			if(breedTypeList !=null && breedTypeList.size()>0)
				breedTypeList.add(0,breedType);
			
			getPrepareAreaList = prepareAreaHome.searchByCriteria(prepareAreaName, breedTypeId, breedGroupId);
			prepareAreaList = new ArrayList();
			if(getPrepareAreaList !=null && getPrepareAreaList.size() > 0){
				for(int i=0; i< getPrepareAreaList.size(); i++){
					PrepareArea prepareArea = new PrepareArea();
					Object[] obj = (Object[])getPrepareAreaList.get(i);
					prepareArea.setPrepareAreaId(Long.parseLong(obj[0].toString()));
					prepareArea.setPrepareAreaName(obj[1].toString());
					prepareArea.setPprepareAreaId(Long.parseLong(obj[2].toString()));
					prepareArea.setPprepareAreaName(obj[3].toString());
					//prepareArea.setBreedTypeName(obj[4].toString());
					//prepareArea.setBreedGroupName(obj[5].toString());
					prepareAreaList.add(prepareArea);
				}
				Collections.sort(prepareAreaList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}

			session.setAttribute("prepareAreaList", prepareAreaList);
			session.setAttribute("breedTypePlantList", breedTypeList);
			session.setAttribute("countPrepareArea", prepareAreaList!=null?prepareAreaList.size():0);
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("prepareAreaList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrepareAreaListForm eform = (PrepareAreaListForm)form;
        HttpSession session = request.getSession();
        List prepareAreaList = (List) session.getAttribute("prepareAreaList");
        GridUtil.applyCheckValue(prepareAreaList, eform.getDelPrepareArea(), "prepareAreaId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(prepareAreaList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("prepareAreaList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		PrepareAreaListForm eform = (PrepareAreaListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List prepareAreaList = (List) session.getAttribute("prepareAreaList");
		GridUtil.applyCheckValue(prepareAreaList, eform.getDelPrepareArea(), "prepareAreaId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		PlantDetailHome plantDetailHome = new PlantDetailHome();//User
		try{
			sf.getCurrentSession().beginTransaction();	
			PrepareAreaHome home = new PrepareAreaHome();
			for (int i=0; i<prepareAreaList.size(); i++) {
				PrepareArea prepareArea = (PrepareArea)prepareAreaList.get(i);
				if (prepareArea.getCheckBox()==true){
					if (home.findBy(prepareArea.getPrepareAreaId()).size()>0) {
						eform.setErrMessage(prepareArea.getPrepareAreaName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					List<PlantDetail> plantDetailList = plantDetailHome.findByPrepareArea(String.valueOf(prepareArea.getPrepareAreaId()));
					if(plantDetailList != null && plantDetailList.size() > 0){
						eform.setErrMessage(prepareArea.getPrepareAreaName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
						home.delete(prepareArea);
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบการจัดการแปลงก่อนเตรียมดินไปทั้งสิ้น " + eform.getDelPrepareArea().length + " records");
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
