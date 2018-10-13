package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckPlant;

public class LandCheckPlantHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckPlantHome.class);
	public List<LandCheckPlant> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPlant.class);
			crit.addOrder(Order.asc("landCheckPlantId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckPlant findByLandCheckPlantId(long landCheckPlantId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPlant.class);
			crit.add(Restrictions.eq("landCheckPlantId", landCheckPlantId));
			return (LandCheckPlant) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckPlantId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckPlant> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPlant.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
		
	}
	
	public void deleteLandCheckPlant(long landCheckPlantId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckPlant WHERE LandCheckPlantId=:landCheckPlantId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckPlantId", landCheckPlantId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckPlant successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckPlant failed", re);
			throw re;
		}
	}
}
