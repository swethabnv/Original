package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.dcs.util.DCSCompare;
import com.dcs.util.GridUtil;
import com.wsndata.data.Branch;
import com.wsndata.data.Buyer;
import com.wsndata.data.Farmer;
import com.wsndata.data.FarmerGroupAddress;
import com.wsndata.data.LandRight;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.data.District;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.FarmerGroupAddressHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.SellingDetailHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.DistrictListForm;

public class DistrictListAction extends Action {
	private static final Logger log = Logger.getLogger(DistrictListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 DistrictListForm eform = (DistrictListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else if("GetProvince".equals(eform.getCmd())){
				 return getProvinceInfo(mapping, form, request, response);
			 } else{
				 if (!"".equals(eform.getRegionNo()) && eform.getRegionNo() != null) {
					 search(mapping, eform, request, response);
					 if (!"".equals(eform.getProvinceNo()) && eform.getRegionNo() != null) {
						 return getProvinceList(mapping, form, request, response);
					 }
					 return getProvinceList(mapping, form, request, response);
				 }
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	// get provincelist from regionNo
	public ActionForward getProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictListForm eform = (DistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceHome provinceHome = new ProvinceHome();		
			Province prov=new Province();
			prov.setRegionNo(0);
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			List<Province> provList = null;
			if(eform.getRegionNo() != null && !"".equals(eform.getRegionNo())){
				provList = provinceHome.retrieveByRegionNo(Long.parseLong(eform.getRegionNo()));
				provList.add(0,prov);
			}
		    session.setAttribute("provinceList", provList);
		}
		catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	// log.error(e.getMessage());
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("list");
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		DistrictListForm eform = (DistrictListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = null;
		List districtList = new ArrayList();
		List regionList = new ArrayList();
		List provinceList = new ArrayList();
		String districtName = ""; long regionNo = 0, provinceNo = 0;
		try{
			DistrictHome home = new DistrictHome();
			RegionHome regHome = new RegionHome();
			ProvinceHome provHome = new ProvinceHome();
			sf.getCurrentSession().beginTransaction();
			
			//--- ดึงภูมิภาค ออกมา --- //
//			if(regionList == null){
				Region reg=new Region();
				reg.setRegionNo(0);
				reg.setRegionName("กรุณาเลือก");
				regionList = regHome.findAll();
				if(regionList !=null && regionList.size()>0)
				     regionList.add(0,reg);
//			}

//			if(provinceList == null){
				Province prov=new Province();
				prov.setRegionNo(0);
				prov.setProvinceNo(0);
				prov.setThaiName("กรุณาเลือก");
//				provinceList = provHome.findAll();
//				if(provinceList  !=null && provinceList.size()>0)
				provinceList.add(0,prov);
//			}
			
			// ----
			districtName = eform.getDistrictName()==null?"":eform.getDistrictName();
			regionNo = Long.parseLong(eform.getRegionNo()==null?"0":eform.getRegionNo());
			if(!"".equals(eform.getProvinceNo()))
			   provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
			if(regionNo <= 0 && provinceNo <= 0 && "".equals(districtName))
				getList=home.searchAll();  			    
			else
				getList=home.searchByKey(regionNo, provinceNo, districtName); 
			
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
					District district = new District();
					Object[] obj = (Object[])getList.get(i);
					    district.setRegionNo(Long.parseLong(obj[0].toString()));
					    district.setRegionName(obj[1].toString());
					    district.setProvinceNo(Long.parseLong(obj[2].toString()));
					    district.setProvinceName(obj[3].toString());
					    district.setDistrictNo(Long.parseLong(obj[4].toString()));
					    district.setThaiName(obj[5].toString());
					    district.setEngName(obj[6].toString());
					    districtList.add(district);
				}
				Collections.sort(districtList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			session.setAttribute("districtList", districtList);
			session.setAttribute("countDistrict", districtList!=null?districtList.size():0);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("regionList", regionList);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("list");
	}


	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictListForm eform = (DistrictListForm)form;
		HttpSession session = request.getSession();
		List districtList = (List) session.getAttribute("districtList");
		GridUtil.applyCheckValue(districtList, eform.getDelDistrictNo(), "districtNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		DistrictHome home = new DistrictHome();
		UserHome uHome = new UserHome();//User
		BranchHome bHome = new BranchHome();//Branch
		SubDistrictHome sHome = new SubDistrictHome();//SubDistrict
		BuyerHome buyHome = new BuyerHome();//Buyer
		FarmerHome famerHome = new FarmerHome();//Farmer
		LandRightHome landHome = new LandRightHome();//LandRight
		SellingDetailHome sellHome = new SellingDetailHome();//SellingDetail
		FarmerGroupAddressHome fgAddrHome = new FarmerGroupAddressHome();//FarmerGroupAddress
		try{
			sf.getCurrentSession().beginTransaction();
			for (int i=0; i<districtList.size(); i++) {
				District district = (District)districtList.get(i);
				if (district.getCheckBox()==true){
					
					List<User> uUserList = uHome.findByDistrictNo(district.getDistrictNo());
					
					if(uUserList !=null && uUserList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
													
					List<Branch> uBranchList = bHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uBranchList !=null && uBranchList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<SubDistrict> uSubList = sHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uSubList !=null && uSubList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<Buyer> uBuyList = buyHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uBuyList !=null && uBuyList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<Farmer> uFarmerList = famerHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uFarmerList !=null && uFarmerList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<LandRight> uLandList = landHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uLandList !=null && uLandList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<FarmerGroupAddress> uAddressList = fgAddrHome.findByDistrictNo(district.getDistrictNo());
					
					if(uAddressList !=null && uAddressList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<SellingDetail> uSellList = sellHome.findByRegionAndProvinceAndDistrict(district.getRegionNo(),district.getProvinceNo(),district.getDistrictNo());
					
					if(uSellList !=null && uSellList.size() > 0){
						eform.setErrMessage("อำเภอ/เขต " + district.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					} else {
						home.delete(district);
					}	
				}	
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("delete district : " + eform.getDelDistrictNo().length + " records");
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}

	
	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		DistrictListForm eform = (DistrictListForm)form;
        HttpSession session = request.getSession();
        List districtList = (List) session.getAttribute("districtList");
        GridUtil.applyCheckValue(districtList, eform.getDelDistrictNo(), "districtNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(districtList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("list");
	}
	//Ajax Method
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictListForm eform = (DistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List proList = new ArrayList();			
			
			long regionNo = 0;
			
			if(!"".equals(eform.getRegionNo()))
				regionNo = Long.parseLong(eform.getRegionNo()==null?"0":eform.getRegionNo());
				proList = home.filterByRegionNoDistinct(regionNo);
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();  
		    jsonConfig.setExcludes(new String[]{"regionNo","subDistrictNo","districtNo","regionName","subDistrictThai","subDistrictEng","districtThai","districtEng","postCode"});   

		    String result = JSONArray.fromObject(proList, jsonConfig).toString();
		    result = result.trim().replaceAll("&nbsp;", " ");
		    wt.print(result);
		    wt.flush();
		    wt.close();
		    
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}

}
