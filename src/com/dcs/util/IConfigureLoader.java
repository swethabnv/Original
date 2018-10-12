package com.dcs.util;

import javax.naming.NamingException;

import org.apache.struts.action.ActionServlet;

public interface IConfigureLoader {
	public void load(ActionServlet servlet, String file) throws NamingException;
}
