package com.dcs.strut.exten.roleXML;

import java.io.File;
import java.net.URL;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import com.dcs.util.ConfigurePlugIn;
import com.dcs.util.IConfigureLoader;

public class UserRoleLoader implements IConfigureLoader {
	private static final Logger log = Logger.getLogger(UserRoleLoader.class);
	public static final String CONTEXT_KEY = "USERROLE_CONTEXT"; 
	
	public void load(ActionServlet servlet, String file) throws NamingException {
		log.info("*** Start load application user role XML ***");
		try {
			URL cfgfile = ConfigurePlugIn.class.getResource(file);
			log.info("Load configure file = "+cfgfile.getFile());
			JAXBContext context = JAXBContext.newInstance("com.dcs.strut.exten.roleXML");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			RoleMap roleMap = (RoleMap) unmarshaller.unmarshal(new File(cfgfile.getFile()));
			servlet.getServletContext().setAttribute(CONTEXT_KEY, roleMap.getUserRole());
		} catch(Exception ex) {
			log.error("Exception while load Configure.", ex);
		}
	}
}
