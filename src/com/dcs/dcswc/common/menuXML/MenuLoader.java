package com.dcs.dcswc.common.menuXML;

import java.io.File;
import java.net.URL;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import com.dcs.util.ConfigurePlugIn;
import com.dcs.util.IConfigureLoader;

public class MenuLoader implements IConfigureLoader {
	private static final Logger log = Logger.getLogger(MenuLoader.class);
	public static final String CONTEXT_KEY = "MENU_CONTEXT"; 
	
	public void load(ActionServlet servlet, String file) throws NamingException {
		log.info("*** Start load application menu XML ***");
		try {
			URL cfgfile = ConfigurePlugIn.class.getResource(file);
			log.info("Load configure file = "+cfgfile.getFile());
			JAXBContext context = JAXBContext.newInstance("com.dcs.dcswc.common.menuXML");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MenuList menuList = (MenuList) unmarshaller.unmarshal(new File(cfgfile.getFile()));
			servlet.getServletContext().setAttribute(CONTEXT_KEY, menuList.getMenu());
		} catch(Exception ex) {
			log.error("Exception while load Configure.", ex);
		}
	}
}
