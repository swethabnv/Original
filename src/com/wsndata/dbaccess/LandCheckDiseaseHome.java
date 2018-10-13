package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckDisease;

public class LandCheckDiseaseHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckDiseaseHome.class);
	public List<LandCheckDisease> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckDisease.class);
			crit.addOrder(Order.asc("landCheckDiseaseId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public LandCheckDisease findByLandCheckDiseaseId(long landCheckDiseaseId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckDisease.class);
			crit.add(Restrictions.eq("landCheckDiseaseId", landCheckDiseaseId));
			return (LandCheckDisease) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckDiseaseId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckDisease> getByLandCheckId(long landCheckId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckDisease.class);
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByLandCheckId failed", re);
			throw re;
		}
	}
	
	public void deleteLandCheckDisease(long landCheckDiseaseId)	{
		try {
			String sqlQuery = "DELETE FROM LandCheckDisease WHERE LandCheckDiseaseId=:landCheckDiseaseId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("landCheckDiseaseId", landCheckDiseaseId);
			uQuery.executeUpdate();
			log.info("deleteLandCheckDisease successful");
		}catch (RuntimeException re) {
			log.error("deleteLandCheckDisease failed", re);
			throw re;
		}
	}
}
