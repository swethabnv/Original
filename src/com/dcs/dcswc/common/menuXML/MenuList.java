package com.dcs.dcswc.common.menuXML;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="menulist")
public class MenuList {
	private List<Menu> menu;

	@XmlElement(name="menu")
	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
}
