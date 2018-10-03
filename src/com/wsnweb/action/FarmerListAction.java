package com.wsnweb.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.wsndata.data.Farmer;
import com.wsndata.data.User;
import com.wsndata.dbaccess.FarmerHome;
import com.wsnweb.form.FarmerListForm;

public class FarmerListAction extends Action {
	private static final Logger log = Logger.getLogger(FarmerListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 FarmerListForm eform = (FarmerListForm)form;
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
		FarmerListForm eform = (FarmerListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List<Farmer> farmerList = new ArrayList<Farmer>();
		List getList = new ArrayList();
		String idCard= "",firstName= "",lastName = "";
		long regionNo=0, provinceNo=0, districtNo=0, subDistrictNo=0, userLevel=0;
		try{
			FarmerHome home = new FarmerHome();
			sf.getCurrentSession().beginTransaction();
			idCard = eform.getIdCard()==null?"":eform.getIdCard();
			firstName = eform.getFirstName()==null?"":eform.getFirstName();
			lastName = eform.getLastName()==null?"":eform.getLastName();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
			
			provinceNo = eform.getProvinceNo();
			districtNo = eform.getDistrictNo();
			subDistrictNo = eform.getSubDistrictNo();

			userLevel = userLogin.getLevel();

			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();
			
			getList = home.findByCriteria3(idCard, firstName, lastName,regionNo, provinceNo, districtNo, subDistrictNo);
			for (int i = 0; i < getList.size(); i++) {
				Object [] fObj = (Object[]) getList.get(i);
				Farmer farmer = new Farmer();
				
				farmer.setIdCard(fObj[1].toString());
				farmer.setEffectiveDate(df.parse(fObj[2].toString()));
				farmer.setAbbrPrefix(fObj[3].toString());
				farmer.setFirstName(fObj[4].toString());
				farmer.setLastName(fObj[5].toString());
				farmer.setAddressNo(fObj[6].toString());
				farmer.setMoo(Integer.parseInt(fObj[7].toString(), 10));
				farmer.setPostCode(Integer.parseInt(fObj[8].toString(), 10));
				farmer.setTel(fObj[9].toString());
				farmer.setBranchCode(Long.parseLong(fObj[17].toString()));
				farmer.setRegionNo(Long.parseLong(fObj[13].toString()));
				farmer.setProvinceNo(Long.parseLong(fObj[14].toString()));
				farmer.setDistrictNo(Long.parseLong(fObj[15].toString()));
				farmer.setSubDistrictNo(Long.parseLong(fObj[16].toString()));
				
				farmerList.add(farmer);
			}
			eform.setBranchCode(eform.getBranchCode());
			Collections.sort(farmerList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("farmerList", farmerList);
			session.setAttribute("countFarmer", farmerList!=null?farmerList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("farmerList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FarmerListForm eform = (FarmerListForm)form;
        HttpSession session = request.getSession();
        List farmerList = (List) session.getAttribute("farmerList");
        GridUtil.applyCheckValue(farmerList, eform.getDelFarmer(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(farmerList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("farmerList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		FarmerListForm eform = (FarmerListForm)form;
		HttpSession session = request.getSession();
		List farmerList = (List) session.getAttribute("farmerList");
		GridUtil.applyCheckValue(farmerList, eform.getDelFarmer(), "idCard", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			FarmerHome home = new FarmerHome();
			for (int i=0; i<farmerList.size(); i++) {
				Farmer farmer = (Farmer)farmerList.get(i);
				if (farmer.getCheckBox()==true){
					if(home.findbyTable("plant",farmer.getIdCard(),farmer.getEffectiveDate().toString()) == false) {
						eform.setErrMessage("'" + farmer.getFirstName() + "' มีการใช้งานอยู่ในข้อมูลการเพาะปลูกในขณะนี้");
						log.info("'" + farmer.getFirstName() + "' มีการใช้งานอยู่ในข้อมูลการเพาะปลูกในขณะนี้");
						break;
					} if(home.findbyTable("farmergroupfarmer",farmer.getIdCard(),farmer.getEffectiveDate().toString()) == false) {
						eform.setErrMessage("'" + farmer.getFirstName()+"' มีการใช้งานอยู่ในกลุ่มเกษตรกรในขณะนี้");
						log.info("'" + farmer.getFirstName() + "' มีการใช้งานอยู่ในกลุ่มเกษตรกรในขณะนี้");
						break;
					} if(home.findbyTable("plant",farmer.getIdCard(),farmer.getEffectiveDate().toString()) == true && home.findbyTable("farmergroupfarmer",farmer.getIdCard(),farmer.getEffectiveDate().toString()) == true ) {
						if(home.findbyTable("plant",farmer.getIdCard(),"") == true){ // true คือ ไม่มี farmer คนนี้อยู่ใน Plant
							if(home.findbyTable("farmergroupfarmer", farmer.getIdCard(),"")) {
								if(home.isDelete(farmer.getIdCard(),"") == true){
									eform.setErrMessage("'"+farmer.getFirstName()+"' ไม่สามารถลบได้ เนื่องจากเคยมีข้อมูลการเพาะปลูก");
									log.info("'"+farmer.getFirstName() + "' ไม่สามารถลบได้ เนื่องจากเคยมีข้อมูลการเพาะปลูก");
									break;
								}else{
							      home.delFarmer(farmer.getIdCard()); 
								}
							}else{
								eform.setErrMessage("'" + farmer.getFirstName()+"' มีการใช้งานอยู่ในกลุ่มเกษตรกรในขณะนี้");
								log.info("'" + farmer.getFirstName() + "' มีการใช้งานอยู่ในกลุ่มเกษตรกรในขณะนี้");
								break;
							}
						} else {
							eform.setErrMessage("'"+farmer.getFirstName()+"' ไม่สามารถลบได้ เนื่องจากมีข้อมูลการเพาะปลูก");
							log.info("'"+farmer.getFirstName() + "' ไม่สามารถลบได้ เนื่องจากมีข้อมูลการเพาะปลูก");
							break;
						}
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบจำนวนเกษตรกรไปทั้งสิ้น " + eform.getDelFarmer().length + " records");
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
