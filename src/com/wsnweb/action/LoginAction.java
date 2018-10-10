package com.wsnweb.action;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import sun.misc.BASE64Encoder;

import com.dcs.hibernate.HibernateHome;
import com.dcs.util.DCSCompare;
import com.wsnweb.form.LoginForm;
import com.wsnweb.util.Utility;
import com.wsndata.data.Branch;
import com.wsndata.data.Login;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.LoginHome;
import com.wsndata.dbaccess.UserAuthorizeHome;
import com.wsndata.dbaccess.UserHome;
public class LoginAction extends Action {

	private static final Logger log = Logger.getLogger(LoginAction.class);
	private final int TIME_SHOW_CAPCHA = Integer.parseInt(Utility.get("TIME_SHOW_CAPCHA"), 10);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		LoginForm eform = (LoginForm)form;
		if("Login".equals(eform.getCmd()))
		 	return logOn(mapping, form, request, response);
		else if("Logout".equals(eform.getCmd()))
			return logOut(mapping, form, request, response);
		else
			return mapping.findForward("logon");
	 }
	
	
	// เมื่อคลิก login
	public ActionForward logOn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		LoginForm eform = (LoginForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		
		HttpSession session = request.getSession();
		String verificationCode = (String)session.getAttribute("verification.code");
		
		UserHome userHome = new UserHome();
		LoginHome loginHome = new LoginHome();
		User userLogin = null;
		Login login = null;
		String page = ""; 
		try{	
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(eform.getPassword().getBytes("UTF-8")); 
			byte raw[] = digest.digest(); 
		    String password = (new BASE64Encoder()).encode(raw); 
			
			sf.getCurrentSession().beginTransaction();
			eform.setUserName(eform.getUserName().toLowerCase());
			login = loginHome.loggingOn(eform.getUserName());
			userLogin= userHome.authenticateUser(eform.getUserName().toLowerCase(), password);  //
			
			if(userLogin != null) 
			{
				 // login เข้าถูกต้องทั้ง userName และ password
				if(verificationCode !=null && !"".equals(verificationCode))
				{
					if(verificationCode.equals(eform.getVerificationCode()))
					{
						// verify code ถูกต้อง
						if(login != null) {
							// เคย login เข้ามาก่อนแล้ว
							page = "success"; 
							UserAuthorizeHome mhome = new UserAuthorizeHome();
							List authorList = mhome.listAuthorize(userLogin.getUserName());
							if (authorList == null || authorList.size()<=0 ) {
								eform.setMsg("คุณไม่มีสิทธิ์ ในการเข้าใช้");
								request.setAttribute("LoginForm", eform);
								page = "logon";
							}else{
								session.setAttribute("authorList", authorList);
								// Branch by User
								BranchHome branchHome = new BranchHome();
								List branchList = new ArrayList();
								List branchTmp = new ArrayList();
								//branchTmp = branchHome.findByPBranch(Long.parseLong(userLogin.getBranchCode()));
								//branchList.add(branchHome.findByBranchCode(Long.parseLong(userLogin.getBranchCode())));
								//branchList.addAll(getPBranch(branchTmp,Long.parseLong(userLogin.getBranchCode())));

								Collections.sort(branchList, new DCSCompare("provinceNo",true));
								//Branch branch=new Branch();
								//branch.setBranchCode(0);
								//branch.setSeq(0); // first row
								//branch.setBranchName("กรุณาเลือก");
								//branchList.add(0,branch);
								session.setAttribute("userBranchList", branchList);
							}
							login.setUserName(eform.getUserName().toLowerCase());
							login.setCountLogin(0);
							login.setLastLoginDate(new Date());
							loginHome.saveOrUpdate(login);
							sf.getCurrentSession().getTransaction().commit();
							eform.setMsg("Set Count User's login Successful.");
						} else {
							// ยังไม่เคย login มาก่อน
							eform.setMsg("Go to change password");
							page = "changePassword"; 
						}
						
						session.setAttribute("userLogin", userLogin);
						session.removeAttribute("verification.code");
						request.setAttribute("LoginForm", eform); 
						return mapping.findForward(page);	 //for main page		
					}else{
						// verify code ไม่ถูกต้อง
						if(login !=null){
							login.setCountLogin(login.getCountLogin()+1);
						}else{
							login = new Login();
							login.setUserName(eform.getUserName().toLowerCase());
							login.setCountLogin(login.getCountLogin()+1); // เดิมเป็น 1
						}		
						loginHome.saveOrUpdate(login);
						sf.getCurrentSession().getTransaction().commit();
						
						eform.setMsg("คุณใส่รหัส  Verification ไม่ถูกต้อง");
						eform.setShowCapcha("1");
						
						session.removeAttribute("verification.code");
						request.setAttribute("LoginForm", eform); 
						return mapping.findForward("logon");						
					}
				}else{
					// ครั้งแรกที่เข้ามา
					if(login == null || login.getLastLoginDate()==null){
						// ยังไม่เคย login มาก่อน
						eform.setMsg("Go to change password");
						page = "changePassword"; 
					}else {
						// เคย login เข้ามาก่อนแล้ว
						page = "success"; 
						UserAuthorizeHome mhome = new UserAuthorizeHome();
						List authorList = mhome.listAuthorize(userLogin.getUserName());
						if (authorList == null || authorList.size()<=0 ) {
							eform.setMsg("คุณไม่มีสิทธิ์ ในการเข้าใช้");
							request.setAttribute("LoginForm", eform);
							page = "logon";
						}else{
							session.setAttribute("authorList", authorList);
							
							// Branch by User
							BranchHome branchHome = new BranchHome();
							List branchList = new ArrayList();
							List branchTmp = new ArrayList();
							//branchTmp = branchHome.findByPBranch(Long.parseLong(userLogin.getBranchCode()));
							//branchList.add(branchHome.findByBranchCode(Long.parseLong(userLogin.getBranchCode())));
							//branchList.addAll(getPBranch(branchTmp,Long.parseLong(userLogin.getBranchCode())));

							Collections.sort(branchList, new DCSCompare("provinceNo",true));
							//Branch branch=new Branch();
							//branch.setBranchCode(0);
							//branch.setSeq(0); // first row
							//branch.setBranchName("กรุณาเลือก");
							//branchList.add(0,branch);
							session.setAttribute("userBranchList", branchList);
						}
						login.setLastLoginDate(new Date());
						login.setCountLogin(0);
						login.setUserName(eform.getUserName().toLowerCase());
						loginHome.saveOrUpdate(login);
						sf.getCurrentSession().getTransaction().commit();
						eform.setMsg("Set Count User's login Successful.");
					} 
					session.setAttribute("userLogin", userLogin);
					session.removeAttribute("verification.code");
					request.setAttribute("LoginForm", eform); 
					return mapping.findForward(page);	
				}	
			} else {
				/*Login logObj = loginHome.loggingOutOfDate(eform.getUserName());
				if(logObj!=null) {
					logObj.setCountLogin(0);
					logObj.setLastLoginDate(new Date());
					loginHome.saveOrUpdate(login);
					sf.getCurrentSession().getTransaction().commit();
					sf.getCurrentSession().beginTransaction();
				}*/
				// ---  login ไม่ผ่าน  Set Capcha เป็น 1 ---
				userLogin = userHome.findByUser(eform.getUserName());
				if(userLogin != null){
					if(userLogin.isActive()) {
						if(login !=null){
							login.setCountLogin(login.getCountLogin()+1);
						}else{
							login = new Login();
							login.setUserName(eform.getUserName());
							login.setCountLogin(1); 
						}		
						
						sf.getCurrentSession().flush();
						loginHome.saveOrUpdate(login); // update ลง table Login เก็บเป็น history ว่า login มากี่ครั้งแล้ว
						sf.getCurrentSession().getTransaction().commit();				
						if(login !=null && login.getCountLogin() >= TIME_SHOW_CAPCHA){
							eform.setShowCapcha("1");
						}
						eform.setMsg("ล็อกอินไม่สำเร็จ กรุณาล็อกอินใหม่อีกครั้ง");
					} else {
						eform.setMsg("ไม่มีชื่อผู้ใช้งานนี้อยู่ในระบบ  กรุณาล็อกอินใหม่อีกครั้ง");
					}
				}else{
					eform.setMsg("ไม่มีชื่อผู้ใช้งานนี้อยู่ในระบบ  กรุณาล็อกอินใหม่อีกครั้ง");
				}
				request.setAttribute("LoginForm", eform); 
				page = "logon";   // stay on login page		
			}
			session.setAttribute("userLogin", userLogin);
		}catch(Exception e){
	    	e.printStackTrace();
	    	log.error(e,e);
	    }finally{
	    	sf.getCurrentSession().close();
	    }
    	return mapping.findForward(page);
	}
	
	public ActionForward logOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();			
		if(session !=null){
			session.removeAttribute("userLogin");
			session.invalidate();
		}
		return mapping.findForward("logon");
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
			//e.printStackTrace();
			log.error(e,e);
		}
		return branchList;
	}
}
