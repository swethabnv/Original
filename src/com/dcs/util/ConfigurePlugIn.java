package com.dcs.util;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import org.apache.log4j.Logger;


public class ConfigurePlugIn implements PlugIn {
	private static final Logger _log = Logger.getLogger(ConfigurePlugIn.class);

	private String _loaderClass = "";
	private String _configFilePath = "";

	public void destroy() {		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1)	throws ServletException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Configure from " + _loaderClass + "...");
			}
			IConfigureLoader loader = (IConfigureLoader)Class.forName(_loaderClass).newInstance();
			loader.load(arg0, _configFilePath);
			
		} catch (Throwable t) {
			_log.error("Exception while initializing Configure.");
			_log.error("Rethrowing exception...", t);

			throw (new ServletException(t));
		}		
	}	
	
	public void setLoaderClass(String loaderClass) {
		if ((loaderClass == null) || (loaderClass.trim().length() == 0)) {
			throw new IllegalArgumentException("loaderClass cannot be blank or null.");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Setting 'loaderClass' to '" + loaderClass + "'...");
		}

		_loaderClass = loaderClass;
	}
	
	public void setConfigFilePath(String configFilePath) {
		if ((configFilePath == null) || (configFilePath.trim().length() == 0)) {
			throw new IllegalArgumentException("configFilePath cannot be blank or null.");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Setting 'configFilePath' to '" + configFilePath + "'...");
		}

		_configFilePath = configFilePath;
	}
}
