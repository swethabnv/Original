package com.wsndata.dbaccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Login;

public class LoginHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(LoginHome.class);
	
	public Login loggingOn(String userName)
	{
			try {		
				
				Criteria crit = sessionFactory.getCurrentSession().createCriteria(Login.class);
				
				crit.add(Restrictions.eq("userName", userName));
				System.out.println("entered database");
				return (Login) crit.uniqueResult();
				
				
			} catch (RuntimeException re) {
				log.error("authenticateUsers failed", re);
				throw re;
			}
	}
	
	public Login loggingOutOfDate(String userName)
	{
			try {	
			
				Criteria crit = sessionFactory.getCurrentSession().createCriteria(Login.class);
				
				//Criteria crit = sessionFactory.getCurrentSession().createCriteria(Login.class);
				crit.add(Restrictions.eq("userName", userName));
				crit.add(Restrictions.gt("countLogin", 0));
				Calendar cal = Calendar.getInstance(); // creates calendar
			    cal.setTime(new Date()); // sets calendar time/date
			    cal.add(Calendar.HOUR_OF_DAY, -12); // decrease 12 hour
			    Date oldDate = cal.getTime(); // returns new date object
				crit.add(Restrictions.lt("lastLoginDate", oldDate));
				return (Login) crit.uniqueResult();
			} catch (RuntimeException re) {
				log.error("authenticateUsers failed", re);
				throw re;
			}
	}
	
	public void deleteHistory(String userName)	{
		
		try {	
			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Login WHERE userName=:userName");
			
			uQuery.setString("userName", userName);
			uQuery.executeUpdate();
			log.info("deleteHistory successful");
		}catch (RuntimeException re) {
			log.error("deleteHistory failed", re);
			throw re;
		}
	}
	
}
