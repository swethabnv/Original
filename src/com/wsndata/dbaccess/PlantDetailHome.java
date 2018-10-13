package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PlantDetail;

public class PlantDetailHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PlantDetailHome.class);
	public List<PlantDetail> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantDetail.class);
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<PlantDetail> findByBreedTypeId(long breedTypeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantDetail.class);
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
	
	public List<PlantDetail> findbyPlantBreed(long plantId,long breedTypeId,long breedGroupId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantDetail.class);
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (breedTypeId>0) {
				crit.add(Restrictions.eq("breedTypeId", breedTypeId));	
			}
			if (breedGroupId>0) {
				crit.add(Restrictions.eq("breedGroupId", breedGroupId));	
			}
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findbyPlantBreed failed", re);
			throw re;
		}
	}

	public PlantDetail findByCriteria(long plantId,long breedTypeId,long breedGroupId,int seq) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantDetail.class);
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (breedTypeId>0) {
				crit.add(Restrictions.eq("breedTypeId", breedTypeId));	
			}
			if (breedGroupId>0) {
				crit.add(Restrictions.eq("breedGroupId", breedGroupId));	
			}
			if (seq>0) {
				crit.add(Restrictions.eq("seq", seq));	
			}
			return (PlantDetail)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public List<PlantDetail> findByPrepareArea(String prepareAreaId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM PlantDetail WHERE ");
			sqlQuery.append("FIND_IN_SET(?,prepareArea) ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, prepareAreaId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByPrepareArea failed", re);
			throw re;
		}
		
	}
	
	public List<PlantDetail> findByPlantMethod(String plantMethodId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM PlantDetail WHERE ");
			sqlQuery.append("FIND_IN_SET(?,plantMethod) ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, plantMethodId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByPlantMethod failed", re);
			throw re;
		}
		
	}
}

