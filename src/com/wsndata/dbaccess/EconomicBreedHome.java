package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.EconomicBreed;

public class EconomicBreedHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(EconomicBreedHome.class);
	public List<EconomicBreed> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EconomicBreed.class);
			crit.addOrder(Order.asc("breedTypeId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<EconomicBreed> searchByName(String breedTypeName, long regionNo, String provinceName) 
	{	
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT e.breedTypeId AS breedTypeId, b.breedTypeName AS breedTypeName, p.ThaiName AS provinceName, e.provinceNo,e.regionNo FROM EconomicBreed e ");
			sqlQuery.append("LEFT JOIN BreedType b ON e.breedTypeId=b.breedTypeId ");
			sqlQuery.append("LEFT JOIN Region r ON e.regionNo=r.regionNo ");
			sqlQuery.append("LEFT JOIN Province p ON e.provinceNo=p.provinceNo WHERE e.breedTypeId IS NOT NULL ");
			if(!"".equals(breedTypeName))
				sqlQuery.append("AND b.breedTypeName LIKE :breedTypeName ");
			if(regionNo!=0)
				sqlQuery.append("AND e.RegionNo LIKE :regionNo ");
			if(!"".equals(provinceName))
				sqlQuery.append("AND p.ThaiName LIKE :provinceName ");
			sqlQuery.append("ORDER BY e.breedTypeId ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(breedTypeName))
				query.setString("breedTypeName", breedTypeName + "%");
			if(regionNo!=0)
				query.setLong("regionNo", regionNo);
			if(!"".equals(provinceName))
				query.setString("provinceName", provinceName + "%");			
		    log.debug("searchByName successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("searchByName failed", re);
			throw re;
		}
	}
	
	public EconomicBreed findByBreedTypeId(long breedTypeId,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EconomicBreed.class);	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return (EconomicBreed)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
	
	public List<EconomicBreed> findByBreedTypeId2(long breedTypeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EconomicBreed.class);	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId2 failed", re);
			throw re;
		}
	}
	
	public List<EconomicBreed> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EconomicBreed.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<EconomicBreed> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EconomicBreed.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));		
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
}
