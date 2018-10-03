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
import com.wsndata.data.Branch;
import com.wsndata.data.Buyer;
import com.wsndata.data.District;
import com.wsndata.data.Farmer;
import com.wsndata.data.LandRight;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.Region;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.SellingDetailHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.DistrictForm;
import com.wsnweb.util.Utility;
import com.wsndata.dbaccess.ProvinceDistrictHome;

public class DistrictAction extends Action {

	private static final Logger log = Logger.getLogger(DistrictAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		DistrictForm eform = (DistrictForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if("Edit".equals(eform.getCmd()) ){
				 getProvinceList(mapping, form, request, response);
				 return edit(mapping, form, request, response);
			 } else if("GetProvince".equals(eform.getCmd())){
				 return getProvinceInfo(mapping, form, request, response);
			 } else{
				 return mapping.findForward("district"); 
			 }
		}
	}
	
	private ActionForward save(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		DistrictForm eform = (DistrictForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictHome home = new DistrictHome();
		long districtNo = 0, provinceNo = 0, regionNo = 0;
		String msg = "";
		long newRegionNo = 0;
		long newProvinceNo = 0;
		long prevRegionNo = 0;
		long prevProvinceNo = 0;
		
		try {
			sf.getCurrentSession().beginTransaction();
			if(eform.getDistrictNo() !=null && !"".equals(eform.getDistrictNo())){
				districtNo = Long.parseLong(eform.getDistrictNo());
			}
			if(eform.getProvinceNo() !=null && !"".equals(eform.getProvinceNo())){
				provinceNo = Long.parseLong(eform.getProvinceNo());
			}			
			if(eform.getRegionNo() !=null && !"".equals(eform.getRegionNo())){
				regionNo = Long.parseLong(eform.getRegionNo());
			}
			
			if(eform.getPrevRegionNo() !=null && !"".equals(eform.getPrevRegionNo())){
				prevRegionNo = Long.parseLong(eform.getPrevRegionNo());
			}
			if(eform.getPrevProvinceNo() !=null && !"".equals(eform.getPrevProvinceNo())){
				prevProvinceNo = Long.parseLong(eform.getPrevProvinceNo());
			}
			
			List<District> checkDupDistrict = home.checkDupDistrict(regionNo, eform.getThaiName(), provinceNo, districtNo);
			
			if(checkDupDistrict !=null && checkDupDistrict.size()>0 ){
				msg = "ไม่สามารถบันทึกได้ มีเขต/อำเภอ"+eform.getThaiName()+"อยู่แล้วในระบบ!";
			}else{
				District district = null;
				if(districtNo>0)
					district = home.findByDistrictNo(districtNo);
				else
					district = home.findByName(provinceNo, eform.getThaiName());
				if(district !=null){  //Case Update
					
					SubDistrictHome subDistrictHome = new SubDistrictHome();
					List<SubDistrict> subDistrictList = subDistrictHome.findByRegionAndProvinceAndDistrict(prevRegionNo, prevProvinceNo, districtNo);
					
					BuyerHome buyerHome = new BuyerHome();
					List<Buyer> buyerList = buyerHome.findByRegionAndProvinceAndDistrict(prevRegionNo, prevProvinceNo, districtNo);
					
					BranchHome branchHome = new BranchHome();
					List<Branch> branchList = branchHome.findByRegionAndProvinceAndDistrict(prevRegionNo, prevProvinceNo, districtNo);
					
					FarmerHome farmerHome = new FarmerHome();
					List<Farmer> farmerList = farmerHome.findByRegionAndProvinceAndDistrict(prevRegionNo, prevProvinceNo, districtNo);
					
					LandRightHome landRightHome = new LandRightHome();
					List<LandRight> landRightList = landRightHome.findByRegionAndProvinceAndDistrict(prevRegionNo,prevProvinceNo, districtNo);
					
					SellingDetailHome sellingDetailHome = new SellingDetailHome();
					List<SellingDetail> sellingDetailList = sellingDetailHome.findByRegionAndProvinceAndDistrict(prevRegionNo, prevProvinceNo, districtNo);
					
					newRegionNo = prevRegionNo;
					newProvinceNo = prevProvinceNo;
					if(subDistrictList !=null && subDistrictList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo)){
						msg = Utility.get("EXIST_SUB_DISTRICT_D");
					}else if(buyerList !=null && buyerList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo)){
						msg = Utility.get("EXIST_BUYER_D");
					}else if(branchList !=null && branchList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo)){
						msg = Utility.get("EXIST_BRANCH_D");
					}else if(farmerList !=null && farmerList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo)){
						msg =Utility.get("EXIST_FARMER_D");
					}else if(landRightList !=null && landRightList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo) ){
						msg = Utility.get("EXIST_LANDRIGHT_D");
					}else if(sellingDetailList !=null && sellingDetailList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo)){
						msg = Utility.get("EXIST_SELLING_DETAIL_D");
					}else{
						newRegionNo = regionNo;
						newProvinceNo = provinceNo;
						msg = Utility.get("SAVE_SUCCESS"); 
					}
					
					Region region = new Region();
					region.setRegionNo(newRegionNo);
					
					Province province = new Province();
					province.setProvinceNo(newProvinceNo);
					province.setRegion(region);
					province.setRegionNo(newRegionNo);
					
					district.setDistrictNo(districtNo);
					district.setRegionNo(newRegionNo);
	         		district.setProvinceNo(newProvinceNo);
	         		district.setProvince(province);
	         		district.setThaiName(eform.getThaiName());
	         		district.setEngName(eform.getEngName());
	         		district.setLastUpdateDate(new Date());
	             	district.setLastUpdateBy(userLogin.getUserName());	             	
	    			home.updateDistrict(district, prevRegionNo, prevProvinceNo);
	    			sf.getCurrentSession().getTransaction().commit();
				} else { //Case Add New
					district = new District();
	         		districtNo = home.getMaxDistrictNo();
	         		district.setRegionNo(regionNo);
	         		district.setProvinceNo(provinceNo);
	         		district.setDistrictNo(districtNo);
	         		district.setThaiName(eform.getThaiName());
	         		district.setEngName(eform.getEngName());
	         		district.setLastUpdateDate(new Date());
	             	district.setLastUpdateBy(userLogin.getUserName());	             	
	    			home.saveOrUpdate(district);
	    			sf.getCurrentSession().getTransaction().commit();
	    			msg = Utility.get("SAVE_SUCCESS"); 
				}
			}
			eform.setMsg(msg);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL")); 
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}

		return mapping.findForward("district");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		DistrictForm eform = (DistrictForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		long districtNo = 0, provinceNo = 0, regionNo = 0;
		try{			
			sf.getCurrentSession().beginTransaction();	
			districtNo = Long.parseLong(eform.getDistrictNo() == null?"0":eform.getDistrictNo());
			provinceNo = Long.parseLong(eform.getProvinceNo() == null?"0":eform.getProvinceNo());
			regionNo   = Long.parseLong(eform.getRegionNo() == null?"0":eform.getRegionNo());
	     
			eform.setDistrictNo(String.valueOf(districtNo));
			eform.setProvinceNo(String.valueOf(provinceNo));
			eform.setRegionNo(String.valueOf(regionNo));
			eform.setThaiName(eform.getThaiName());
			eform.setEngName(eform.getEngName());
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //eform.setMsg("Load Data failed.");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("district");
	}
	
	// get provincelist from regionNo
	public ActionForward getProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictForm eform = (DistrictForm)form;
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
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("district");
	}
	//Ajax Method
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		DistrictForm eform = (DistrictForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List<ProvinceDistrict> proList = new ArrayList<ProvinceDistrict>();	
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
