package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckMixed;

public class LandCheckMixedHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckMixedHome.class);
	public List<LandCheckMixed> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckMixed.class);
			crit.addOrder(Order.asc("landCheckMixedId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckMixed findByLandCheckMixedId(long landCheckManureId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckMixed.class);
			crit.add(Restrictions.eq("landCheckMixedId", landCheckManureId));
			return (LandCheckMixed) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckMixedId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckMixed> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckMixed.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
	}
	
	public void deleteLandCheckMixed(long landCheckMixedId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckMixed WHERE LandCheckMixedId=:landCheckMixedId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckMixedId", landCheckMixedId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckMixed successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckMixed failed", re);
			throw re;
		}
	}
}
