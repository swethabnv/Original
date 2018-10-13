package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.UserType;

public class UserTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(UserTypeHome.class);
	public List<UserType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserType.class);
			crit.addOrder(Order.asc("userName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<UserType> searchByCriteria(String userName, String userType) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserType.class);
			if(userName.equals(""))
				crit.add(Restrictions.eq("userName", userName));
			if(userType.equals(""))
				crit.add(Restrictions.eq("userType", userType));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public List<UserType> findByUserName(String userName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserType.class);
			crit.add(Restrictions.eq("userName", userName));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByUserName failed", re);
			throw re;
		}
	}
	
	public void deleteByUserName(String userName) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("DELETE FROM UserType ");
		    sqlQuery.append("WHERE UserName=? ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, userName);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteByUserName failed", re);
			throw re;
		}
	}
	
}
