package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Region;

public class RegionHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(RegionHome.class);
	public List<Region> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Region.class);
			crit.addOrder(Order.asc("regionName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public Region findByName(String regionName) 
	{
		try {	
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Region.class);
			crit.add(Restrictions.like("regionName", regionName));
			crit.addOrder(Order.asc("regionName"));
			log.debug("findByName successful"); 
			return (Region)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByName failed", re);
			throw re;
		}
	}
	
	public List<Region> searchByName(String regionName) 
	{
		try {	
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Region.class);
			crit.add(Restrictions.like("regionName", "%"+regionName+"%"));
			crit.addOrder(Order.asc("regionName"));
			log.debug("searchByName successful"); 
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByName failed", re);
			throw re;
		}
	}
	
	public Region searchByNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Region.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			return (Region) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("searchByNo failed", re);
			throw re;
		}
	}
	
	public List<Region> searchByNoList(List<String> regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Region.class);
			crit.add(Restrictions.in("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByNo failed", re);
			throw re;
		}
	}

}
