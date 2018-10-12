package com.dcs.dcswc.common;

import java.util.List;

public class AMenu implements IMenu{
	private List child;
	private String menuIcon;
	private String menuAction;
	private String menuID;
	private String menuText;
	private String width;
	private ITree parent;
	private boolean visible;
	
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public List getChild() {
		return child;
	}
	public void setChild(List child) {
		this.child = child;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public ITree getParent() {
		return parent;
	}
	public void setParent(ITree parent) {
		this.parent = parent;
	}
}
