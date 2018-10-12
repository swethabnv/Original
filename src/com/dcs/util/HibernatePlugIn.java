package com.dcs.util;

import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.dcs.hibernate.HibernateHome;

public class HibernatePlugIn implements PlugIn {

	public static final String SESSION_FACTORY_KEY = SessionFactory.class.getName();
	public static final String SESSION_FACTORY_NAME = "SessionFactoryName";
	private static final Logger _log = Logger.getLogger(HibernatePlugIn.class);
	private static boolean _storedInServletContext = true;
	private static String _configFilePath = "/hibernate.cfg.xml";
	private ActionServlet _servlet = null;
	private SessionFactory _factory = null;

	public void destroy() {
		_servlet = null;

		try {
			_log.debug("Destroying SessionFactory...");
			_factory.close();
			_log.debug("SessionFactory destroyed...");
		} catch (Exception e) {
			_log.error("Unable to destroy SessionFactory...(exception ignored)", e);
		}
	}

	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		_servlet = servlet;
		initHibernate();
	}

	private void initHibernate() throws ServletException {
		Configuration configuration = null;
		URL configFileURL = null;
		ServletContext context = null;

		try {
			Locale.setDefault(Locale.US);
			configFileURL = HibernatePlugIn.class.getResource(_configFilePath);
			context = _servlet.getServletContext();
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Hibernate from " + _configFilePath + "...");
			}
			configuration = (new Configuration()).configure(configFileURL);
			
			context.setAttribute(SESSION_FACTORY_NAME, configuration.getProperty("hibernate.session_factory_name"));
			HibernateHome.SESSION_FACTORY_NAME = configuration.getProperty("hibernate.session_factory_name");

			_factory = configuration.buildSessionFactory();

			if (_storedInServletContext) {
				_log.debug("Storing SessionFactory in ServletContext...");
				context.setAttribute(SESSION_FACTORY_KEY, _factory);
			}

		} catch (Throwable t) {
			_log.error("Exception while initializing Hibernate.");
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


	public void setStoredInServletContext(String storedInServletContext) {
		if ((storedInServletContext == null) || (storedInServletContext.trim().length() == 0)) {
			storedInServletContext = "false";
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Setting 'storedInServletContext' to '" + storedInServletContext + "'...");
		}
		_storedInServletContext = new Boolean(storedInServletContext).booleanValue();
	}

}
