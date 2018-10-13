package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PlantMethod;

public class PlantMethodHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PlantMethodHome.class);
	public List<PlantMethod> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantMethod.class);
			crit.addOrder(Order.asc("plantMethodName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
	public List<PlantMethod> findById(long breedTypeId, long breedGroupId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantMethod.class);
			crit.addOrder(Order.asc("plantMethodName"));
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.add(Restrictions.eq("breedGroupId", breedGroupId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}
	
	public List<PlantMethod> searchByCriteria(String plantMethodName, long breedTypeId, long breedGroupId)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("SELECT plantMethod.PlantMethodId AS plantMethodId");
			sqlQuery.append(", plantMethod.PlantMethodName AS plantMethodName");
			sqlQuery.append(", breedType.BreedTypeName AS breedTypeName");
			sqlQuery.append(", breedGroup.BreedGroupName AS breedGroupName");
			sqlQuery.append(" FROM PlantMethod AS plantMethod");
			sqlQuery.append(" left join BreedType AS breedType");
			sqlQuery.append(" on plantMethod.BreedTypeId = breedType.BreedTypeId");
			sqlQuery.append(" left join BreedGroup AS breedGroup");
			sqlQuery.append(" on plantMethod.BreedGroupId = breedGroup.BreedGroupId");
			sqlQuery.append(" WHERE plantMethod.plantMethodId is NOT NULL");
			if(!"".equals(plantMethodName))
				sqlQuery.append(" AND plantMethod.PlantMethodName LIKE :plantMethodName");
			if(breedTypeId!=0)
				sqlQuery.append(" AND breedType.BreedTypeId = :breedTypeId");
			if(breedGroupId!=0)
				sqlQuery.append(" AND breedGroup.BreedGroupId = :breedGroupId");
		    sqlQuery.append(" ORDER BY  plantMethod.PlantMethodName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(plantMethodName))
				query.setString("plantMethodName", "%"+plantMethodName+"%");
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
	
	public List findBy(long plantMethodId) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM PlantMethod ");
			sqlQuery.append("WHERE pPlantMethodId = :plantMethodId ");
			sqlQuery.append("AND PlantMethodId != :plantMethodId ");
			sqlQuery.append("ORDER BY PlantMethodId ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("plantMethodId", plantMethodId);
		    log.debug("findBy successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("findBy failed", re);
			throw re;
		}
	}
	
	public PlantMethod findByPlantMethodId(long plantMethodId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantMethod.class);
			crit.add(Restrictions.eq("plantMethodId", plantMethodId));
			return (PlantMethod) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByPlantMethodId failed", re);
			throw re;
		}
	}
	
	public PlantMethod findByPlantMethodName(String plantMethodName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantMethod.class);
			crit.add(Restrictions.eq("plantMethodName", plantMethodName));
			return (PlantMethod) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByPlantMethodName failed", re);
			throw re;
		}
	}
	
}
