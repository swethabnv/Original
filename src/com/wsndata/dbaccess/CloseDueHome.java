package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.CloseDue;

public class CloseDueHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CloseDueHome.class);
	public List<CloseDue> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CloseDue.class);
			crit.addOrder(Order.asc("plantYear"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List searchByCriteria(long plantYear, long plantNo, long farmerGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT c.CloseDueId, ");// 0
			sqlQuery.append("c.PlantYear, ");//1
			sqlQuery.append("c.PlantNo, ");//2
			sqlQuery.append("c.FarmerGroupId, ");//3
			sqlQuery.append("fg.FarmerGroupName ");//4
			sqlQuery.append("FROM CloseDue AS c ");
			sqlQuery.append("LEFT JOIN FarmerGroup AS fg ON c.FarmerGroupId = fg.FarmerGroupId ");
		    sqlQuery.append("WHERE CloseDueId>0 ");
		    
		    if(plantYear > 0)
		    	sqlQuery.append("AND c.PlantYear = :plantYear ");
		    if(plantNo > 0)
		    	sqlQuery.append("AND c.PlantNo = :plantNo ");
		    if(farmerGroupId > 0)
		    	sqlQuery.append("AND fg.FarmerGroupId = :farmerGroupId ");
		    	
		    sqlQuery.append("ORDER BY PlantYear, PlantNo ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if(plantYear > 0)
		    	query.setLong("plantYear", plantYear);
		    if(plantNo > 0)
		    	query.setLong("plantNo", plantNo);
		    if(farmerGroupId>0)
		    	query.setLong("farmerGroupId", farmerGroupId);
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public CloseDue findByCloseDueId(long closeDueId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CloseDue.class);
			if(closeDueId>0)
				crit.add(Restrictions.eq("closeDueId", closeDueId));
			return (CloseDue)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCloseDueId failed", re);
			throw re;
		}
	}
	
	public CloseDue findByCriteria(long plantYear, long plantNo, long farmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CloseDue.class);
			crit.add(Restrictions.eq("plantYear", plantYear));
			crit.add(Restrictions.eq("plantNo", plantNo));
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return (CloseDue)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
}
