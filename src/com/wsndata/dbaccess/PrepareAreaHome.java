package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PrepareArea;

public class PrepareAreaHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PrepareAreaHome.class);
	public List<PrepareArea> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PrepareArea.class);
			crit.addOrder(Order.asc("prepareAreaId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<PrepareArea> findById(long pprepareAreaId, long breedTypeId, long breedGroupId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PrepareArea.class);
			crit.addOrder(Order.asc("prepareAreaName"));
			crit.add(Restrictions.eq("pprepareAreaId", pprepareAreaId));
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.add(Restrictions.eq("breedGroupId", breedGroupId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
	public List<PrepareArea> searchByCriteria(String prepareAreaName, long breedTypeId, long breedGroupId)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("SELECT prepareArea.PrepareAreaId AS prepareAreaId");
			sqlQuery.append(", prepareArea.PrepareAreaName AS prepareAreaName");
			sqlQuery.append(", prepareArea.PPrepareAreaId AS pPrepareAreaId");
			sqlQuery.append(", (SELECT pPrepareArea.PrepareAreaName FROM PrepareArea AS pPrepareArea WHERE pPrepareArea.PrepareAreaId = prepareArea.pPrepareAreaId) AS pPrepareAreaName");
			sqlQuery.append(", breedType.BreedTypeName AS breedTypeName");
			sqlQuery.append(", breedGroup.BreedGroupName AS breedGroupName");
			sqlQuery.append(" FROM PrepareArea AS prepareArea");
			sqlQuery.append(" left join BreedType AS breedType");
			sqlQuery.append(" on prepareArea.BreedTypeId = breedType.BreedTypeId");
			sqlQuery.append(" left join BreedGroup AS breedGroup");
			sqlQuery.append(" on prepareArea.BreedGroupId = breedGroup.BreedGroupId");
			sqlQuery.append(" WHERE prepareArea.prepareAreaId is NOT NULL");
			if(!"".equals(prepareAreaName))
				sqlQuery.append(" AND prepareArea.PrepareAreaName LIKE :prepareAreaName");
			if(breedTypeId!=0)
				sqlQuery.append(" AND breedType.BreedTypeId = :breedTypeId");
			if(breedGroupId!=0)
				sqlQuery.append(" AND breedGroup.BreedGroupId = :breedGroupId");
		    sqlQuery.append(" ORDER BY  prepareArea.PrepareAreaName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(prepareAreaName))
				query.setString("prepareAreaName", "%"+prepareAreaName+"%");
			if(breedTypeId!=0)
				query.setLong("breedTypeId", breedTypeId);
			if(breedGroupId!=0)
				query.setLong("breedGroupId", breedGroupId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public List findBy(long prepareAreaId) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM PrepareArea ");
			sqlQuery.append("WHERE pPrepareAreaId = :prepareAreaId ");
			sqlQuery.append("AND PrepareAreaId != :prepareAreaId ");
			sqlQuery.append("ORDER BY PrepareAreaId ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("prepareAreaId", prepareAreaId);
		    log.debug("findBy successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}
	
	public PrepareArea findByPrepareAreaId(long prepareAreaId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PrepareArea.class);
			crit.add(Restrictions.eq("prepareAreaId", prepareAreaId));
			return (PrepareArea) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByPrepareAreaId failed", re);
			throw re;
		}
	}
	

	public long getMaxPrepareAreaId()	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(PrepareAreaId)+1 AS PrepareAreaId FROM PrepareArea");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxPrepareAreaId successful"); 
			if(maxId!=null)
				return maxId.longValue();
			else
				return 1;
		}catch (RuntimeException re) {
			log.error("getMaxPrepareAreaId failed", re);
			throw re;
		}
	}
	

	// render on PlantDetail
	public List<PrepareArea> findPPrepareArea(){
		try {	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM PrepareArea ");
			sqlQuery.append("WHERE PrepareAreaId = PPrepareAreaId ");
			sqlQuery.append("ORDER BY PrepareAreaId ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());			
		    log.debug("findPPrepareArea successful"); 		    
		    return query.list();
		} catch (RuntimeException re) {
			log.error("findPPrepareArea failed", re);
			throw re;
		}
	}
	
	//SELECT * FROM preparearea where PPrepareAreaId in (4) and PrepareAreaId <> 4
	public List findSubPrepareArea(long prepareId){
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT PrepareAreaId, PrepareAreaName FROM PrepareArea ");
			sqlQuery.append("WHERE PPrepareAreaId in (:prepareId) ");
			sqlQuery.append("AND PrepareAreaId <> :prepareId");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("prepareId", prepareId);
		    log.debug("findSubPrepareArea successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("findPPrepareArea failed", re);
			throw re;
		}
	}

	public List<PrepareArea> searchByPrepareAreaName(String prepareAreaName, long pprepareAreaId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PrepareArea.class);
			crit.add(Restrictions.eq("prepareAreaName", prepareAreaName));
			if(pprepareAreaId>0)
				crit.add(Restrictions.eq("pprepareAreaId", pprepareAreaId));
			else
				crit.add(Restrictions.eqProperty("pprepareAreaId", "prepareAreaId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByPrepareAreaName failed", re);
			throw re;
		}
	}
}
