package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerFamily;


public class FarmerFamilyHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(FarmerFamilyHome.class);
	public List<FarmerFamily> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerFamily.class);
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public void deleteFarmerFamily(long plantId)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM FarmerFamily WHERE plantId=:plantId");
			uQuery.setLong("plantId", plantId);
			uQuery.executeUpdate();
			log.info("deleteFarmerFamily successful");
		}catch (RuntimeException re) {
			log.error("deleteFarmerFamily failed", re);
			throw re;
		}
	}
	
	
}
