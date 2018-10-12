package com.dcs.hibernate;

import javax.naming.InitialContext;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

public class HibernateHome {
	private static final Logger log = Logger.getLogger(HibernateHome.class);
	public static  String SESSION_FACTORY_NAME = "SessionFactory";
	public  final SessionFactory sessionFactory = getSessionFactory();
	
	

	public  static SessionFactory getSessionFactory() {
		try {
			//System.out.println("entered heroku as session factory name added comp env values" );
			//return (SessionFactory) new InitialContext().lookup("java:comp/env/jdbc/heroku_12c8f021a06a74a");
	
			System.out.println("Checking by removing jdbc");
			String jdbcDbUrl = "mysql://eu-cdbr-west-02.cleardb.net:3306/heroku_12c8f021a06a74a?characterEncoding=UTF-8";
			String jdbcDbUname="b516f81a2fdc2a";
			String jdbcPwd="ee11375f";
			String jdsfname="heroku_12c8f021a06a74a";
			
			
		//InitialContext initialContext = new InitialContext();
			//SessionFactory sf = (SessionFactory)  initialContext.lookup("heroku_12c8f021a06a74a");*/
			
			
             Configuration con= new Configuration();		
         	System.out.println("Entered Configuration");
			con.setProperty("hibernate.connection.driver_class",
					"com.mysql.jdbc.Driver");
					con.setProperty("hibernate.connection.url",
							jdbcDbUrl);
					con.setProperty("hibernate.connection.username",jdbcDbUname);
					con.setProperty("hibernate.connection.password", jdbcPwd);
					con.setProperty("hibernate.session_factory_name", jdsfname);
					
					con.setProperty("hibernate.connection.provider_class","org.hibernate.connection.C3P0ConnectionProvider");
					con.setProperty("hibernate.c3p0.min_size", "5");
					con.setProperty("hibernate.c3p0.max_size", "100");
					
					con.setProperty("hibernate.c3p0.max_statements","50");
					con.setProperty("hibernate.c3p0.maxConnectionAge", "1800");
					con.setProperty("hibernate.c3p0.maxIdleTime", "900");
					
					con.setProperty("hibernate.c3p0.idle_test_period","3000");
					con.setProperty("hibernate.c3p0.preferredTestQuery", "select 1 from dual");
					con.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
					
					con.setProperty("hibernate.current_session_context_class","thread");
					con.setProperty("hibernate.show_sql", "true");
					
					
					
					
					System.out.println("the values are"+jdbcDbUname);
					return con.configure("hibernate.cfg.xml").buildSessionFactory();
					
					
					
							
					
					
			/*		sf= con.configure("hibernate.cfg.xml").buildSessionFactory();
					initialContext.bind("SessionFactory", sf);
					return sf;*/
					
					
		
		   
			
			
			//return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			
			/*cfg.configure();
			sf = cfg.buildSessionFactory();
			initialContext.bind("SessionFactory", sf);
			con.setProperty("hibernate.connection.driver_class",
					"com.mysql.jdbc.Driver");
					con.setProperty("hibernate.connection.url",
							jdbcDbUrl);
					con.setProperty("hibernate.connection.username",jdbcDbUname);
					con.setProperty("hibernate.connection.password", jdbcPwd);
					con.setProperty("hibernate.session_factory_name", jdsfname);*/
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}
	
	public void persist(Object obj) {
		try {			
			sessionFactory.getCurrentSession().persist(obj);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void update(Object obj) {
		try {
			sessionFactory.getCurrentSession().update(obj);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public void save(Object obj) {
		try {
			sessionFactory.getCurrentSession().save(obj);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(Object obj) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public void merge(Object obj) {
		try {
			sessionFactory.getCurrentSession().merge(obj);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public void delete(Object obj) {
		try {
			sessionFactory.getCurrentSession().delete(obj);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public void evict(Object obj) {
		try {
			sessionFactory.getCurrentSession().evict(obj);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
}
