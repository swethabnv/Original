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
import com.wsndata.data.District;
import com.wsndata.data.Province;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.BranchListForm;

public class BranchListAction extends Action {
	private static final Logger log = Logger.getLogger(BranchListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		BranchListForm eform = (BranchListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if("Delete".equals(eform.getCmd())){
				return deleteBranch(mapping, eform, request, response);
			} else if("DirtyList".equals(eform.getCmd())){
				return dirtylist(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else if("GetSubDistrict".equals(eform.getCmd())){
				 return getSubDistrictInfo(mapping, form, request, response);
			} else {
				return searchBranch(mapping, form, request, response);
			}
		}
	}
	
	public ActionForward dirtylist(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		BranchListForm eform = (BranchListForm) form;
		HttpSession session = request.getSession();
		List branchList = (List) session.getAttribute("branchList");
		GridUtil.applyCheckValue(branchList, eform.getDelBranchCode(),"branchCode",eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		Collections.sort(branchList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
		return mapping.findForward("branchList");
	}

	private ActionForward searchBranch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		BranchListForm eform = (BranchListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List getBranchList = null;
		List branchList = new ArrayList();
		List branchTmp = new ArrayList();
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		String branchName= "";
		long userLevel=0, districtNo=0, subDistrictNo=0, provinceNo=0, regionNo=0;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceHome provHome = new ProvinceHome();

			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
			if(userLogin.getLevel()>2) {
				Province province = provHome.searchByProvinceNo(userLogin.getProvinceNo());
				provinceList.add(province);
			} else if(userLogin.getLevel()>1)
				provinceList = provHome.retrieveByRegionNo(userLogin.getRegionNo());
			else
				provinceList = provHome.findAll();
			if(provinceList !=null && provinceList.size()>0)
				provinceList.add(0,prov);
			//--- ดึงอำเภอ ออกมา --- //
			District district = new District();
			district.setDistrictNo(0);
			district.setProvinceNo(0);
			district.setThaiName("กรุณาเลือก");
			districtList.add(0,district);
			//--- ดึงตำบล ออกมา --- //
			SubDistrict subDistrict = new SubDistrict();
			subDistrict.setProvinceNo(0);
			subDistrict.setDistrictNo(0);
			subDistrict.setSubDistrictNo(0);
			subDistrict.setThaiName("กรุณาเลือก");
			subDistrictList.add(0,subDistrict);
			
			BranchHome bHome = new BranchHome();
			
			branchTmp = bHome.findByPBranch(Long.parseLong(userLogin.getBranchCode()));
			branchList.add(bHome.findByBranchCode(Long.parseLong(userLogin.getBranchCode())));
			branchList.addAll(getPBranch(branchTmp,Long.parseLong(userLogin.getBranchCode())));

			Collections.sort(branchList, new DCSCompare("provinceNo",true));
			Branch branch = new Branch();
			//branch.setBranchCode(0);
			//branch.setSeq(0); // first row
			//branch.setBranchName("กรุณาเลือก");
			//branchList.add(0,branch);
			session.setAttribute("userBranchList", branchList);
			
			branchName = eform.getBranchName()==null?"":eform.getBranchName();
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
			
			getBranchList=bHome.searchByCriteria2(branchName, regionNo, provinceNo, districtNo, subDistrictNo);
			branchList = new ArrayList();
			if(getBranchList !=null && getBranchList.size() > 0){
				for(int i=0; i< getBranchList.size(); i++){
					branch = new Branch();
					Object[] obj = (Object[])getBranchList.get(i);
					branch.setBranchName(obj[0].toString());
					branch.setBranchCode(Long.parseLong(obj[1].toString()));
					branch.setPbranchCode(Long.parseLong(obj[2].toString()));
					branch.setPbranchName(obj[3].toString());
					branch.setAddress(obj[4].toString());
					branch.setTel(obj[5].toString());
					branch.setFax(obj[6].toString());
					branch.setProvinceName(obj[7].toString());
					branch.setDistrictName(obj[8].toString());
					branch.setSubDistrictName(obj[9].toString());
					branch.setRegionNo(Long.parseLong(obj[10].toString()));
					branch.setProvinceNo(Long.parseLong(obj[11].toString()));
					branch.setDistrictNo(Long.parseLong(obj[12].toString()));
					branch.setSubDistrictNo(Long.parseLong(obj[13].toString()));
					branchList.add(branch);
				}
				Collections.sort(branchList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			session.setAttribute("branchList", branchList);
			session.setAttribute("countBranch", branchList!=null?branchList.size():0);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("branchList");
	}

	private ActionForward deleteBranch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		BranchListForm eform = (BranchListForm)form;
		HttpSession session = request.getSession();
		List branchList = (List) session.getAttribute("branchList");
		List userBranchList = (List) session.getAttribute("userBranchList");
		GridUtil.applyCheckValue(branchList, eform.getDelBranchCode(), "branchCode", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		BranchHome home = new BranchHome();
		try{
			sf.getCurrentSession().beginTransaction();	
			for (int i=0; i<branchList.size(); i++) {
				Branch branch = (Branch)branchList.get(i);
				if (branch.getCheckBox()==true){
					if (home.findbyTable("User", branch.getBranchCode())
							&& home.findbyTable("Plant", branch.getBranchCode())
							&& home.findbyTable("BankBranch", branch.getBranchCode())
							&& home.findbyPBranch(branch.getBranchCode())) {
						if (branch.getBranchCode()==branch.getPbranchCode()) {
							branch.setPbranchCode(1);
							home.saveOrUpdate(branch);
							sf.getCurrentSession().getTransaction().commit();
							sf.getCurrentSession().beginTransaction();	
						}
							home.delete(branch);
						
						for (int j = 0; j < userBranchList.size(); j++) {
							Branch userBranch = (Branch)userBranchList.get(j);
							if (userBranch.getBranchCode()==branch.getBranchCode()){
								userBranchList.remove(j);
								break;
							}
						}
					}else{
						eform.setErrMessage(branch.getBranchName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ทำการลบข้อมูลสาขาไปทั้งสิ้น "+eform.getDelBranchCode().length+" records");
			session.setAttribute("userBranchList", userBranchList);
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return searchBranch(mapping, eform, request, response);
	}
	
	private List<Branch> getPBranch(List<Branch> branchTmp,long branchCode) {
		List branchList =  new ArrayList();
		List pbranch = new ArrayList();
		try {
			BranchHome branchHome = new BranchHome();
			for (int i = 0; i < branchTmp.size(); i++) {
				Branch branch = new Branch();
				branch = (Branch)branchTmp.get(i);
				if (branchCode == branch.getBranchCode()) {
					continue;
				}
				
				pbranch = branchHome.findByPBranch(branch.getBranchCode());
				if (pbranch.size()>0) {
					branchList.add(branch);
					branchList.addAll(getPBranch(pbranch,branch.getBranchCode()));
				}else{
					branchList.add(branch);
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 log.error(e,e);
		}
		return branchList;
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BranchListForm eform = (BranchListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();

			if(eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(eform.getProvinceNo());
			}
		    
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
	
	//Ajax Method
	public ActionForward getSubDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BranchListForm eform = (BranchListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List subList = new ArrayList();
			if(eform.getDistrictNo() > 0 && eform.getProvinceNo() > 0) {
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(eform.getDistrictNo(), eform.getProvinceNo());
			}
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng"});   

		    String result = JSONArray.fromObject(subList, jsonConfig).toString();
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
