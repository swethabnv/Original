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
import com.wsndata.data.Buyer;
import com.wsndata.data.District;
import com.wsndata.data.Province;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.BuyerListForm;

public class BuyerListAction extends Action {
	private static final Logger log = Logger.getLogger(BuyerListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerListForm eform = (BuyerListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Delete".equals(eform.getCmd())) {
				return delete(mapping, eform, request, response);
			} else if ("DirtyList".equals(eform.getCmd())) {
				return dirtyList(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else if("GetSubDistrict".equals(eform.getCmd())){
				 return getSubDistrictInfo(mapping, form, request, response);
			} else {
				return search(mapping, eform, request, response);
			}
		}
	}

	private ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerListForm eform = (BuyerListForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List buyerList = new ArrayList();
		List getBuyerList = new ArrayList();
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		long districtNo=0, subDistrictNo=0, provinceNo=0, regionNo=0, userLevel=0;
		String buyerName = "";
		try {
			sf.getCurrentSession().beginTransaction();
			ProvinceHome provHome = new ProvinceHome();

			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
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
			
			BuyerHome home = new BuyerHome();
			Buyer buyer = new Buyer();
			buyerName = eform.getBuyerName() == null ? "" : eform.getBuyerName();
			provinceNo = Long.parseLong(eform.getProvinceNo()==null || eform.getProvinceNo().equals("")?"0":eform.getProvinceNo());
			districtNo = Long.parseLong(eform.getDistrictNo()==null || eform.getDistrictNo().equals("")?"0":eform.getDistrictNo());
			subDistrictNo = Long.parseLong(eform.getSubDistrictNo()==null || eform.getSubDistrictNo().equals("")?"0":eform.getSubDistrictNo());
			userLevel = userLogin.getLevel();

			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();
			
			getBuyerList = home.findByCriteria(buyerName, regionNo, provinceNo, districtNo, subDistrictNo);
			buyerList = new ArrayList();
			if(getBuyerList !=null && getBuyerList.size() > 0){
				for(int i=0; i< getBuyerList.size(); i++){
					buyer = new Buyer();
					Object[] obj = (Object[])getBuyerList.get(i);
					buyer.setBuyerId(Long.parseLong(obj[0].toString()));
					buyer.setBuyerName(obj[1].toString());
					buyer.setProvinceName(obj[2].toString());
					buyer.setDistrictName(obj[3].toString());
					buyer.setSubDistrictName(obj[4].toString());
					buyer.setProvinceNo(Long.parseLong(obj[5].toString()));
					buyer.setDistrictNo(Long.parseLong(obj[6].toString()));
					buyer.setSubDistrictNo(Long.parseLong(obj[7].toString()));
					buyer.setMobile(obj[8].toString());
					buyerList.add(buyer);
				}
				Collections.sort(buyerList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			}
			session.setAttribute("buyerList", buyerList);
			session.setAttribute("countBuyer", buyerList!=null?buyerList.size():0);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("buyerList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		BuyerListForm eform = (BuyerListForm) form;
		HttpSession session = request.getSession();
		List buyerList = (List) session.getAttribute("buyerList");
		Collections.sort(buyerList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
		GridUtil.applyCheckValue(buyerList, eform.getDelBuyer(), "buyerId",	eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		
		return mapping.findForward("buyerList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		BuyerListForm eform = (BuyerListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		List buyerList = (List) session.getAttribute("buyerList");
		GridUtil.applyCheckValue(buyerList, eform.getDelBuyer(), "buyerId",eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		try {
			sf.getCurrentSession().beginTransaction();
			BuyerHome home = new BuyerHome();
			for (int i = 0; i < buyerList.size(); i++) {
				Buyer buyer = (Buyer) buyerList.get(i);
				if (buyer.getCheckBox() == true) {
					home.delete(buyer);
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบข้อมูลผู้ซื้อไปทั้งสิ้น " + eform.getDelBuyer().length
					+ " records");
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return search(mapping, form, request, response);
	}
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		BuyerListForm eform = (BuyerListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
			
			long provinceNo = 0;
			if(!"".equals(eform.getProvinceNo())) {
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
				if(userLogin.getLevel()>3)
					dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
				else
					dtrcList = home.filterByProvinceNo(provinceNo);
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
		BuyerListForm eform = (BuyerListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List subList = new ArrayList();	
			long districtNo = 0;
			long provinceNo = 0;
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo())) {
				districtNo = Long.parseLong(eform.getDistrictNo()==null?"0":eform.getDistrictNo());
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
				if(userLogin.getLevel()>4)
					subList = home.selectBySubDistrictNo(userLogin.getSubDistrictNo());
				else
					subList = home.filterByDistrictNo(districtNo, provinceNo);
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
