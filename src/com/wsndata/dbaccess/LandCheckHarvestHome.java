package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckHarvest;

public class LandCheckHarvestHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckHarvestHome.class);
	public List<LandCheckHarvest> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckHarvest.class);
			crit.addOrder(Order.asc("landCheckHarvestId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckHarvest findByLandCheckHarvestId(long landCheckHarvestId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckHarvest.class);
			crit.add(Restrictions.eq("landCheckHarvestId", landCheckHarvestId));
			return (LandCheckHarvest) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckHarvestId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckHarvest> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckHarvest.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
		
	}
	
	public void deleteLandCheckHarvest(long landCheckHarvestId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckHarvest WHERE LandCheckHarvestId=:landCheckHarvestId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckHarvestId", landCheckHarvestId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckHarvest successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckHarvest failed", re);
			throw re;
		}
	}
}
