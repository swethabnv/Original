package com.wsnweb.action;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Menu;
import com.wsndata.data.User;
import com.wsndata.data.UserAuthorize;
import com.wsndata.dbaccess.MenuHome;
import com.wsndata.dbaccess.UserAuthorizeHome;
import com.wsndata.dbaccess.UserHome;
import com.wsnweb.form.AccessControlListForm;
import com.wsnweb.util.Utility;

public class AccessControlListAction extends Action {

	private static final Logger log = Logger.getLogger(AccessControlListAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
		 AccessControlListForm eform = (AccessControlListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Save".equals(eform.getCmd())){
				 return save(mapping,form,request,response);
			 } else{
				 return search(mapping,form,request,response);
			 }
		 }
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		AccessControlListForm eform = (AccessControlListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		MenuHome mhome = new MenuHome();
		UserAuthorizeHome authHome = new UserAuthorizeHome();
		UserHome userHome = new UserHome();
		try{	
			String msg = Utility.get("SAVE_ACCESSCONTROL_FAIL");//msg = "การบันทึกสิทธิ์ล้มเหลว";
			sf.getCurrentSession().beginTransaction();
		
		    User user = userHome.findByUser(eform.getUserName());
			List<UserAuthorize> listAuth = authHome.listAuthorize(eform.getUserName());
			if(listAuth!= null){
				for(int i = 0; i < listAuth.size(); i++){
					UserAuthorize uAuth = (UserAuthorize)listAuth.get(i);
					authHome.delete(uAuth);
				}
				sf.getCurrentSession().getTransaction().commit();
				sf.getCurrentSession().beginTransaction();
			}
			
			String authArr = eform.getAuthArray().substring(1);
			String[] menuItem = authArr.split("\\|");
		    if(menuItem !=null && menuItem.length>0){
		    	for(int i=1; i<menuItem.length; i++){
		    		UserAuthorize auth = new UserAuthorize();
		    		String[] value = menuItem[i].split("\\,");
		    		String menuId = value[0];
		    		String authorize = value[1];
		    		Menu menu = mhome.findByMenuId(menuId);
		    		if(menu != null)
		    			auth.setMenu(menu);
		    		auth.setUser(user);
		    		auth.setAuthorize(authorize);
		    		auth.setMenuId(menu.getMenuId());
		    		auth.setUserName(user.getUserName());
		    		authHome.saveOrUpdate(auth);
		    	}
			}
			sf.getCurrentSession().getTransaction().commit();
			msg = Utility.get("SAVE_ACCESSCONTROL_SUCCESS");//msg = "การบันทึกสิทธิ์สมบูรณ์";
		    eform.setMsg(msg);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			sf.getCurrentSession().getTransaction().rollback();
		}finally{
			sf.getCurrentSession().close();
		}
		return search(mapping,form,request,response);
	}
	
	
	
	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		AccessControlListForm eform = (AccessControlListForm)form;
		List<Menu> newMenuList = new ArrayList<Menu>();
		String userName = "", firstName = "", lastName ="";
		HashMap<String, String> authMap = new HashMap<String, String>();
		HashMap<String, String> menuMap = new HashMap<String, String>();
		try{
			userName = eform.getUserName() == null ? "" : eform.getUserName();
			firstName = eform.getFirstName() == null ? "" : eform.getFirstName();
			lastName = eform.getLastName() == null ? "" : eform.getLastName();
			MenuHome home = new MenuHome();
			UserAuthorizeHome uHome = new UserAuthorizeHome();
			
			sf.getCurrentSession().beginTransaction();		
			// ---
			List<UserAuthorize> uAuthList =  uHome.listAuthorize(userName);
			if(uAuthList != null){
				//Update permission
				if (userName.equals(userLogin.getUserName())) {
					session.setAttribute("authorList", uAuthList);
				}
			    // แล้วเก็บ hashtable ของ uAuthList ว่า มี menuId อะไรบ้างแล้วมาเทียบกับ menuList
			    for(int i = 0; i< uAuthList.size(); i++){
					UserAuthorize uAuth = (UserAuthorize)uAuthList.get(i);
					String key = uAuth.getMenuId();
					String value = uAuth.getAuthorize();
					authMap.put(key, value);
			    }
			}
			// ---
		    List<Menu> menuList = home.listAllMenu();
		    if(menuList != null){
		    	String authorize ="";
		    	
				for(int i = 0; i< menuList.size(); i++){
					Menu menu = (Menu)menuList.get(i);
					String key = menu.getMenuId();
					String value = menu.getMtext();
					menu.setMenuId(key);
					menu.setMtext(value); 
					if(!authMap.containsKey(key)){ 
						authMap.put(key, "000");
						authorize = "000";
						menuMap.put(key, value + "," + authorize); 
					}else{
					    authorize = (String) authMap.get(key);
					    menuMap.put(key, value + "," + authorize);
					}
					menu.setAuthorizeMap(menuMap);
					newMenuList.add(menu);
				}
		    }
			eform.setUserName(userName);
			eform.setFirstName(firstName);
			eform.setLastName(lastName);
			session.setAttribute("menuList", newMenuList);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex,ex);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("menuList");
	}
}
