package com.wsnweb.action;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.wsndata.data.Branch;
import com.wsndata.data.District;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.Login;
import com.wsndata.data.Menu;
import com.wsndata.data.Prefix;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.data.UserAuthorize;
import com.wsndata.data.UserFarmerGroup;
import com.wsndata.data.UserProvince;
import com.wsndata.data.UserRegion;
import com.wsndata.data.UserType;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.LoginHome;
import com.wsndata.dbaccess.MenuHome;
import com.wsndata.dbaccess.PrefixHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsndata.dbaccess.UserAuthorizeHome;
import com.wsndata.dbaccess.UserHome;
import com.wsndata.dbaccess.UserTypeHome;
import com.wsnweb.form.UserForm;
import com.wsnweb.util.Utility;

public class UserAction extends Action {

	private static final Logger log = Logger.getLogger(LoginAction.class);
	private final String INITIAL_PASSWORD = Utility.get("DEFAULT_PASSWORD");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm eform = (UserForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			if ("Save".equals(eform.getCmd())) {
				return saveUser(mapping, form, request, response);
			} else if ("Edit".equals(eform.getCmd())) {
				load(mapping, form, request, response);
				return edit(mapping, form, request, response);
			} else if ("ChangePassword".equals(eform.getCmd())) {
				return changePassword(mapping, form, request, response);
			} else if ("GetUserName".equals(eform.getCmd())){
				return getUserName(mapping, eform, request, response);
			} else if ("LogOff".equals(eform.getCmd())){
				return mapping.findForward("unauthorize");
			 } else if("GetRegion".equals(eform.getCmd())){
				 return getRegionInfo(mapping, form, request, response);
			 } else if("GetProvince".equals(eform.getCmd())){
				 return getProvinceInfo(mapping, form, request, response);
			 } else if("GetDistrict".equals(eform.getCmd())){
				 return getDistrictInfo(mapping, form, request, response);
			 } else if("GetSubDistrict".equals(eform.getCmd())){
				 return getSubDistrictInfo(mapping, form, request, response);
			 } else if("getCooperative".equals(eform.getCmd())){
				 return getCooperativeInfo(mapping, form, request, response);
			}  else {
				return load(mapping, form, request, response);
			}
		}
	}

	public ActionForward saveUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm eform = (UserForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserHome userHome = new UserHome();
		MenuHome mhome = new MenuHome();
		UserTypeHome uTypehome = new UserTypeHome();
		UserAuthorizeHome authHome = new UserAuthorizeHome();
		ProvinceHome pHome = new ProvinceHome();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		String msg = Utility.get("SAVE_FAIL");// msg = "การบันทึกล้มเหลว";
		try {
			digest.update(INITIAL_PASSWORD.getBytes("UTF-8"));
			byte raw[] = digest.digest();
			String password = (new BASE64Encoder()).encode(raw);
			sf.getCurrentSession().beginTransaction();
			sf.getCurrentSession().clear();
			
			User userObj = userHome.findByUser(eform.getUserName().toLowerCase());
			if (userObj != null) {
				if ("Edit".equals(eform.getMsg())) {
					
					boolean isDel = false;
					
					if(userObj.getUserRegion()!=null && userObj.getUserRegion().size() > 0){
						
						userHome.delUserRegion(userObj.getUserName());
						isDel = true;
					}
					
					if(userObj.getUserProvince()!=null && userObj.getUserProvince().size() > 0){
						userHome.delUserProvince(userObj.getUserName());
						isDel = true;
					}
					
					if(userObj.getUserFarmerGroup()!=null && userObj.getUserFarmerGroup().size() > 0){
						userHome.delUserFarmerGroup(userObj.getUserName());
						isDel = true;
					}
					
					
					if(isDel == true){
						uTypehome.deleteByUserName(eform.getUserName());
						//userHome.saveOrUpdate(userObj);
						//sf.getCurrentSession().flush();
						sf.getCurrentSession().getTransaction().commit();
						sf.getCurrentSession().beginTransaction();
					}
					
					
					eform.loadToBean(userObj);
					if(!eform.getUserName().toLowerCase().equals(userLogin.getUserName())) {
						userObj.setDistrictNo(eform.getDistrictNo());
						userObj.setSubDistrictNo(eform.getSubDistrictNo());
						userObj.setLevel(eform.getLevel());
					}
					userObj.setLastUpdateBy(userLogin.getUserName().toLowerCase());
					userObj.setLastUpdateDate(new Date());
					if ("".equals(userObj.getPassword())) {
						userObj.setPassword(password);
					}
					
					
					
					// --- set into userRegion --- eform.getUserRegion();
					Set<UserRegion> userRegionSet =  new HashSet<UserRegion>();
					//String[] regionNo = eform.getUserRegion(); //comment by Music 20/09/2017
					if(eform.getUserRegionNo() != null){
						String[] regionNo = eform.getUserRegionNo().split(","); //Add by Music 20/09/2017
						if(regionNo.length > 0){
							for (String item : regionNo) {
								if(item != null){
									UserRegion reg = new UserRegion();
									reg.setUser(userObj);
									reg.setUserName(userObj.getUserName());
									reg.setRegionNo(Long.parseLong(item));
									userRegionSet.add(reg);
								}
							}
							userObj.setUserRegion(userRegionSet);
						}
					}
					// ----
					
					// --- set into userProvince --- eform.getUserProvince();
					Set<UserProvince> userProvinceSet =  new HashSet<UserProvince>();
					//String[] provinceNo = eform.getUserProvince(); //comment by Music 20/09/2017
					if(eform.getUserProvinceNo() != null){
						String[] provinceNo = eform.getUserProvinceNo().split(","); //Add by Music 20/09/2017
						if(provinceNo.length > 0){
							for (String provItem : provinceNo) {
								if(provItem != null && !provItem.equals("")){
									Province province = pHome.searchByProvinceNo(Long.parseLong(provItem));
									UserProvince prov = new UserProvince();
									prov.setUser(userObj);
									prov.setUserName(userObj.getUserName());
									prov.setRegionNo(province.getRegionNo());
									prov.setProvinceNo(Long.parseLong(provItem));
									userProvinceSet.add(prov);
								}
							}	
							userObj.setUserProvince(userProvinceSet);
						}
					}
					// -----
					
					// -----
					
					// --- set into userFarmerGroup --- eform.getUserFarmerGroup();
					Set<UserFarmerGroup> userFarmerGroupSet =  new HashSet<UserFarmerGroup>();
					//String[] farmerGroup = eform.getUserFarmerGroup(); //comment by Music 20/09/2017
					if(eform.getUserFarmerGroupId() != null){
						String[] farmerGroup = eform.getUserFarmerGroupId().split(","); //Add by Music 20/09/2017
						if (farmerGroup.length > 0) {
							for (String item : farmerGroup) {
								UserFarmerGroup farmer = new UserFarmerGroup();
								farmer.setUser(userObj);
								farmer.setUserName(userObj.getUserName());
								farmer.setFarmerGroupId(Long.parseLong(item));
								userFarmerGroupSet.add(farmer);
							}
							userObj.setUserFarmerGroup(userFarmerGroupSet);
						} 
					}
					
					
					
					userHome.saveOrUpdate(userObj);
					sf.getCurrentSession().getTransaction().commit();
					sf.getCurrentSession().beginTransaction();

					if(eform.getUserType()!=null) {
//						if(eform.getUserType().length==1)
//							userObj.setGroupUser(eform.getUserType()[0]);
						String[] userGroup = {"กอ.รมน.", "สหกรณ์", "กลุ่มเกษตรกร", "หอการค้า", "ผู้อนุมัติ"};
						int uGroup = Arrays.asList(userGroup).indexOf(eform.getUserType()[eform.getUserType().length-1]);
						userObj.setGroupUser(String.valueOf(uGroup+1));
						
						for(int i=0;i<eform.getUserType().length;i++) {
							UserType userType = new UserType();
							userType.setUserName(eform.getUserName());
							userType.setUserType(eform.getUserType()[i]);
							userType.setLastUpdateBy(eform.getUserName());
							userType.setLastUpdateDate(new Date());
							
							uTypehome.save(userType);
						}
					}
					
					
				} else {
					sf.getCurrentSession().getTransaction().commit();
					msg = Utility.get("SAVE_DUPLICATE");//msg = "มีข้อมูลนี้อยู่แล้ว";
					throw new Exception();
				}
			} else {
				// create New User
				userObj = new User();
				userObj.setUserName(eform.getUserName().toLowerCase());
				eform.loadToBean(userObj);
				
				userObj.setLevel(eform.getLevel());
				
				userObj.setDistrictNo(eform.getDistrictNo());
				userObj.setSubDistrictNo(eform.getSubDistrictNo());
				userObj.setPassword(password);
				userObj.setLastUpdateBy(userLogin.getUserName().toLowerCase());
				userObj.setLastUpdateDate(new Date());
				userObj.setCreateBy(userLogin.getUserName().toLowerCase());
				userObj.setCreateDate(new Date());
				
				
				// --- set into userRegion --- eform.getUserRegion();
				Set<UserRegion> userRegionSet =  new HashSet<UserRegion>();
				//String[] regionNo = eform.getUserRegion(); //comment by Music 20/09/2017
				if(eform.getUserRegionNo() != null){
					String[] regionNo = eform.getUserRegionNo().split(","); //Add by Music 20/09/2017
					if(regionNo.length > 0){
						for (String item : regionNo) {
							if(item != null && !item.equals("")){
								UserRegion reg = new UserRegion();
								userObj.setRegionNo(Long.parseLong(item));
								reg.setUser(userObj);
								reg.setUserName(userObj.getUserName());
								reg.setRegionNo(Long.parseLong(item));
								userRegionSet.add(reg);
							}
						}
						userObj.setUserRegion(userRegionSet);
					}
				}
				// ----
				
				// --- set into userProvince --- eform.getUserProvince();
				Set<UserProvince> userProvinceSet =  new HashSet<UserProvince>();
				//String[] provinceNo = eform.getUserProvince(); //comment by Music 20/09/2017
				if(eform.getUserProvinceNo() != null){
					String[] provinceNo = eform.getUserProvinceNo().split(","); //Add by Music 20/09/2017
					if(provinceNo.length > 0){
						for (String provItem : provinceNo) {
							if(provItem != null && !provItem.equals("")){
								UserProvince prov = new UserProvince();
								Province province = pHome.searchByProvinceNo(Long.parseLong(provItem));
								prov.setUser(userObj);
								prov.setUserName(userObj.getUserName());
								prov.setRegionNo(province.getRegionNo());
								prov.setProvinceNo(Long.parseLong(provItem));
								userProvinceSet.add(prov);
							
							}
						}	
						userObj.setUserProvince(userProvinceSet);
					}
				}
				// -----
				
				// --- set into userFarmerGroup --- eform.getUserFarmerGroup();
				Set<UserFarmerGroup> userFarmerGroupSet =  new HashSet<UserFarmerGroup>();
				//String[] farmerGroup = eform.getUserFarmerGroup(); //comment by Music 20/09/2017
				if(eform.getUserFarmerGroupId() != null){
					String[] farmerGroup = eform.getUserFarmerGroupId().split(","); //Add by Music 20/09/2017
					if (farmerGroup.length > 0) {
						for (String item : farmerGroup) {
							if(item != null && !item.equals("")){
								UserFarmerGroup farmer = new UserFarmerGroup();
								userObj.setFarmerGroupId(Long.parseLong(item));
								///
								farmer.setUser(userObj);
								farmer.setUserName(userObj.getUserName());
								farmer.setFarmerGroupId(Long.parseLong(item));
								userFarmerGroupSet.add(farmer);
							}
						}
						userObj.setUserFarmerGroup(userFarmerGroupSet);
					} 
				}
				// ------ 
				
				userHome.saveOrUpdate(userObj);
				
				
				if(eform.getUserType()!=null) {
					String[] userGroup = {"กอ.รมน.", "สหกรณ์", "กลุ่มเกษตรกร", "หอการค้า", "ผู้อนุมัติ"};
					int uGroup = Arrays.asList(userGroup).indexOf(eform.getUserType()[eform.getUserType().length-1]);
					userObj.setGroupUser(String.valueOf(uGroup+1));
					
					for(int i=0;i<eform.getUserType().length;i++) {
						UserType userType = new UserType();
						userType.setUserName(eform.getUserName());
						userType.setUserType(eform.getUserType()[i]);
						userType.setLastUpdateBy(eform.getUserName());
						userType.setLastUpdateDate(new Date());
						
						uTypehome.saveOrUpdate(userType);
					}
				}
			}
		
			
			if (!"Edit".equals(eform.getMsg())) {
				if (userObj.getUserName().equals(userLogin.getUserName())) {
					session.setAttribute("userLogin",userObj);
				}else{
					sf.getCurrentSession().getTransaction().commit();
					sf.getCurrentSession().beginTransaction();
					List<Menu> menuList = mhome.listAllMenu();
					for (int i = 0; i < menuList.size(); i++) {
						UserAuthorize auth = new UserAuthorize();
						Menu menu = menuList.get(i);
						auth.setUserName(userObj.getUserName().toLowerCase());
						auth.setAuthorize(menu.getDefaultVal());
			    		auth.setMenuId(menu.getMenuId());
			    		authHome.saveOrUpdate(auth);
					}
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("success");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		load(mapping, form, request, response);
		UserForm eform = (UserForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		UserHome home = new UserHome();
		
		List regionList = new ArrayList();
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		List userTypeList = new ArrayList();
		
		try {
			sf.getCurrentSession().beginTransaction();
			List<Prefix> prefixList = new ArrayList();
			PrefixHome prefHome = new PrefixHome();
			UserTypeHome uTypeHome = new UserTypeHome();
			prefixList = prefHome.findAll();
			session.setAttribute("prefixList", prefixList);
			
			User user = home.findByUser(eform.getUserName().toLowerCase());
			eform.loadFromBean(user);
			long level = userLogin.getLevel();

			//Region reg = new Region();
			Province prov = new Province();
			District dist = new District();
			SubDistrict sub = new SubDistrict();
			//--- ดึงภูมิภาค ออกมา --- //
			RegionHome regHome = new RegionHome();
			
			// closed by Yatphiroon.p on 10/01/2017
			/*
			if(user.getRegionNo()>0) {
				reg = regHome.searchByNo(user.getRegionNo());
			} else {
				reg.setRegionNo(0);
				reg.setRegionName("กรุณาเลือก");
				if(level==1)
					regionList = regHome.findAll();
				else {
					eform.setRegionNo(userLogin.getRegionNo());
					reg = regHome.searchByNo(userLogin.getRegionNo());
				}
			}
		     regionList.add(0,reg);
			*/
			
			
			regionList = regHome.findAll(); // added by yatphiroon.p on 10/1/2017
		
			
			//--- ดึงจังหวัด ออกมา --- //
			ProvinceHome provHome = new ProvinceHome();
			/*if(user.getProvinceNo()>0) {
				prov = provHome.searchByProvinceNo(user.getProvinceNo());
			} else { //Comment by music 21/09/2560 */
				prov.setRegionNo(0);
				prov.setProvinceNo(0);
				prov.setThaiName("กรุณาเลือก");
				if(level>2) {
					eform.setProvinceNo(userLogin.getProvinceNo());
					prov = provHome.searchByProvinceNo(userLogin.getProvinceNo());
				}
			//}
			provinceList.add(0,prov);
			//--- ดึงอำเภอ ออกมา --- //
			DistrictHome distHome = new DistrictHome();
			/*if(user.getDistrictNo()>0) {
				dist = distHome.findByDistrictNo(user.getDistrictNo());
			} else { //Comment by music 21/09/2560 */
				dist.setRegionNo(0);
				dist.setProvinceNo(0);
				dist.setDistrictNo(0);
				dist.setThaiName("กรุณาเลือก");
				if(level>3) {
					eform.setDistrictNo(userLogin.getDistrictNo());
					dist = distHome.findByDistrictNo(userLogin.getDistrictNo());
				}
			//}
			districtList.add(0,dist);
			//--- ดึงตำบล ออกมา --- //
			SubDistrictHome subHome = new SubDistrictHome();
			/*if(user.getSubDistrictNo()>0) {
				sub = subHome.findBySubDistrictNo(user.getSubDistrictNo());
			} else { //Comment by music 21/09/2560 */
				sub.setRegionNo(0);
				sub.setProvinceNo(0);
				sub.setDistrictNo(0);
				sub.setSubDistrictNo(0);
				sub.setThaiName("กรุณาเลือก");
				if(level>4) {
					eform.setSubDistrictNo(userLogin.getSubDistrictNo());
					sub = subHome.findBySubDistrictNo(userLogin.getSubDistrictNo());
				}
			//}
			subDistrictList.add(0,sub);
			
			//--- กลุ่มผู้ใช้งาน ---//
			String uTypeList = "";
			userTypeList = uTypeHome.findByUserName(user.getUserName());
			if(userTypeList !=null && userTypeList.size()>0){
				for(int i=0;i<userTypeList.size();i++) {
					uTypeList = ((UserType)userTypeList.get(i)).getUserType();
				}
			}
			
			// --- Edit by Music on 18/09/2017 ---
			String userRegion = "";
			String userProvince = "";
			String userFarmerGroup = "";
			
			StringBuffer result = new StringBuffer();
			for(UserRegion u : user.getUserRegion()){
				if(u!=null){
					result.append(","+String.valueOf(u.getRegionNo()));
				}
			} 
			if(result.length() > 0)
				userRegion = result.toString().substring(1);

			result = new StringBuffer();
			for(UserProvince u : user.getUserProvince()){
				if(u!=null){
					result.append(","+String.valueOf(u.getProvinceNo()));
				}
			} 
			if(result.length() > 0)
				userProvince = result.toString().substring(1);

			result = new StringBuffer();
			for(UserFarmerGroup u : user.getUserFarmerGroup()){
				if(u!=null){
					result.append(","+String.valueOf(u.getFarmerGroupId()));
				}
			} 
			if(result.length() > 0)
				userFarmerGroup = result.toString().substring(1);
			// --- End Edit by Music on 18/09/2017 ---
			
			// --- province : Added by Jane on 5/1/2016 ---
			/*String[] userRegion = new String[100];
			int cntReg = 0;
			for(UserRegion u : user.getUserRegion()){
				if(u!=null){
					userRegion[cntReg]= String.valueOf(u.getRegionNo());
					cntReg++;
				}
			}
			eform.setUserRegion(userRegion);
			// --- province : Added by Jane on 5/1/2016 ---
			String[] userProvince = new String[100];
			int cntProv = 0;
			for(UserProvince u : user.getUserProvince()){
				if(u!=null){
					userProvince[cntProv]= String.valueOf(u.getProvinceNo());
					cntProv++;
				}
			}
			eform.setUserProvince(userProvince);
			
			// ---- farmergroup  : Added by Jane on 5/1/2016 ---
			String[] usrFarmerGroup = new String[100];
			int cntGrp = 0;
			for(UserFarmerGroup u : user.getUserFarmerGroup()){
				usrFarmerGroup[cntGrp] = String.valueOf(u.getFarmerGroupId());
				cntGrp++;
			}
			eform.setUserFarmerGroup(usrFarmerGroup);*/
			
			session.setAttribute("regionList", regionList);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("userTypeList", uTypeList);
			
			session.setAttribute("userRegionList", userRegion);
			session.setAttribute("userProvinceList", userProvince);
			session.setAttribute("userFarmerGroupList", userFarmerGroup);
			// uTypeList
			
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("success");
	}

	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm eform = (UserForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserHome userHome = new UserHome();
		try {
			sf.getCurrentSession().beginTransaction();

			if (eform.getCurrentPassword() != null && !"".equals(eform.getCurrentPassword().trim())) {
				MessageDigest digestCurr = MessageDigest.getInstance("MD5");
				digestCurr.update(eform.getCurrentPassword().trim().getBytes("UTF-8"));
				byte raw[] = digestCurr.digest();
				String currentPassword = (new BASE64Encoder()).encode(raw);

				MessageDigest digestNew = MessageDigest.getInstance("MD5");
				digestNew.update(eform.getPassword().trim().getBytes("UTF-8"));
				byte raw1[] = digestNew.digest();
				String passwordNew = (new BASE64Encoder()).encode(raw1);

				// for userLogin
				if (userHome.authenUser(userLogin.getUserName(),currentPassword)) { // correct current password
					if (!currentPassword.equals(passwordNew)) { // correct new
						// password
						User user = userHome.authenticateUser(userLogin.getUserName().toLowerCase(), currentPassword);
						user.setPassword(passwordNew);
						user.setLastUpdateBy(userLogin.getUserName().toLowerCase());
						user.setLastUpdateDate(new Date());
						userHome.saveOrUpdate(user);
						sf.getCurrentSession().getTransaction().commit();
						eform.setMsg(Utility.get("PASSWORD_SUCCESS"));
						
						sf.getCurrentSession().beginTransaction();
						LoginHome loginHome = new LoginHome();
						Login login = new Login();
						login.setUserName(userLogin.getUserName().toLowerCase());
						login.setCountLogin(0);
						login.setLastLoginDate(new Date());
						loginHome.saveOrUpdate(login);
						sf.getCurrentSession().getTransaction().commit();
						sf.getCurrentSession().close();
					
					} else {
						eform.setMsg("กรุณากำหนดรหัสผ่านใหม่ให้ไม่เหมือนรหัสผ่านปัจจุบัน");//eform.setMsg(Utility.get("SAVE_DUPLICATE"));
					}
				} else {
					eform.setMsg(Utility.get("PASSWORD_INCORRECT"));//eform.setMsg("Incorrect");
				}
			}else {
				eform.setMsg(Utility.get("SAVE_INCORRECT"));//eform.setMsg("Incorrect");
			}
		} catch (Exception e) {
			eform.setMsg(e.getMessage());
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("changePassword");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm eform = (UserForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		List regionList = new ArrayList();
		List provinceList = new ArrayList();
		List districtList = new ArrayList();
		List subDistrictList = new ArrayList();
		List cooperativeList = new ArrayList();
		long districtNo = 0, subDistrictNo = 0, userLevel = 0;
		ArrayList<Long> regionNoList = new ArrayList<Long>();
		ArrayList<String> farmerGroupId = new ArrayList<String>();
		ArrayList<String> regionNo = new ArrayList<String>(), provinceNo = new ArrayList<String>(); //For multiselect by Music 21/09/2560
		try {
			sf.getCurrentSession().beginTransaction();
			PrefixHome 	prefHome = new PrefixHome();
			FarmerGroupHome home = new FarmerGroupHome();
			List prefixList = prefHome.findAll();

			userLevel = userLogin.getLevel();
			//farmerGroupId = userLogin.getFarmerGroupId();
			for (UserFarmerGroup u : userLogin.getUserFarmerGroup()) {
				if(u != null){
					farmerGroupId.add(String.valueOf(u.getFarmerGroupId()));
				}
			}
			if(userLevel>1) {
				//regionNo = userLogin.getRegionNo();
				//eform.setRegionNo(regionNo);
				//For multiselect by Music 21/09/2560
				if(userLogin.getUserRegion()!=null || userLogin.getUserRegion().size()>0){
					for (UserRegion u : userLogin.getUserRegion()) {
						if(u != null){
							regionNo.add(String.valueOf(u.getRegionNo()));
							regionNoList.add(u.getRegionNo());
						}
					}
					eform.setUserRegionNo(regionNo.toString());
				}
			} if(userLevel>2) {
				//provinceNo = userLogin.getProvinceNo();
				//eform.setProvinceNo(provinceNo);
				//For multiselect by Music 21/09/2560
				if(userLogin.getUserProvince()!=null || userLogin.getUserProvince().size()>0){
					for (UserProvince u : userLogin.getUserProvince()) {
						if(u != null){
							provinceNo.add(String.valueOf(u.getProvinceNo()));
						}
					}
					eform.setUserProvinceNo(provinceNo.toString());
				}
			} if(districtNo==0 && userLevel>3) {
				districtNo = userLogin.getDistrictNo();
				eform.setDistrictNo(subDistrictNo);
			} if(subDistrictNo==0 && userLevel>4) {
				subDistrictNo = userLogin.getSubDistrictNo();
				eform.setSubDistrictNo(subDistrictNo);
			}
			
			eform.setBranch(userLogin.getBranchCode());
			
			long level = userLogin.getLevel();
			Region reg = new Region();
			Province prov = new Province();
			District dist = new District();
			SubDistrict sub = new SubDistrict();
			//--- ดึงภูมิภาค ออกมา --- //
			RegionHome regHome = new RegionHome();
			if(regionNo.size() == 1) {
				eform.setUserRegionNo(regionNo.toString());
				reg = regHome.searchByNo(Long.parseLong(regionNo.get(0).toString()));
				regionList.add(0,reg);
			} else {
				//reg.setRegionNo(0);
				//reg.setRegionName("กรุณาเลือก");
				if(level<=1)
					regionList = regHome.findAll();
				else {
					eform.setUserRegionNo(regionNo.toString());
					regionList = regHome.searchByNoList(regionNo);
				}
			}
			//--- ดึงจังหวัด ออกมา --- //
			ProvinceHome provHome = new ProvinceHome();
			if(provinceNo.size() == 1) {
				eform.setUserProvinceNo(provinceNo.toString());
				prov = provHome.searchByProvinceNo(Long.parseLong(provinceNo.get(0).toString()));
				provinceList.add(0,prov);
			} else if(provinceNo.size() > 1) {
				//prov.setRegionNo(0);
				//prov.setProvinceNo(0);
				//prov.setThaiName("กรุณาเลือก");
				if(level>2) {
					eform.setUserProvinceNo(provinceNo.toString());
					provinceList = provHome.searchByProvinceNoList(provinceNo);
				} else {
					provinceList = provHome.retrieveByRegionNoList(regionNoList);
				}
			}
			//--- ดึงอำเภอ ออกมา --- //
			DistrictHome distHome = new DistrictHome();
			if(userLogin.getDistrictNo()>0) {
				dist = distHome.findByDistrictNo(userLogin.getDistrictNo());
			} else {
				dist.setRegionNo(0);
				dist.setProvinceNo(0);
				dist.setDistrictNo(0);
				dist.setThaiName("กรุณาเลือก");
				if(level>3) {
					eform.setDistrictNo(userLogin.getDistrictNo());
					dist = distHome.findByDistrictNo(userLogin.getDistrictNo());
				} else {
					districtList = distHome.findByProvinceNo(userLogin.getProvinceNo());
				}
			}
			districtList.add(0,dist);
			//--- ดึงตำบล ออกมา --- //
			SubDistrictHome subHome = new SubDistrictHome();
			if(userLogin.getSubDistrictNo()>0) {
				sub = subHome.findBySubDistrictNo(userLogin.getSubDistrictNo());
			} else {
				sub.setRegionNo(0);
				sub.setProvinceNo(0);
				sub.setDistrictNo(0);
				sub.setSubDistrictNo(0);
				sub.setThaiName("กรุณาเลือก");
				if(level>4) {
					eform.setSubDistrictNo(userLogin.getSubDistrictNo());
					sub = subHome.findBySubDistrictNo(userLogin.getSubDistrictNo());
				} else {
					subDistrictList = subHome.findByDistrictNo(userLogin.getDistrictNo());
				}
			}
			subDistrictList.add(0,sub);

			//--- ดึงสหกรณ์ ออกมา --- //
			FarmerGroup coop = new FarmerGroup();
			//coop.setFarmerGroupId(0);
			//coop.setFarmerGroupName("ทั้งหมด");
			cooperativeList = home.searchByAnauthorizeFilteredByCriteria("C", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId);
			//cooperativeList = home.searchByAnauthorize("C", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId);
			//cooperativeList.add(0, coop);
			
			eform.setLevel(userLogin.getLevel());
			
			
			/*
			///
			// --- region : Added by Jane on 5/1/2016 ---
			String[] userRegion = {};
			String[] userRegionName = {};
			int cnt = 0;
			for(UserRegion u : user.getUserRegion()){
				if(u!=null){
					
					userRegion[cnt]=u.getRegionNo() + "";
					Region region = regHome.searchByNo(u.getRegionNo());
					if(region != null)
						userRegionName[cnt] = region.getRegionName();
					
					cnt++;
				}
			}
			eform.setUserRegion(userRegion);
			eform.setUserRegionName(userRegionName);
			
			// --- province : Added by Jane on 5/1/2016 ---
			String[] userProvince = {};
			String[] userProvinceName = {};
			int cntProv = 0;
			for(UserProvince u : user.getUserProvince()){
				if(u!=null){
					userProvince[cntProv]=u.getProvinceNo() + "";
					Province province = provHome.searchByProvinceNo(u.getProvinceNo());
					if(province != null)
						userProvinceName[cntProv] = province.getThaiName();
					cntProv++;
				}
			}
			eform.setUserProvince(userProvince);
			eform.setUserProvinceName(userProvinceName);
			// ---- farmergroup ---
			
			FarmerGroupHome gHome = new FarmerGroupHome();
			String[] usrFarmerGroup = {};
			String[] usrFarmerGroupName = {};
			int cntGrp = 0;
			for(UserFarmerGroup u : user.getUserFarmerGroup()){
				usrFarmerGroup[cntGrp] = u.getFarmerGroupId() + "";
				 FarmerGroup group =  gHome.findByFarmerGroupId(u.getFarmerGroupId());
				 if(group != null)
					 usrFarmerGroupName[cntGrp] = group.getFarmerGroupName();
				cntGrp++;
			}
			eform.setUserFarmerGroup(usrFarmerGroup);
			eform.setUserFarmerGroup(usrFarmerGroupName);
		*/
			
			///
			
			
			session.setAttribute("allPrefix", prefixList);
			session.setAttribute("regionList", regionList);
			session.setAttribute("provinceList", provinceList);
			session.setAttribute("districtList", districtList);
			session.setAttribute("subDistrictList", subDistrictList);
			session.setAttribute("cooperativeGroupList", cooperativeList);
			session.setAttribute("userTypeList", new String());
			
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("success");
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
	public ActionForward getUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			UserForm eform = (UserForm)form;
			try{
				sf.getCurrentSession().beginTransaction();
				UserHome home = new UserHome();
				User user = home.findByUser(eform.getUserName());
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{"password", "branchCode", "name", "branchName", "firstName", "lastName", "email", "groupUser", "active", "createDate", "createBy", "lastUpdateDate", "lastUpdateBy", "userAuthorize", "abbrPrefix", "checkBox", "linkImageEdit", "linkImageAccess", "status", "linkImageReset"});
			    String result = JSONArray.fromObject(user, jsonConfig).toString();
			    result = result.trim().replaceAll("&nbsp;", " ");
			    wt.print(result);
			    wt.flush();
			    wt.close();
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}finally{
				sf.getCurrentSession().close();
			}
			return null;
	}

	//Ajax Method
	public ActionForward getRegionInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		UserForm eform = (UserForm)form;
		try{
			HttpSession session = request.getSession();
			sf.getCurrentSession().beginTransaction();
			RegionHome home = new RegionHome();		
			List regionList = new ArrayList();			
			
			User userLogin = (User) session.getAttribute("userLogin");
			if(userLogin.getLevel() > 1) {
				ArrayList<String> regionNo = new ArrayList<String>();
				if(userLogin.getUserRegion()!=null || userLogin.getUserRegion().size()>0){
					for (UserRegion u : userLogin.getUserRegion()) {
						if(u != null){
							regionNo.add(String.valueOf(u.getRegionNo()));
						}
					}
				}
				regionList = home.searchByNoList(regionNo);
			}else
				regionList = home.findAll();
			
			session.setAttribute("regionList", regionList);
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();  
		    jsonConfig.setExcludes(new String[]{"checkbox","linkImageDel","linkImageEdit","province","lastUpdateDate","lastUpdateBy"});   

		    String result = JSONArray.fromObject(regionList, jsonConfig).toString();
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
	public ActionForward getProvinceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		UserForm eform = (UserForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List proList = new ArrayList();			
			
			//long regionNo = 0;
			//if(!"".equals(eform.getRegionNo()))
			//	regionNo = eform.getRegionNo();

			User userLogin = (User) session.getAttribute("userLogin");
			if(userLogin.getLevel() > 2) {
				ArrayList<String> provinceNo = new ArrayList<String>();
				if(userLogin.getUserProvince()!=null || userLogin.getUserProvince().size()>0){
					for (UserProvince u : userLogin.getUserProvince()) {
						if(u != null){
							provinceNo.add(String.valueOf(u.getProvinceNo()));
						}
					}
				}
				proList = home.selectByProvinceNoList(provinceNo);
			}else{
				List<String> regionList = new ArrayList<String>();
				String regionNo = String.valueOf(eform.getUserRegionNo());
				if (!"".equals(regionNo)) {
					if (regionNo.indexOf(",") > -1) {
						String[] items = regionNo.split(",");
						for (String item : items) {
							regionList.add(item);
						}
					} else {
						regionList.add(regionNo);
					}
					
				}
				proList = home.filterByRegionNoDistinctByList(regionList);
				// proList = home.filterByRegionNoDistinct(regionNo);
			}
			
			session.setAttribute("provFilteredByRegionList", proList);
				
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
		UserForm eform = (UserForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
			
			
			
			/*
			long provinceNo = 0;
			if(!"".equals(eform.getProvinceNo()))
				provinceNo = eform.getProvinceNo();
				dtrcList = home.filterByProvinceNo(provinceNo);
		    */
				
			HttpSession session = request.getSession();
			User userLogin = (User) session.getAttribute("userLogin");
			if(userLogin.getLevel() > 3) {
				dtrcList = home.selectByDistrictNo(userLogin.getDistrictNo());
			}else{
				// --- start : added on 30/12/2016 ---
				List<String> provinceList = new ArrayList<String>();
				String provinceNo = String.valueOf(eform.getUserProvinceNo());
				if (!"".equals(provinceNo)) {
					if (provinceNo.indexOf(",") > -1) {
						String[] items = provinceNo.split(",");
						for (String item : items) {
							provinceList.add(item);
						}
					} else {
						provinceList.add(provinceNo);
					}
				}
				dtrcList = home.filterByProvinceNoList(provinceList);
				// --- finish : added on 30/12/2016 ---
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
		UserForm eform = (UserForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List subList = new ArrayList();	
			long districtNo = 0;
			//long provinceNo = 0;
			
			
			List<String> provinceList = new ArrayList<String>();
			String provinceNo = String.valueOf(eform.getUserProvinceNo());
			if (!"".equals(provinceNo)) {
				if (provinceNo.indexOf(",") > -1) {
					String[] items = provinceNo.split(",");
					for (String item : items) {
						provinceList.add(item);
					}
				} else {
					provinceList.add(provinceNo);
				}
			}
			
			
			if(!"".equals(eform.getDistrictNo()) && !"".equals(provinceNo)) {
				districtNo = eform.getDistrictNo();
				//provinceNo = eform.getProvinceNo();
				subList = home.filterByProvinceAndDistrictNo(districtNo, provinceList);
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
	
	//Ajax Method
	public ActionForward getCooperativeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		UserForm eform = (UserForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			List subList = new ArrayList();
			long districtNo = 0, subDistrictNo = 0;//, farmerGroupId = 0;//regionNo = 0, provinceNo = 0, 
			//farmerGroupId = userLogin.getFarmerGroupId();
			List<String> farmerGroupList = new ArrayList<String>();
			String farmerGroupId = String.valueOf(eform.getUserFarmerGroupId());
			if (!"".equals(farmerGroupId)) {
				if (farmerGroupId.indexOf(",") > -1) {
					String[] items = farmerGroupId.split(",");
					for (String item : items) {
						farmerGroupList.add(item);
					}
				} else {
					if ("null".equals(farmerGroupId))
						farmerGroupId = "0";
					farmerGroupList.add(farmerGroupId);
				}
				
			}
			
			List<String> regionList = new ArrayList<String>();
			String regionNo = String.valueOf(eform.getUserRegionNo());
			if (!"".equals(regionNo)) {
				if (regionNo.indexOf(",") > -1) {
					String[] items = regionNo.split(",");
					for (String item : items) {
						regionList.add(item);
					}
				} else {
					regionList.add(regionNo);
				}
				
			}
			
			List<String> provinceList = new ArrayList<String>();
			String provinceNo = String.valueOf(eform.getUserProvinceNo());
			if (!"".equals(provinceNo)) {
				if (provinceNo.indexOf(",") > -1) {
					String[] items = provinceNo.split(",");
					for (String item : items) {
						provinceList.add(item);
					}
				} else {
					provinceList.add(provinceNo);
				}
			}
			
			if(!"".equals(eform.getDistrictNo()))
				districtNo = eform.getDistrictNo();
			if(!"".equals(eform.getSubDistrictNo()))
				subDistrictNo = eform.getSubDistrictNo();
			
			if(userLogin.getLevel() > 1) {
				regionList.clear();
				for (UserRegion u : userLogin.getUserRegion()) {
					regionList.add(String.valueOf(u.getRegionNo()));
					}
			}
			if(userLogin.getLevel() > 2) {
				provinceList.clear();
				for (UserProvince u : userLogin.getUserProvince()) {
					provinceList.add(String.valueOf(u.getProvinceNo()));
				}
			}
			if(userLogin.getLevel() > 3)
				districtNo = userLogin.getDistrictNo();
			if(userLogin.getLevel() > 4)
				subDistrictNo = userLogin.getSubDistrictNo();
			if(userLogin.getUserFarmerGroup() != null && userLogin.getUserFarmerGroup().size() > 0) {
				farmerGroupList.clear();
				for (UserFarmerGroup u : userLogin.getUserFarmerGroup()) {
					farmerGroupList.add(String.valueOf(u.getFarmerGroupId()));
				}
			}

			//--- ดึงสหกรณ์ ออกมา --- //
			//subList = home.searchByAnauthorize("C", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId);
			subList = home.searchByAnauthorizeFilteredByCriteria("C", regionList, provinceList, districtNo, subDistrictNo, farmerGroupList);
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"farmerGroupFarmer","farmerGroupAddress","farmerGroupChild","farmerGroupTeam","provinceName","districtName","subDistrictName","joinCooperative","countFarmer"});   

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
