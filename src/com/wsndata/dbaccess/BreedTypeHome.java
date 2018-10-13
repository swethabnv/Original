package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.BreedType;

public class BreedTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(BreedTypeHome.class);
	public List<BreedType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedType.class);
			crit.addOrder(Order.asc("breedTypeId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<BreedType> findAllNonEconomic()
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT b.* FROM BreedType b ");
			sqlQuery.append("RIGHT JOIN EconomicBreed e ON b.breedTypeId <> e.breedTypeId ");
			sqlQuery.append("ORDER BY b.breedTypeId ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(BreedType.class);			
		    log.debug("findAllNonEconomic successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("findAllNonEconomic failed", re);
			throw re;
		}
	}
	
	public List<BreedType> searchByBreedTypeName(String breedTypeName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedType.class);
			crit.add(Restrictions.like("breedTypeName", breedTypeName, MatchMode.ANYWHERE));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByBreedTypeId failed", re);
			throw re;
		}
	}
	
	public BreedType findByBreedTypeId(long breedTypeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedType.class);	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));	
			return (BreedType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
	
	public boolean findbyTable(String table, long breedTypeId)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM "+table);
			sqlQuery.append(" WHERE breedTypeId = ? ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, breedTypeId);
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
	
	public BreedType findByBreedTypeName(String breedTypeName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BreedType.class);	
			crit.add(Restrictions.eq("breedTypeName", breedTypeName));	
			return (BreedType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByDistrictNo failed", re);
			throw re;
		}
	}
}
