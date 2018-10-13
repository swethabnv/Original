package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PlantDetail;
import com.wsndata.data.ProductionForecast;
import com.wsndata.data.SellingDetail;

public class ProductionForecastHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(ProductionForecastHome.class);
	public List<ProductionForecast> findByRegionNo(long regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProductionForecastHome.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	public List<ProductionForecast> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProductionForecastHome.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<ProductionForecast> findByBreedTypeId(long breedTypeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProductionForecast.class);
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
}

