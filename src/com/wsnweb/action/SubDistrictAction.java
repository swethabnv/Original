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
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.SellingDetailHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.SubDistrictForm;
import com.wsnweb.form.SubDistrictListForm;
import com.wsnweb.util.Utility;

public class SubDistrictAction extends Action {
	private static final Logger log = Logger.getLogger(SubDistrictAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	throws Exception
	{
		 SubDistrictForm eform = (SubDistrictForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
	     }else{
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 getProvinceList(mapping, form, request, response);
				 getDistrictList(mapping, form, request, response);
				 return edit(mapping, form, request, response);
			 } else if("GetProvince".equals(eform.getCmd())){
				 return getProvinceInfo(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else {
				 return mapping.findForward("subdistrict"); 
			 }	
		 }
	}
	

	private ActionForward save(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		SubDistrictForm eform = (SubDistrictForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictHome home = new SubDistrictHome();
		long districtNo = 0, provinceNo = 0, regionNo = 0, subDistrictNo = 0;
		int postCode = 0;
		String msg = "";
		long newRegionNo = 0;
		long newProvinceNo = 0;
		long newDistrictNo = 0;
		
		long prevRegionNo = 0;
		long prevProvinceNo = 0;
		long prevDistrictNo = 0;
		
		try {
			sf.getCurrentSession().beginTransaction();
			// --- current ---
			if(eform.getSubDistrictNo() !=null && !"".equals(eform.getSubDistrictNo())){
				subDistrictNo = Long.parseLong(eform.getSubDistrictNo());
			}
			if(eform.getDistrictNo() !=null && !"".equals(eform.getDistrictNo())){
				districtNo = Long.parseLong(eform.getDistrictNo());
			}
			if(eform.getProvinceNo() !=null && !"".equals(eform.getProvinceNo())){
				provinceNo = Long.parseLong(eform.getProvinceNo());
			}			
			if(eform.getRegionNo() !=null && !"".equals(eform.getRegionNo())){
				regionNo = Long.parseLong(eform.getRegionNo());
			}
			if(eform.getPostCode() !=null && !"".equals(eform.getPostCode())){
				postCode = Integer.parseInt(eform.getPostCode(), 10);
			}
			// --- previous ---
			if(eform.getPrevRegionNo() !=null && !"".equals(eform.getPrevRegionNo())){
				prevRegionNo = Long.parseLong(eform.getPrevRegionNo());
			}
			if(eform.getPrevProvinceNo() !=null && !"".equals(eform.getPrevProvinceNo())){
				prevProvinceNo = Long.parseLong(eform.getPrevProvinceNo());
			}
			if(eform.getPrevDistrictNo() !=null && !"".equals(eform.getPrevDistrictNo())){
				prevDistrictNo = Long.parseLong(eform.getPrevDistrictNo());
			}
			// --- check sub district duplicate ---
			List<SubDistrict> checkDupSubDistrict = home.checkDupSubDistrict(regionNo, eform.getThaiName(), provinceNo, districtNo, subDistrictNo);
			
			if(checkDupSubDistrict !=null && checkDupSubDistrict.size()>0 ){
				msg = "ไม่สามารถบันทึกได้ มีตำบล"+eform.getThaiName()+"อยู่แล้วในระบบ!";
			}else{
				SubDistrict subDistrict = null;
				if(subDistrictNo>0)
					subDistrict = home.findBySubDistrictNo(subDistrictNo);
				else
					subDistrict = home.findByCriteria(regionNo, provinceNo, districtNo, eform.getThaiName());
				if (subDistrict !=null) {  // Case Update
					
					BuyerHome buyerHome = new BuyerHome();
					List<Buyer> buyerList = buyerHome.findByRegionAndProvinceAndDistrictAndSubDistrict(prevRegionNo, prevProvinceNo, prevDistrictNo, subDistrictNo);
					
					BranchHome branchHome = new BranchHome();
					List<Branch> branchList = branchHome.findByRegionAndProvinceAndDistrictAndSubDistrict(prevRegionNo, prevProvinceNo, prevDistrictNo, subDistrictNo);
					
					FarmerHome farmerHome = new FarmerHome();
					List<Farmer> farmerList = farmerHome.findByRegionAndProvinceAndDistrictAndSubDistrict(prevRegionNo, prevProvinceNo, prevDistrictNo, subDistrictNo);
					
					LandRightHome landRightHome = new LandRightHome();
					List<LandRight> landRightList = landRightHome.findByRegionAndProvinceAndDistrictAndSubDistrict(prevRegionNo,prevProvinceNo, prevDistrictNo, subDistrictNo);
					
					SellingDetailHome sellingDetailHome = new SellingDetailHome();
					List<SellingDetail> sellingDetailList = sellingDetailHome.findByRegionAndProvinceAndDistrictAndSubDistrict(prevRegionNo, prevProvinceNo, prevDistrictNo, subDistrictNo);
					
					newRegionNo = prevRegionNo;
					newProvinceNo = prevProvinceNo;
					newDistrictNo = prevDistrictNo;
				
					if(buyerList !=null && buyerList.size()>0 && ( prevRegionNo != regionNo || prevProvinceNo != provinceNo || prevDistrictNo != districtNo )){
						msg = Utility.get("EXIST_BUYER_S");
					}else if(branchList !=null && branchList.size()>0 && ( prevRegionNo != regionNo || prevProvinceNo != provinceNo || prevDistrictNo != districtNo )){
						msg = Utility.get("EXIST_BRANCH_S");
					}else if(farmerList !=null && farmerList.size()>0 && ( prevRegionNo != regionNo || prevProvinceNo != provinceNo || prevDistrictNo != districtNo )){
						msg =Utility.get("EXIST_FARMER_S");
					}else if(landRightList !=null && landRightList.size()>0 && ( prevRegionNo != regionNo || prevProvinceNo != provinceNo || prevDistrictNo != districtNo )){
						msg = Utility.get("EXIST_LANDRIGHT_S");
					}else if(sellingDetailList !=null && sellingDetailList.size()>0 && (prevRegionNo != regionNo || prevProvinceNo != provinceNo || prevDistrictNo != districtNo )){
						msg = Utility.get("EXIST_SELLING_DETAIL_S");
					}else{
						newRegionNo = regionNo;
						newProvinceNo = provinceNo;
						newDistrictNo = districtNo;
						msg = Utility.get("SAVE_SUCCESS"); 
					}
					
					Region region = new Region();
					region.setRegionNo(newRegionNo);
					
					Province province = new Province();
					province.setProvinceNo(newProvinceNo);
					province.setRegion(region);
					province.setRegionNo(newRegionNo);
					
					District district = new District();
					district.setDistrictNo(newDistrictNo);
					district.setProvince(province);
					district.setProvinceNo(newProvinceNo);
					district.setRegionNo(newRegionNo);
					
					subDistrict.setSubDistrictNo(subDistrictNo);
					subDistrict.setRegionNo(newRegionNo);
					subDistrict.setProvinceNo(newProvinceNo);
					subDistrict.setDistrictNo(newDistrictNo);
					subDistrict.setDistrict(district);
					subDistrict.setThaiName(eform.getThaiName());
					subDistrict.setEngName(eform.getEngName());
					subDistrict.setPostCode(postCode);
	         		subDistrict.setLastUpdateDate(new Date());
	         		subDistrict.setLastUpdateBy(userLogin.getUserName());	     
	         		
	    			home.updateSubDistrict(subDistrict, prevRegionNo, prevProvinceNo, prevDistrictNo);
	    			
				} else {  // Case Add New
					subDistrict = new SubDistrict();
	         		subDistrictNo = home.getMaxSubDistrictNo()+1;
	         		subDistrict.setRegionNo(regionNo);
	         		subDistrict.setProvinceNo(provinceNo);
	         		subDistrict.setDistrictNo(districtNo);
	         		subDistrict.setSubDistrictNo(subDistrictNo);
	         		subDistrict.setEngName(eform.getEngName());
	             	subDistrict.setThaiName(eform.getThaiName());
	             	subDistrict.setPostCode(postCode);	
	    			subDistrict.setLastUpdateBy(userLogin.getUserName());
	             	subDistrict.setLastUpdateDate(new Date());
	             	home.saveOrUpdate(subDistrict);
	    			msg = Utility.get("SAVE_SUCCESS"); 
				}
			}
			sf.getCurrentSession().flush();
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_FAIL")); //eform.setMsg("Save failed.");
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("subdistrict");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		SubDistrictForm eform = (SubDistrictForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictHome home = new SubDistrictHome();
		SubDistrict subDistrict = new SubDistrict();
		long districtNo = 0, provinceNo = 0, regionNo = 0, subDistrictNo = 0;
		try{			
			sf.getCurrentSession().beginTransaction();	
			subDistrictNo = Long.parseLong(eform.getSubDistrictNo() == null?"0":eform.getSubDistrictNo());
			districtNo = Long.parseLong(eform.getDistrictNo() == null?"0":eform.getDistrictNo());
			provinceNo = Long.parseLong(eform.getProvinceNo() == null?"0":eform.getProvinceNo());
			regionNo   = Long.parseLong(eform.getRegionNo() == null?"0":eform.getRegionNo());
			subDistrict = home.findByKey(regionNo, provinceNo, districtNo, subDistrictNo);
			eform.loadFromBean(subDistrict);
			eform.setThaiName(eform.getThaiName());
			eform.setEngName(eform.getEngName());
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //eform.setMsg("Load Data failed.");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("subdistrict");
	}
	
	public ActionForward getProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictForm eform = (SubDistrictForm)form;
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
		return mapping.findForward("subdistrict");
	}
	
	// get district from regionNo and provinceNo
	public ActionForward getDistrictList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictForm eform = (SubDistrictForm)form;
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
		return mapping.findForward("subdistrict");
	}
	//Ajax Method
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictListForm eform = (SubDistrictListForm)form;
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
	
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		SubDistrictListForm eform = (SubDistrictListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List<ProvinceDistrict> dtrcList = new ArrayList<ProvinceDistrict>();
			
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
