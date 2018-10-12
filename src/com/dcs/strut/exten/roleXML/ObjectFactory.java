package com.dcs.strut.exten.roleXML;

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
	
	private final static QName _RoleMap_QNAME = new QName("", "RoleMap");
    private final static QName _UserRight_QNAME = new QName("", "UserRight");
    private final static QName _UserRole_QNAME = new QName("", "UserRole");

    public ObjectFactory() {
    }
    
    public RoleMap createRoleMap() {
        return new RoleMap();
    }
    
    public UserRight createUserRight() {
        return new UserRight();
    }
    
    public UserRole createUserRole() {
        return new UserRole();
    }
    
}
