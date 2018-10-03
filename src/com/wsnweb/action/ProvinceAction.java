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
import com.wsndata.data.EconomicBreed;
import com.wsndata.data.Farmer;
import com.wsndata.data.LandRight;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.EconomicBreedHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.SellingDetailHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.ProvinceForm;
import com.wsnweb.util.Utility;

public class ProvinceAction extends Action {
	private static final Logger log = Logger.getLogger(ProvinceAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		ProvinceForm eform = (ProvinceForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd())){
				 return edit(mapping, form, request, response);
			 } else if("GetProvinceName".equals(eform.getCmd())){
				 return getProvince(mapping, eform, request, response);
			 } else{
				 return mapping.findForward("province"); 
			 }
		}
	}

	private ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		RegionHome home = new RegionHome();
		ProvinceForm eform = (ProvinceForm)form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		List regionList = (ArrayList)session.getAttribute("regionList");
	
		try{			
			sf.getCurrentSession().beginTransaction();
			if(regionList == null)
				regionList = home.findAll();
			eform.setRegionNo(eform.getRegionNo());
			eform.setThaiName(eform.getThaiName());
			eform.setEngName(eform.getEngName());
			eform.setProvinceNo(eform.getProvinceNo());
			
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //eform.setMsg("Load Data failed.");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("province");
	}

	private ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		ProvinceForm eform = (ProvinceForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		ProvinceHome home = new ProvinceHome();
		String thaiName = "";
		String engName = "";
		String msg = Utility.get("SAVE_FAIL");
		long regionNo = 0;
		long provinceNo = 0;
		long prevRegionNo = 0;
		long newRegionNo = 0;
		
		try{			
			sf.getCurrentSession().beginTransaction();			
			thaiName = eform.getThaiName() == null?"":eform.getThaiName();
			engName = eform.getEngName() == null?"":eform.getEngName();
			if(eform.getRegionNo() !=null && !"".equals(eform.getRegionNo()))
			    regionNo = Long.parseLong(eform.getRegionNo());
			if(eform.getProvinceNo() != null && !"".equals(eform.getProvinceNo()))
				provinceNo = Long.parseLong(eform.getProvinceNo());
			if(eform.getPrevRegionNo() !=null && !"".equals(eform.getPrevRegionNo()))
				prevRegionNo = Long.parseLong(eform.getPrevRegionNo());
			
			List<Province> checkDup = home.checkDupProvince(regionNo, thaiName, provinceNo);
			
			if(checkDup !=null && checkDup.size()>0){
				msg = Utility.get("SAVE_DUPLICATE");
			}else{
				Province province = null;
				if(provinceNo>0)
					province = home.searchByProvinceNo(provinceNo);
				else
					province = home.findByName(thaiName);
				
				if(province != null) {  //Case Update
					if(checkDup !=null && checkDup.size()>0){
						msg = Utility.get("SAVE_DUPLICATE");
					}else{
						
						EconomicBreedHome ecoHome = new EconomicBreedHome();
						List<EconomicBreed> ecoList = ecoHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						DistrictHome districtHome = new DistrictHome();
						List<District> districtList = districtHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						SubDistrictHome subDistrictHome = new SubDistrictHome();
						List<SubDistrict> subDistrictList = subDistrictHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						
						BuyerHome buyerHome = new BuyerHome();
						List<Buyer> buyerList = buyerHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						BranchHome branchHome = new BranchHome();
						List<Branch> branchList = branchHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						FarmerHome farmerHome = new FarmerHome();
						List<Farmer> farmerList = farmerHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						LandRightHome landRightHome = new LandRightHome();
						List<LandRight> landRightList = landRightHome.findByRegionAndProvince(prevRegionNo,provinceNo);
						
						SellingDetailHome sellingDetailHome = new SellingDetailHome();
						List<SellingDetail> sellingDetailList = sellingDetailHome.findByRegionAndProvince(prevRegionNo, provinceNo);
						
						newRegionNo = prevRegionNo;
						if(ecoList !=null && ecoList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_ECONOMIC_BREED");
						}else if(districtList !=null && districtList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_DISTRICT");
						}else if(subDistrictList !=null && subDistrictList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_SUB_DISTRICT");
						}else if(buyerList !=null && buyerList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_BUYER");
						}else if(branchList !=null && branchList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_BRANCH");
						}else if(farmerList !=null && farmerList.size()>0 && prevRegionNo != regionNo){
							msg =Utility.get("EXIST_FARMER");
						}else if(landRightList !=null && landRightList.size()>0 && prevRegionNo != regionNo ){
							msg = Utility.get("EXIST_LANDRIGHT");
						}else if(sellingDetailList !=null && sellingDetailList.size()>0 && prevRegionNo != regionNo){
							msg = Utility.get("EXIST_SELLING_DETAIL");
						}else{
							newRegionNo = regionNo;
							msg = Utility.get("SAVE_SUCCESS"); 
						}
						
						Region region = new Region();
						region.setRegionNo(newRegionNo);
					
						
						province.setRegion(region);
						province.setProvinceNo(provinceNo);
						province.setRegionNo(newRegionNo);
						province.setThaiName(thaiName);
						province.setEngName(engName);
						province.setLastUpdateBy(userLogin.getUserName());
						province.setLastUpdateDate(new Date());						
						home.updateProvince(province);	  
						sf.getCurrentSession().getTransaction().commit();
						eform.setMsg(msg);
					}
				}else{  //Case Add New
					province = new Province();
					provinceNo = home.getMaxId();
					province.setProvinceNo(provinceNo);
					province.setRegionNo(regionNo);
					province.setThaiName(thaiName);
					province.setEngName(engName);
					province.setLastUpdateBy(userLogin.getUserName());
					province.setLastUpdateDate(new Date());
					home.saveOrUpdate(province);	  
					sf.getCurrentSession().getTransaction().commit();
					msg = Utility.get("SAVE_SUCCESS"); 
				}
				
			}
			eform.setMsg(msg);

		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL")); 
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("province");
	}
	
	//Ajax Method
	public ActionForward getProvince(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			ProvinceForm eform = (ProvinceForm)form;
			Province prov = null, provObj = null;
			long provinceNo = 0;
			try{

				sf.getCurrentSession().beginTransaction();
				ProvinceHome home = new ProvinceHome();
				
				provinceNo = Long.parseLong("".equals(eform.getProvinceNo())?"0":eform.getProvinceNo());
			    if(provinceNo != 0)
			    	provObj = home.searchByProvinceNo(provinceNo);
			    if(provinceNo == 0 || (provObj!=null && !provObj.getThaiName().equals(eform.getThaiName())))
			    	prov = home.findByName(eform.getThaiName());
			      
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{"lastUpdateDate", "lastUpdateBy", "region", "district", "checkBox", "linkImageEdit", "linkImageDel", "regionName"});
			    String result = JSONArray.fromObject(prov, jsonConfig).toString();
			    result = result.trim().replaceAll("&nbsp;", " ");
			    wt.print(result);
			    wt.flush();
			    wt.close();
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}finally{
				sf.getCurrentSession().close();
			}
			return null;
	}
	

}
