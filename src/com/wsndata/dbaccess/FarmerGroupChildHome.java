package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerGroupChild;

public class FarmerGroupChildHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(FarmerGroupChildHome.class);
	public List<FarmerGroupChild> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupChild.class);
			crit.addOrder(Order.asc("farmerGroupId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public FarmerGroupChild findByFarmerGroupId(long farmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupChild.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return (FarmerGroupChild) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupId failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupChild> findByChildFarmerGroupId(long childFarmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupChild.class);
			crit.add(Restrictions.eq("childFarmerGroupId", childFarmerGroupId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupId failed", re);
			throw re;
		}
	}

	public void saveFarmerGroupChild(long farmerGroupId, long childFarmerGroupId) {
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("INSERT INTO FarmerGroupChild VALUES(?, ?) ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, farmerGroupId);
			query.setLong(1, childFarmerGroupId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("saveFarmerGroupChild failed", re);
			throw re;
		}
	}
	
	public void deleteChildByFarmerGroupId(long farmerGroupId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("DELETE FROM FarmerGroupChild ");
			sqlQuery.append("WHERE FarmerGroupId = ? ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, farmerGroupId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteChildByFarmerGroupId failed", re);
			throw re;
		}
	}
	
}
