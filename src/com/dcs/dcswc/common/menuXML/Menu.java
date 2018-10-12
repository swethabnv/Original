package com.dcs.dcswc.common.menuXML;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.dcs.dcswc.common.IMenu;
import com.dcs.dcswc.common.ITree;

public class Menu implements IMenu {
	private ITree parent;	
	private String menuAction;
	private String menuID;
	private String menuText;
	private String menuIcon;
	private String width;
	private boolean visible = true;
	private List child;
	
	public Menu() {
	}
	
	public Menu(String menuID, String menuText, String menuAction, String menuIcon, String width, boolean visible) {
		this.menuID = menuID;
		this.menuText = menuText;
		this.menuAction = menuAction;
		this.menuIcon = menuIcon;
		this.width = width;
		this.visible = visible;
	}
		
	@XmlElement(name="action")
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	
	@XmlAttribute(name="id")
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
	
	@XmlElement(name="text")
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	
	@XmlAttribute(name="width")
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
	@XmlElement(name="icon")
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	
	@XmlAttribute(name="width")
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@XmlElement(name="menu", type=Menu.class)
	public List getChild() {
		return child;
	}
	public void setChild(List child) {
		this.child = child;
	}
	
	@XmlTransient
	public ITree getParent() {
		return parent;
	}
	public void setParent(ITree parent) {
		this.parent = parent;
	}
}
