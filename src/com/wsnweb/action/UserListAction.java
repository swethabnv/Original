package com.wsnweb.action;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
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

import sun.misc.BASE64Encoder;

import com.dcs.hibernate.HibernateHome;
import com.dcs.util.DCSCompare;
import com.dcs.util.GridUtil;
import com.wsndata.data.District;
import com.wsndata.data.Login;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.Region;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.dbaccess.LoginHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.PrefixHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.UserListForm;
import com.wsnweb.util.Utility;

public class UserListAction extends Action {
	private static final Logger log = Logger.getLogger(UserListAction.class);
	private final String INITIAL_PASSWORD = Utility.get("DEFAULT_PASSWORD");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserListForm eform = (UserListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Active".equals(eform.getCmd())) {
				return activeUser(mapping, eform, request, response);
			} else if ("Delete".equals(eform.getCmd())) {
				return deleteUser(mapping, eform, request, response);
			} else if ("DirtyList".equals(eform.getCmd())) {
				return dirtylist(mapping, form, request, response);
			} else if ("Reset".equals(eform.getCmd())) {
				return resetPassword(mapping, eform, request, response);
			 } else if("GetProvince".equals(eform.getCmd())){
				 return getProvinceInfo(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else if("GetSubDistrict".equals(eform.getCmd())){
				 return getSubDistrictInfo(mapping, form, request, response);
			} else {
				return searchUser(mapping, eform, request, response);
			}
		}
	}

	public ActionForward dirtylist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserListForm eform = (UserListForm) form;
		HttpSession session = request.getSession();
		List userList = (List) session.getAttribute("userList");
		GridUtil.applyCheckValue(userList, eform.getDelUserName(), "userName", eform.getDisplayRow() * (eform.getLastPage() - 1), eform.getDisplayRow());
		Collections.sort(userList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
		return mapping.findForward("list");
	}

