package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckManure;

public class LandCheckManureHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckManureHome.class);
	public List<LandCheckManure> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckManure.class);
			crit.addOrder(Order.asc("landCheckManureId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckManure findByLandCheckManureId(long landCheckManureId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckManure.class);
			crit.add(Restrictions.eq("landCheckManureId", landCheckManureId));
			return (LandCheckManure) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckManureId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckManure> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckManure.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
	}
	
	public void deleteLandCheckManure(long landCheckManureId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckManure WHERE LandCheckManureId=:landCheckManureId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckManureId", landCheckManureId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckManure successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckManure failed", re);
			throw re;
		}
	}
}
