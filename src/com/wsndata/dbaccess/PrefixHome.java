package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Prefix;

public class PrefixHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PrefixHome.class);
	public List<Prefix> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Prefix.class);
			crit.addOrder(Order.asc("abbrPrefix"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Prefix> searchByAbbrPrefix(String abbrPrefix) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Prefix.class);
			crit.add(Restrictions.like("abbrPrefix", abbrPrefix, MatchMode.ANYWHERE));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByAbbrPrefix failed", re);
			throw re;
		}
		
	}
	public Prefix findByAbbrPrefix(String abbrPrefix) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Prefix.class);	
			crit.add(Restrictions.eq("abbrPrefix", abbrPrefix));	
			return (Prefix)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByAbbrPrefix failed", re);
			throw re;
		}
	}
	
	public List findBy(String abbrPrefix) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Farmer ");
			sqlQuery.append("WHERE abbrPrefix = ? ");
			sqlQuery.append("ORDER BY abbrPrefix ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, abbrPrefix);
		    log.debug("findBy successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
		
	}
	
}
