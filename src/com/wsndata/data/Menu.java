package com.wsndata.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 304963055821935858L;
    private String menuId;
    private String pmenuId;
    private int seq;
    private String mtext;
    private String maction;
    private boolean visible;
    private Menu parentMenu;
    private String defaultVal;
	private List<Menu> childMenu;
	//private Set<UserAuthorize> userAuthorize;
	
	// --- for menuList in AccessControl --- //
	private String authorize;
	private String userName;
	private HashMap<String, String> authorizeMap;
	
	
//	public Set<UserAuthorize> getUserAuthorize() {
//		return userAuthorize;
//	}
//	public void setUserAuthorize(Set<UserAuthorize> userAuthorize) {
//		this.userAuthorize = userAuthorize;
//	}
	public HashMap<String, String> getAuthorizeMap() {
		return authorizeMap;
	}
	public void setAuthorizeMap(HashMap<String, String> authorizeMap) {
		this.authorizeMap = authorizeMap;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAuthorize() {
		return authorize;
	}
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getPmenuId() {
		return pmenuId;
	}
	public void setPmenuId(String pmenuId) {
		this.pmenuId = pmenuId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMtext() {
		return mtext;
	}
	public void setMtext(String mtext) {
		this.mtext = mtext;
	}
	public String getMaction() {
		return maction;
	}
	public void setMaction(String maction) {
		this.maction = maction;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<Menu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	
    
}
