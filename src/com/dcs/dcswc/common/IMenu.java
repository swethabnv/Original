package com.dcs.dcswc.common;

public interface IMenu extends ITree {
	public String getMenuIcon();
	public void setMenuIcon(String menuIcon);
	public String getMenuID();
	public void setMenuID(String menuID);
	public String getMenuAction();
	public void setMenuAction(String menuAction);
	public String getMenuText();
	public void setMenuText(String menuText);
	public String getWidth();
	public void setWidth(String width);
	public boolean getVisible();
	public void setVisible(boolean visible);
}
