package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckPrepareSoil;

public class LandCheckPrepareSoilHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckPrepareSoilHome.class);
	public List<LandCheckPrepareSoil> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPrepareSoil.class);
			crit.addOrder(Order.asc("landCheckSoilId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckPrepareSoil findByLandCheckSoilId(long landCheckSoilId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPrepareSoil.class);
			crit.add(Restrictions.eq("landCheckSoilId", landCheckSoilId));
			return (LandCheckPrepareSoil) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckSoilId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckPrepareSoil> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckPrepareSoil.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
		
	}
	
	public void deleteLandCheckPrepareSoil(long landCheckSoilId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckPrepareSoil WHERE LandCheckSoilId=:landCheckSoilId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckSoilId", landCheckSoilId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckPrepareSoil successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckPrepareSoil failed", re);
			throw re;
		}
	}
}
