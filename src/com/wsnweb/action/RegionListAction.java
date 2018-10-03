package com.wsnweb.action;

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
import com.wsndata.data.Region;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.EconomicBreedHome;
import com.wsndata.dbaccess.FarmerGroupAddressHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.ProductionForecastHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.SellingDetailHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.RegionListForm;

public class RegionListAction extends Action {
	private static final Logger log = Logger.getLogger(RegionListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 RegionListForm eform = (RegionListForm)form;
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
		RegionListForm eform = (RegionListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = null;
		String regionName= "";
		try{
			RegionHome home = new RegionHome();
			sf.getCurrentSession().beginTransaction();
			regionName = eform.getRegionName()==null?"":eform.getRegionName();
			eform.setRegionName(regionName);
			if("".equals(regionName))
				getList=home.findAll();  			    
			else
				getList=home.searchByName(regionName); 
			Collections.sort(getList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("regionList", getList);
			session.setAttribute("countRegion", getList!=null?getList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("list");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegionListForm eform = (RegionListForm)form;
        HttpSession session = request.getSession();
        List regionList = (List) session.getAttribute("regionList");
        GridUtil.applyCheckValue(regionList, eform.getDelRegionNo(), "regionNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(regionList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("list");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		RegionListForm eform = (RegionListForm)form;
		HttpSession session = request.getSession();
		List regionList = (List) session.getAttribute("regionList");
		GridUtil.applyCheckValue(regionList, eform.getDelRegionNo(), "regionNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		
		try{
			sf.getCurrentSession().beginTransaction();	
			RegionHome home = new RegionHome();
			UserHome uHome = new UserHome();//User
			BranchHome bHome = new BranchHome();//Branch
			ProvinceHome pHome = new ProvinceHome();//Province
			DistrictHome dHome = new DistrictHome();//District
			SubDistrictHome sHome = new SubDistrictHome();//SubDistrict
			EconomicBreedHome ecoHome = new EconomicBreedHome();//EconomicBreed
			BuyerHome buyHome = new BuyerHome();//Buyer
			ProductionForecastHome forecastHome = new ProductionForecastHome();//ProductionForecast
			FarmerHome famerHome = new FarmerHome();//Farmer
			LandRightHome landHome = new LandRightHome();//LandRight
			SellingDetailHome sellHome = new SellingDetailHome();//SellingDetail
			FarmerGroupAddressHome fgAddrHome = new FarmerGroupAddressHome();//FarmerGroupAddress
			
			for (int i=0; i<regionList.size(); i++) {
				Region region = (Region)regionList.get(i);
				if (region.getCheckBox()==true){
					//สร้างList
					
					List uUserList = uHome.findByRegionNo(region.getRegionNo());
					
					if(uUserList !=null && uUserList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uBranchList = bHome.findByRegionNo(region.getRegionNo());
					
					if(uBranchList !=null && uBranchList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uProvList = pHome.retrieveByRegionNo(region.getRegionNo());
					
					if(uProvList !=null && uProvList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uDistList = dHome.findByRegionNo(region.getRegionNo());
					
					if(uDistList !=null && uDistList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uSubList = sHome.findByRegionNo(region.getRegionNo());
					
					if(uSubList !=null && uSubList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uEcoList = ecoHome.findByRegionNo(region.getRegionNo());
					
					if(uEcoList !=null && uEcoList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uBuyList = buyHome.findByRegionNo(region.getRegionNo());
					
					if(uBuyList !=null && uBuyList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uForecastList = forecastHome.findByRegionNo(region.getRegionNo());
					
					if(uForecastList !=null && uForecastList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uFarmerList = famerHome.findByRegionNo(region.getRegionNo());
					
					if(uFarmerList !=null && uFarmerList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uLandList = landHome.findByRegionNo(region.getRegionNo());
					
					if(uLandList !=null && uLandList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uSellList = sellHome.findByRegionNo(region.getRegionNo());
					
					if(uSellList !=null && uSellList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List uAddressList = fgAddrHome.findByRegionNo(region.getRegionNo());
					
					if(uAddressList !=null && uAddressList.size() > 0){
						eform.setErrMessage(region.getRegionName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					else {
						home.delete(region);
					}
				}
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบภูมิภาคไปทั้งสิ้น " + eform.getDelRegionNo().length + " records");
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
