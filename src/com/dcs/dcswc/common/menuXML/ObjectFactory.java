package com.dcs.dcswc.common.menuXML;

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
	
	private final static QName _Menu_QNAME = new QName("", "Menu");
    private final static QName _MenuList_QNAME = new QName("", "MenuList");

    public ObjectFactory() {
    }
    
    public MenuList createMenuList() {
        return new MenuList();
    }
    
    public Menu createMenu() {
        return new Menu();
    }
    
}
