package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.BreedGroup;

public class BreedGroupHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(BreedGroupHome.class);
	public List<BreedGroup> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedGroup.class);
			crit.addOrder(Order.asc("breedGroupName")); // from sort by breedGroupId
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<BreedGroup> searchByBreedGroupName(String breedGroupName,long breedTypeId) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT bg.breedGroupId AS breedGroupId, bg.breedGroupName AS breedGroupName, bt.breedTypeId AS breedTypeId, bt.breedTypeName AS breedTypeName FROM BreedGroup bg ");
			sqlQuery.append("LEFT JOIN BreedType bt ON bg.breedTypeId=bt.breedTypeId WHERE bg.breedGroupId IS NOT NULL ");
			if(!"".equals(breedGroupName))
				sqlQuery.append("AND bg.breedGroupName LIKE :breedGroupName ");
			if(breedTypeId>0 )
				sqlQuery.append("AND bt.breedTypeId = :breedTypeId ");
			sqlQuery.append("ORDER BY bg.breedGroupName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(breedGroupName))
				query.setString("breedGroupName", "%"+breedGroupName+"%");
			if(breedTypeId>0 )
				query.setLong("breedTypeId", breedTypeId);			
		    log.debug("searchByBreedGroupName successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("searchByBreedGroupName failed", re);
			throw re;
		}
	}
	
	public BreedGroup findByBreedGroupId(long breedGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedGroup.class);	
			crit.add(Restrictions.eq("breedGroupId", breedGroupId));	
			return (BreedGroup)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBreedGroupId failed", re);
			throw re;
		}
	}
	
	public BreedGroup findByBreedGroupName(String breedGroupName,long breedTypeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedGroup.class);	
			crit.add(Restrictions.eq("breedGroupName", breedGroupName));	
			if(breedTypeId > 0)
				crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return (BreedGroup)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBreedGroupName failed", re);
			throw re;
		}
	}

	public int getMaxId() {
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(BreedGroupId) AS BreedGroupId FROM BreedGroup");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxId successful");
			if (maxId==null) {
				return 0;
			}
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getMaxId failed", re);
			throw re;
		}
	}
	
	public void deleteBreedGroup(long breedTypeId, long breedGroupId) {
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("DELETE FROM BreedGroup WHERE breedTypeId = :breedTypeId");
			sqlQuery.append(" AND breedGroupId = :breedGroupId");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("breedTypeId", breedTypeId);
			query.setLong("breedGroupId", breedGroupId);
			query.executeUpdate();
			log.debug("deleteBreedGroup successful");
		}catch (RuntimeException re) {
			log.error("deleteBreedGroup failed", re);
			throw re;
		}
	}
	
	public List<BreedGroup> searchByGroupId(long groupId)
	{
		try{
			Criteria query = sessionFactory.getCurrentSession().createCriteria(BreedGroup.class);
			query.add(Restrictions.eq("breedGroupId", groupId));
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByGroupId failed", re);
			throw re;
		}
	}
	
	public List<BreedGroup> findByBreedTypeId(long breedTypeId) 
	{
		try{
			Criteria query = sessionFactory.getCurrentSession().createCriteria(BreedGroup.class);
			query.add(Restrictions.eq("breedTypeId", breedTypeId));
			query.addOrder(Order.asc("breedGroupName")); 
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
	
	
	public boolean findbyTable(String table,long breedGroupId)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM "+table);
			sqlQuery.append(" WHERE breedGroupId = ?");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, breedGroupId);
			if (query.list().size()==0) {
				return true;
			}else {
				return false;
			}
		} catch (RuntimeException re) {
			log.error("findbyTable failed", re);
			throw re;
		}
		
	}
}
