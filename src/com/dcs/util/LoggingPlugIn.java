package com.dcs.util;

import java.net.URL;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import org.apache.log4j.Logger;

import org.apache.log4j.PropertyConfigurator;

public class LoggingPlugIn implements PlugIn {
	private static final Logger _log = Logger.getLogger(LoggingPlugIn.class);

	private String _configFilePath = "/logger.cfg";

	public void destroy() {		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1)	throws ServletException {
		URL configFileURL = null;
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Logger from " + _configFilePath + "...");
			}
	    	configFileURL = LoggingPlugIn.class.getResource(_configFilePath);
	        PropertyConfigurator.configure(configFileURL);
		} catch (Throwable t) {
			_log.error("Exception while initializing Logger.");
			_log.error("Rethrowing exception...", t);

			throw (new ServletException(t));
		}

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
