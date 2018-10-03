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
import com.wsndata.data.Branch;
import com.wsndata.data.Buyer;
import com.wsndata.data.District;
import com.wsndata.data.EconomicBreed;
import com.wsndata.data.Farmer;
import com.wsndata.data.FarmerGroupAddress;
import com.wsndata.data.LandRight;
import com.wsndata.data.ProductionForecast;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
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
import com.wsnweb.form.ProvinceListForm;

public class ProvinceListAction extends Action {
	private static final Logger log = Logger.getLogger(ProvinceListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 ProvinceListForm eform = (ProvinceListForm)form;
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

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ProvinceListForm eform = (ProvinceListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = null;
		List provinceList = new ArrayList();
	
		List regionList = (List) session.getAttribute("regionList");
		String provinceName = ""; long regionNo = 0;
		try{
			RegionHome regHome = new RegionHome();
			ProvinceHome home = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();
			//--- ดึงภูมิภาค ออกมา --- //
				Region reg=new Region();
				reg.setRegionNo(0);
				reg.setRegionName("กรุณาเลือก");
				regionList = regHome.findAll();
				if(regionList !=null && regionList.size()>0)
					regionList.add(0,reg);

			provinceName = eform.getProvinceName()==null?"":eform.getProvinceName();
			if(!"".equals(eform.getRegionNo()))
				regionNo = Long.parseLong(eform.getRegionNo()==null?"0":eform.getRegionNo());
			if("".equals(provinceName) && regionNo <= 0){
				getList=home.searchAll(); 
		    	eform.setRegionNo("0");
			}else{
				getList=home.searchByCriteria(regionNo, provinceName);
				eform.setProvinceName(provinceName);
				eform.setRegionNo(String.valueOf(regionNo));
			}
			
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
					Province province = new Province();
					Object[] obj = (Object[])getList.get(i);
					province.setRegionNo(Long.parseLong(obj[0].toString()));
					province.setRegionName(obj[1].toString());
					province.setProvinceNo(Long.parseLong(obj[2].toString()));
					province.setThaiName(obj[3].toString());
					province.setEngName(obj[4].toString());
					provinceList.add(province);
				}
				Collections.sort(provinceList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("regionList", regionList);
			session.setAttribute("countProvince", provinceList!=null?provinceList.size():0);
			
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e.getMessage());
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("list");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ProvinceListForm eform = (ProvinceListForm)form;
        HttpSession session = request.getSession();
        List provinceList = (List) session.getAttribute("provinceList");
        GridUtil.applyCheckValue(provinceList, eform.getDelProvinceNo(), "provinceNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(provinceList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("list");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SessionFactory sf = HibernateHome.getSessionFactory();
		ProvinceListForm eform = (ProvinceListForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		List provinceList = (List) session.getAttribute("provinceList");
		GridUtil.applyCheckValue(provinceList, eform.getDelProvinceNo(), "provinceNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		ProvinceHome home = new ProvinceHome();

		UserHome uHome = new UserHome();//User
		BranchHome bHome = new BranchHome();//Branch
		DistrictHome dHome = new DistrictHome();//District
		SubDistrictHome sHome = new SubDistrictHome();//SubDistrict
		EconomicBreedHome ecoHome = new EconomicBreedHome();//EconomicBreed
		BuyerHome buyHome = new BuyerHome();//Buyer
		ProductionForecastHome forecastHome = new ProductionForecastHome();//ProductionForecast
		FarmerHome famerHome = new FarmerHome();//Farmer
		LandRightHome landHome = new LandRightHome();//LandRight
		SellingDetailHome sellHome = new SellingDetailHome();//SellingDetail
		FarmerGroupAddressHome fgAddrHome = new FarmerGroupAddressHome();//FarmerGroupAddress
		try{
			sf.getCurrentSession().beginTransaction();	
			for (int i=0; i<provinceList.size(); i++) {
				Province province = (Province)provinceList.get(i);
					if (province.getCheckBox()==true){
					
						//สร้างList
						
						List<User> uUserList = uHome.findByProvinceNo(province.getProvinceNo());
						
						if(uUserList !=null && uUserList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<Branch> uBranchList = bHome.findByProvinceNo(province.getProvinceNo());
						
						if(uBranchList !=null && uBranchList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<District> uDistList = dHome.findByProvinceNo(province.getProvinceNo());
						
						if(uDistList !=null && uDistList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<SubDistrict> uSubList = sHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uSubList !=null && uSubList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<EconomicBreed> uEcoList = ecoHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uEcoList !=null && uEcoList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<Buyer> uBuyList = buyHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uBuyList !=null && uBuyList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<ProductionForecast> uForecastList = forecastHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uForecastList !=null && uForecastList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<Farmer> uFarmerList = famerHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uFarmerList !=null && uFarmerList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<LandRight> uLandList = landHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uLandList !=null && uLandList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<SellingDetail> uSellList = sellHome.findByRegionAndProvince(province.getRegionNo(),province.getProvinceNo());
						
						if(uSellList !=null && uSellList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						List<FarmerGroupAddress> uAddressList = fgAddrHome.findByProvinceNo(province.getProvinceNo());
						
						if(uAddressList !=null && uAddressList.size() > 0){
							eform.setErrMessage("จังหวัด " + province.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
							break;
						}
						
						else{
								home.delete(province);
							}
						
						}	
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบจังหวัดไปทั้งสิ้น " + eform.getDelProvinceNo().length + " records");


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
