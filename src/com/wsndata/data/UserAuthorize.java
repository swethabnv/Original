package com.wsndata.data;
import java.io.Serializable;

public class UserAuthorize implements Serializable {


	private static final long serialVersionUID = -1787692733422870557L;
	private String userName;
	private String menuId;
	private String authorize;
	private User user;
	private Menu menu;
	
	private String fName;
	
	
	
	public String getFName() {
		return fName;
	}
	public void setFName(String name) {
		fName = name;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getAuthorize() {
		return authorize;
	}
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	
	
}