	public ActionForward searchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserListForm eform = (UserListForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List getUserList = null;
		List userList = new ArrayList();
		List branchList = (List) session.getAttribute("userBranchList");
		List prefixList = (List) session.getAttribute("userPrefixList");
		User userLogin = (User) session.getAttribute("userLogin");
		List regionList = new ArrayList();
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		String userName = "", firstName = "", lastName = "", email = "";
		long districtNo = 0, subDistrictNo = 0, provinceNo = 0, regionNo = 0, level = 0, groupUser = 0;
		try {
			UserHome userHome = new UserHome();
			PrefixHome prefHome = new PrefixHome();
			RegionHome regHome = new RegionHome();
			ProvinceHome provHome = new ProvinceHome();
			
			sf.getCurrentSession().beginTransaction();
			//--- ดึงภูมิภาค ออกมา --- //
			Region reg = new Region();
			reg.setRegionNo(0);
			reg.setRegionName("กรุณาเลือก");
			if(userLogin.getLevel()>1) {
				Region region = regHome.searchByNo(userLogin.getRegionNo());
				regionList.add(region);
			} else
				regionList = regHome.findAll();
			regionList.add(0, reg);
			//--- ดึงจังหวัด ออกมา --- //
			Province prov=new Province();
			prov.setProvinceNo(0);
			prov.setThaiName("กรุณาเลือก");
//			if(userLogin.getLevel()>2) {
//				Province province = provHome.searchByProvinceNo(userLogin.getProvinceNo());
//				provinceList.add(province);
//			} else if(userLogin.getLevel()>1)
//				provinceList = provHome.retrieveByRegionNo(userLogin.getRegionNo());
//			else
//				provinceList = provHome.findAll();
//			if(provinceList !=null && provinceList.size()>0)
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
			
			userName = eform.getUserName() == null ? "" : eform.getUserName().trim();
			firstName = eform.getFirstName() == null ? "" : eform.getFirstName().trim();
			lastName = eform.getLastName() == null ? "" : eform.getLastName().trim();
			email = eform.getEmail() == null ? "" : eform.getEmail().trim();
			regionNo = eform.getRegionNo()==null?0:Long.parseLong(eform.getRegionNo());
			provinceNo = eform.getProvinceNo()==null?0:Long.parseLong(eform.getProvinceNo());
			districtNo = eform.getDistrictNo()==null?0:Long.parseLong(eform.getDistrictNo());
			subDistrictNo = eform.getSubDistrictNo()==null?0:Long.parseLong(eform.getSubDistrictNo());
			level = eform.getLevel();
			groupUser = eform.getGroupUser();

			if (userLogin.getLevel()>2)
				regionNo = userLogin.getRegionNo();
			if (regionNo==0 && (userLogin.getLevel()>1 || level>1))
				regionNo = userLogin.getRegionNo();
			if (provinceNo==0 && (userLogin.getLevel()>2 || level>2))
				provinceNo = userLogin.getProvinceNo();
			if (districtNo==0 && (userLogin.getLevel()>3 || level>3))
				districtNo = userLogin.getDistrictNo();
			if (subDistrictNo==0 && (userLogin.getLevel()>4 || level>4))
				subDistrictNo = userLogin.getSubDistrictNo();
			
			if ("".equals(userName) && "".equals(firstName) && "".equals(lastName) && "".equals(email) && regionNo != 0 && provinceNo != 0 && districtNo != 0 && level != 0 && groupUser != 0) {
				getUserList = userHome.findAll(userLogin.getLevel());
			} else {
				getUserList = userHome.searchByCriteria(userName, firstName, lastName, level, userLogin.getLevel(), groupUser, regionNo, provinceNo, districtNo, subDistrictNo); // find user by criteria
			}

			if (getUserList != null && getUserList.size() > 0) {
				for (int i = 0; i < getUserList.size(); i++) {
					User user = new User();
					Object[] obj = (Object[]) getUserList.get(i);
					user.setUserName(obj[0].toString());
					user.setPassword(obj[1].toString());
					user.setBranchCode(obj[2].toString());
					user.setBranchName(obj[3].toString());
					if(obj[4]!=null)
						user.setAbbrPrefix(obj[4].toString());
					else
						user.setAbbrPrefix("คุณ");
					user.setFirstName(obj[5].toString());
					user.setLastName(obj[6].toString());
					user.setName(user.getAbbrPrefix() + " " + user.getFirstName() + " " + user.getLastName());
					user.setEmail(obj[7]==null?"":obj[7].toString());
					if ("1".equals(obj[8].toString()))
						user.setStatus("Active");
					else
						user.setStatus("Inactive");
					user.setRegion(obj[9]==null?"":obj[9].toString());
					user.setProvince(obj[10]==null?"":obj[10].toString());
					user.setDistrict(obj[11]==null?"":obj[11].toString());
					user.setSubDistrict(obj[12]==null?"":obj[12].toString());
					if(obj[13]!=null) {
						if("1".equals(obj[13].toString()))
							user.setStrLevel("ประเทศ");
						if("2".equals(obj[13].toString()))
							user.setStrLevel("ภูมิภาค");
						if("3".equals(obj[13].toString()))
							user.setStrLevel("จังหวัด");
						if("4".equals(obj[13].toString()))
							user.setStrLevel("อำเภอ");
						if("5".equals(obj[13].toString()))
							user.setStrLevel("ตำบล");
					} else {
						user.setStrLevel("ไม่มี");
					}
					//user.setStrLevel(obj[9]==null?"":obj[9].toString());
					userList.add(user);
				}
			}
			
			if(prefixList == null){
				prefixList = prefHome.findAll(); 
			}
			eform.setBranchCode(eform.getBranchCode());
			Collections.sort(userList, new DCSCompare(eform.getSortColumn(), eform.isSortAscending()));
			session.setAttribute("userPrefixList", prefixList);
			session.setAttribute("userList", userList);
			session.setAttribute("userBranchList", branchList);
			session.setAttribute("countUser", userList!=null?userList.size():0);
			session.setAttribute("regionList", regionList);
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
		return mapping.findForward("list");
	}

	public ActionForward activeUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserListForm eform = (UserListForm) form;
		HttpSession session = request.getSession();
		List userList = (List) session.getAttribute("userList");
		MessageDigest digest = MessageDigest.getInstance("MD5");
		GridUtil.applyCheckValue(userList, eform.getDelUserName(), "userName",
				eform.getDisplayRow() * (eform.getLastPage() - 1), eform
						.getDisplayRow());
		UserHome userHome = new UserHome();
		LoginHome loginHome = new LoginHome();
		int delCount = 0;
		try {
			digest.update(INITIAL_PASSWORD.getBytes("UTF-8"));
			byte raw[] = digest.digest();
			String passWord = (new BASE64Encoder()).encode(raw);
			
			for (int i = 0; i < userList.size(); i++) {
				User user = (User) userList.get(i);
				if (user.getCheckBox() == true) {
					sf.getCurrentSession().beginTransaction();
					// active-inactive user
					Login log = new Login();
					log.setUserName(user.getUserName());
					loginHome.delete(log);
					userHome.activeUser(user.getUserName(), passWord);
					sf.getCurrentSession().getTransaction().commit();
					delCount++;
				}
			}
			log.info("Active/Inactive User " + delCount + " records");

		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return searchUser(mapping, form, request, response);
	}

	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserListForm eform = (UserListForm) form;
		HttpSession session = request.getSession();
		List userList = (List) session.getAttribute("userList");
		GridUtil.applyCheckValue(userList, eform.getDelUserName(), "userName",
				eform.getDisplayRow() * (eform.getLastPage() - 1), eform
						.getDisplayRow());
		UserHome userHome = new UserHome();
		PlantHome plantHome = new PlantHome();
		int delCount = 0;
		try {
			sf.getCurrentSession().beginTransaction();
			for (int i = 0; i < userList.size(); i++) {
				User user = (User) userList.get(i);
				if (user.getCheckBox() == true) {
					if(plantHome.findbyCreateBy(user.getUserName())) {
						userHome.delUser(user.getUserName());
						delCount++;
					}else{
						eform.setErrMessage(user.getUserName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			log.info("Delete User " + delCount + " accounts");

		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e,e);
		} finally {
			sf.getCurrentSession().close();
		}
		return searchUser(mapping, form, request, response);
	}
	
	public ActionForward resetPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserListForm eform = (UserListForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserHome userHome = new UserHome();
		try {
			sf.getCurrentSession().beginTransaction();

			MessageDigest digestCurr = MessageDigest.getInstance("MD5");
			digestCurr.update(INITIAL_PASSWORD.getBytes("UTF-8"));
			byte raw[] = digestCurr.digest();
			String currentPassword = (new BASE64Encoder()).encode(raw);

			// password
			User user = userHome.findByUser(eform.getUserName());
			user.setPassword(currentPassword);
			user.setLastUpdateBy(userLogin.getUserName());
			user.setLastUpdateDate(new Date());
			userHome.saveOrUpdate(user);
			LoginHome loginHome = new LoginHome();
			Login log = new Login();
			log.setUserName(user.getUserName());
			loginHome.delete(log);
			sf.getCurrentSession().getTransaction().commit();
			eform.setErrMessage("รีเซ็ตพาสเวิร์ดสำเร็จ");
			eform.setUserName("");
				
		} catch (Exception e) {
			eform.setErrMessage(e.getMessage());
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return searchUser(mapping, form, request, response);
	}

	//Ajax Method
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		UserListForm eform = (UserListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			ProvinceHome pHome = new ProvinceHome();		
			List proList = new ArrayList();			
			
			long regionNo = 0;
			
			if(eform.getRegionNo()!=null && !"".equals(eform.getRegionNo()))
				regionNo = Long.parseLong(eform.getRegionNo());
			
			if(regionNo > 0) {
				if(userLogin.getLevel()>2)
					proList = home.selectByProvinceNo(userLogin.getProvinceNo());
				else
					proList = home.filterByRegionNoDistinct(regionNo);
			}
			
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
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		UserListForm eform = (UserListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
			
			long provinceNo = 0;
			if(!"".equals(eform.getProvinceNo()))
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());

			if(provinceNo > 0) {
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
		UserListForm eform = (UserListForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();
			List<ProvinceDistrict> subList = new ArrayList<ProvinceDistrict>();
			long districtNo = 0;
			long provinceNo = 0;
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo())) {
				districtNo = Long.parseLong(eform.getDistrictNo()==null?"0":eform.getDistrictNo());
				provinceNo = Long.parseLong(eform.getProvinceNo()==null?"0":eform.getProvinceNo());
			}
			if(districtNo > 0 && provinceNo > 0) {
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
