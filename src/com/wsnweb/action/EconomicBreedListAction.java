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
import com.wsndata.data.EconomicBreed;
import com.wsndata.data.Province;
import com.wsndata.data.User;
import com.wsndata.dbaccess.EconomicBreedHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.EconomicBreedListForm;

public class EconomicBreedListAction extends Action {
	private static final Logger log = Logger.getLogger(EconomicBreedListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 EconomicBreedListForm eform = (EconomicBreedListForm)form;
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
		EconomicBreedListForm eform = (EconomicBreedListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List economicBreedList = new ArrayList();
		String breedTypeName = "",provinceName = "";
		long regionNo=0;
		try{
			sf.getCurrentSession().beginTransaction();
			EconomicBreedHome home = new EconomicBreedHome();
			
			List provinceList = new ArrayList();
			ProvinceHome phome = new ProvinceHome();
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			if(userLogin.getLevel()>2) {
				Province province = phome.searchByProvinceNo(userLogin.getProvinceNo());
				provinceList.add(province);
			} else if(userLogin.getLevel()>1)
				provinceList = phome.retrieveByRegionNo(userLogin.getRegionNo());
			else
				provinceList = phome.findAll();
			provinceList.add(0,prov);
			session.setAttribute("ecoProvinceList", provinceList);
			
			breedTypeName = eform.getBreedTypeName()==null?"":eform.getBreedTypeName();
			provinceName = "กรุณาเลือก".equals(eform.getProvinceName())||eform.getProvinceName()==null?"":eform.getProvinceName();
			List newList = new ArrayList();
			long userLevel = userLogin.getLevel();
			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if("".equals(provinceName) && userLevel>2)
				provinceName = phome.searchByKey(regionNo, userLogin.getProvinceNo()).getThaiName();
			economicBreedList = home.searchByName(breedTypeName, regionNo, provinceName);
			if(economicBreedList !=null && economicBreedList.size() > 0) {
				for (int i = 0; i < economicBreedList.size(); i++) {
					EconomicBreed eb = new EconomicBreed();
					Object[] obj = (Object[])economicBreedList.get(i);
					eb.setBreedTypeId(Long.parseLong(obj[0].toString()));
					eb.setBreedTypeName(obj[1].toString());
					eb.setProvinceName(obj[2].toString());
					eb.setProvinceNo(Long.parseLong(obj[3].toString()));
					eb.setRegionNo(Long.parseLong(obj[4].toString()));
					eb.setBreedTypeDel(obj[0].toString()+"|"+obj[3].toString());
					newList.add(eb);
				}
			}
			
			Collections.sort(newList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("economicBreedList", newList);
			session.setAttribute("countEconomicBreed", newList!=null?newList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("economicBreedList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EconomicBreedListForm eform = (EconomicBreedListForm)form;
        HttpSession session = request.getSession();
        List economicBreedList = (List) session.getAttribute("economicBreedList");
        GridUtil.applyCheckValue(economicBreedList, eform.getDelbreedType(), "breedTypeDel", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(economicBreedList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("economicBreedList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		EconomicBreedListForm eform = (EconomicBreedListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List economicBreedList = (List) session.getAttribute("economicBreedList");
		GridUtil.applyCheckValue(economicBreedList, eform.getDelbreedType(), "breedTypeDel", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			EconomicBreedHome home = new EconomicBreedHome();
			for (int i=0; i<economicBreedList.size(); i++) {
				EconomicBreed breedType = (EconomicBreed)economicBreedList.get(i);
				if (breedType.getCheckBox()==true){
					home.delete(breedType);
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบพืชเศรษฐกิจไปทั้งสิ้น " + eform.getDelbreedType().length + " records");
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
