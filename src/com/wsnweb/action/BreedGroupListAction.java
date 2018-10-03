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
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsnweb.form.BreedGroupListForm;

public class BreedGroupListAction extends Action {
	private static final Logger log = Logger.getLogger(BreedGroupListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 BreedGroupListForm eform = (BreedGroupListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		BreedGroupListForm eform = (BreedGroupListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List breedGroupList = new ArrayList();
		String breedGroupName= "";
		long breedTypeId = 0;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();
			breedGroupName = eform.getBreedGroupName()==null?"":eform.getBreedGroupName();
			breedTypeId = eform.getBreedTypeId()==0?0:eform.getBreedTypeId();
			List newList = new ArrayList();
			breedGroupList=home.searchByBreedGroupName(breedGroupName,breedTypeId); 
			if(breedGroupList !=null && breedGroupList.size() > 0) {
				for (int i = 0; i < breedGroupList.size(); i++) {
					BreedGroup bg = new BreedGroup();
					Object[] obj = (Object[])breedGroupList.get(i);
					bg.setBreedGroupId(Long.parseLong(obj[0].toString()));
					bg.setBreedGroupName(obj[1].toString());
					bg.setBreedTypeId(Long.parseLong(obj[2].toString()));
					bg.setBreedTypeName(obj[3].toString());
					newList.add(bg);
				}
			}
			Collections.sort(newList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("breedGroupList", newList);
			BreedTypeHome bhome = new BreedTypeHome();
			List bList = new ArrayList();
			BreedType b = new BreedType();
			bList = bhome.findAll();
			b.setBreedTypeId(0);
			b.setBreedTypeName("กรุณาเลือก");
			bList.add(0,b);
			session.setAttribute("breedTypeList", bList);
			session.setAttribute("countBreedGroup", newList!=null?newList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("breedGroupList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BreedGroupListForm eform = (BreedGroupListForm)form;
        HttpSession session = request.getSession();
        List breedGroupList = (List) session.getAttribute("breedGroupList");
        GridUtil.applyCheckValue(breedGroupList, eform.getDelBreedGroup(), "breedGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(breedGroupList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("breedGroupList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		BreedGroupListForm eform = (BreedGroupListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List breedGroupList = (List) session.getAttribute("breedGroupList");
		GridUtil.applyCheckValue(breedGroupList, eform.getDelBreedGroup(), "breedGroupId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			BreedGroupHome home = new BreedGroupHome();
			for (int i=0; i<breedGroupList.size(); i++) {
				BreedGroup breedGroup = (BreedGroup)breedGroupList.get(i);
				if (breedGroup.getCheckBox()==true){
					if (home.findbyTable("PlantDetail", breedGroup.getBreedGroupId())
							&& home.findbyTable("CostDetail", breedGroup.getBreedGroupId())
							&& home.findbyTable("HarvestDetail", breedGroup.getBreedGroupId())
							&& home.findbyTable("SellingDetail", breedGroup.getBreedGroupId())
							&& home.findbyTable("ProductionForecast", breedGroup.getBreedGroupId())) {
						home.delete(breedGroup);
					}else{
						eform.setErrMessage(breedGroup.getBreedGroupName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบกลุ่มพันธู์ไปทั้งสิ้น " + eform.getDelBreedGroup().length + " records");
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
