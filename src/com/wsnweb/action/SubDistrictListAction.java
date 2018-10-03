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
import com.wsndata.data.District;
import com.wsndata.data.Farmer;
import com.wsndata.data.FarmerGroupAddress;
import com.wsndata.data.LandRight;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
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
import com.wsnweb.form.SubDistrictListForm;

public class SubDistrictListAction extends Action {
	private static final Logger log = Logger.getLogger(SubDistrictListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 SubDistrictListForm eform = (SubDistrictListForm)form;
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
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else{
				 if (!"".equals(eform.getRegionNo()) && eform.getRegionNo() != null) {
					 search(mapping, eform, request, response);
					 if (!"".equals(eform.getProvinceNo()) && eform.getRegionNo() != null) {
						 getProvinceList(mapping, form, request, response);
						 return getDistrictList(mapping, form, request, response);
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
		SubDistrictListForm eform = (SubDistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceHome provinceHome = new ProvinceHome();	
			Province prov=new Province();
			prov.setRegionNo(0);
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			List<Province> provList = provinceHome.retrieveByRegionNo(Long.parseLong(eform.getRegionNo()));
			provList.add(0,prov);
		    session.setAttribute("provinceList", provList);
		}
		catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e.getMessage());
		} finally {
	    	 sf.getCurrentSession().close();
	    } 
		return mapping.findForward("list");
	}
	
	// get district from regionNo and provinceNo
	public ActionForward getDistrictList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictListForm eform = (SubDistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			DistrictHome home = new DistrictHome();		
			District district = new District();
			district.setDistrictNo(0);
			district.setProvinceNo(0);
			district.setDistrictNo(0);
			district.setThaiName("กรุณาเลือก");
			List<District> dtrcList = home.findDistrictByKeyNo(Long.parseLong(eform.getRegionNo()), Long.parseLong(eform.getProvinceNo()));
			dtrcList.add(0,district);
			session.setAttribute("districtList", dtrcList);
		}
		catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e.getMessage());
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("list");
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		SubDistrictListForm eform = (SubDistrictListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getList = null;
		List subDistrictList = new ArrayList();
		List districtList = new ArrayList();
		List regionList = new ArrayList();
		List provinceList= new ArrayList();
		long districtNo = 0, regionNo = 0, provinceNo = 0;
		String subDistrictName = "";
		try{
			SubDistrictHome home = new SubDistrictHome();
			RegionHome regHome = new RegionHome();
			ProvinceHome provHome = new ProvinceHome();
			DistrictHome distHome = new DistrictHome();
			
			
			sf.getCurrentSession().beginTransaction();
			subDistrictName = eform.getSubDistrictName()==null?"":eform.getSubDistrictName();
			if(!"".equals(eform.getRegionNo()))
				regionNo = Long.parseLong(eform.getRegionNo()==null?"0":eform.getRegionNo());
			if(!"".equals(eform.getProvinceNo()))
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
			if(!"".equals(eform.getDistrictNo()))
				districtNo = Long.parseLong(eform.getDistrictNo()==null?"0":eform.getDistrictNo());
			
			
				//--- ดึงภูมิภาค ออกมา --- //
				Region reg=new Region();
				reg.setRegionNo(0);
				reg.setRegionName("กรุณาเลือก");
				regionList = regHome.findAll();
				if(regionList !=null && regionList.size()>0)
				   regionList.add(0,reg);
				
				//--- ดึงจังหวัด ออกมา --- //
				Province prov=new Province();
				prov.setRegionNo(0);
				prov.setProvinceNo(0);
				prov.setThaiName("กรุณาเลือก");
				provinceList.add(0,prov);
				
				//--- ดึงอำเภอ ออกมา --- //
				District district = new District();
				district.setDistrictNo(0);
				district.setProvinceNo(0);
				district.setThaiName("กรุณาเลือก");
				districtList.add(0,district);
			
			if(regionNo <= 0 && provinceNo <= 0 && districtNo <= 0 && "".equals(subDistrictName))
				getList=home.searchAll();  			    
			else
				getList=home.searchByCriteria(regionNo, provinceNo, districtNo, subDistrictName); 
			
			if(getList !=null && getList.size() > 0){
				for(int i=0; i< getList.size(); i++){
						SubDistrict subDrtc = new SubDistrict();
						Object[] obj = (Object[])getList.get(i);
						subDrtc.setRegionNo(Long.parseLong(obj[0].toString()));
						subDrtc.setRegionName(obj[1].toString());
						subDrtc.setProvinceNo(Long.parseLong(obj[2].toString()));
						subDrtc.setProvinceName(obj[3].toString());
						subDrtc.setDistrictNo(Long.parseLong(obj[4].toString()));
						subDrtc.setDistrictName(obj[5].toString());
						subDrtc.setSubDistrictNo(Long.parseLong(obj[6].toString()));
						subDrtc.setThaiName(obj[7].toString());
						subDrtc.setEngName(obj[8].toString());
						subDrtc.setPostCode(Integer.parseInt(obj[9].toString(), 10));
						subDistrictList.add(subDrtc);
			    } 
			    Collections.sort(subDistrictList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));	
			}	
				
			session.setAttribute("regionList", regionList);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("countSubDistrict", subDistrictList!=null?subDistrictList.size():0);
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
		SubDistrictListForm eform = (SubDistrictListForm)form;
		HttpSession session = request.getSession();
		List subDistrictList = (List) session.getAttribute("subDistrictList");
		GridUtil.applyCheckValue(subDistrictList, eform.getDelSubDistrictNo(), "subDistrictNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		SubDistrictHome home = new SubDistrictHome();
		UserHome uHome = new UserHome();//User
		BranchHome bHome = new BranchHome();//Branch
		BuyerHome buyHome = new BuyerHome();//Buyer
		FarmerHome famerHome = new FarmerHome();//Farmer
		LandRightHome landHome = new LandRightHome();//LandRight
		SellingDetailHome sellHome = new SellingDetailHome();//SellingDetail
		FarmerGroupAddressHome fgAddrHome = new FarmerGroupAddressHome();//FarmerGroupAddress
		try{
			sf.getCurrentSession().beginTransaction();	
			for (int i=0; i<subDistrictList.size(); i++) {
				SubDistrict subDistrict = (SubDistrict)subDistrictList.get(i);
				if (subDistrict.getCheckBox()==true){
					
					List<User> uUserList = uHome.findBySubDistrictNo(subDistrict.getSubDistrictNo());
					
					if(uUserList !=null && uUserList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<Branch> uBranchList = bHome.findByRegionAndProvinceAndDistrictAndSubDistrict(subDistrict.getRegionNo(), subDistrict.getProvinceNo(), subDistrict.getDistrictNo(), subDistrict.getSubDistrictNo());
					
					if(uBranchList !=null && uBranchList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<Buyer> uBuyList = buyHome.findByRegionAndProvinceAndDistrictAndSubDistrict(subDistrict.getRegionNo(), subDistrict.getProvinceNo(), subDistrict.getDistrictNo(), subDistrict.getSubDistrictNo());
					
					if(uBuyList !=null && uBuyList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<Farmer> uFarmerList = famerHome.findByRegionAndProvinceAndDistrictAndSubDistrict(subDistrict.getRegionNo(), subDistrict.getProvinceNo(), subDistrict.getDistrictNo(), subDistrict.getSubDistrictNo());
					
					if(uFarmerList !=null && uFarmerList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<LandRight> uLandList = landHome.findByRegionAndProvinceAndDistrictAndSubDistrict(subDistrict.getRegionNo(), subDistrict.getProvinceNo(), subDistrict.getDistrictNo(), subDistrict.getSubDistrictNo());
					
					if(uLandList !=null && uLandList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<FarmerGroupAddress> uAddressList = fgAddrHome.findBySubDistrictNo(subDistrict.getSubDistrictNo());
					
					if(uAddressList !=null && uAddressList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}
					
					List<SellingDetail> uSellList = sellHome.findByRegionAndProvinceAndDistrictAndSubDistrict(subDistrict.getRegionNo(), subDistrict.getProvinceNo(), subDistrict.getDistrictNo(), subDistrict.getSubDistrictNo());
					
					if(uSellList !=null && uSellList.size() > 0){
						eform.setErrMessage("ตำบล/แขวง " + subDistrict.getThaiName() + " มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
							home.delete(subDistrict);
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบแขวง/ตำบลไปทั้งสิ้น " + eform.getDelSubDistrictNo().length + " records");
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
		SubDistrictListForm eform = (SubDistrictListForm)form;
        HttpSession session = request.getSession();
        List subDistrictList = (List) session.getAttribute("subDistrictList");
        GridUtil.applyCheckValue(subDistrictList, eform.getDelSubDistrictNo(), "subDistrictNo", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(subDistrictList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("list");
	}
	
	//Ajax Method
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictListForm eform = (SubDistrictListForm)form;
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
	
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictListForm eform = (SubDistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
			
			long provinceNo = 0;
			if(!"".equals(eform.getProvinceNo()))
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
				dtrcList = home.filterByProvinceNo(provinceNo);
		    
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","subDistrictNo","provinceThai","provinceEng","subDistrictThai","subDistrictEng","postCode"});   

		    String result = JSONArray.fromObject(dtrcList, jsonConfig).toString();
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
