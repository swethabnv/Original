package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Cost;
import com.wsndata.data.CostDetail;

public class CostHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CostHome.class);
	public List<Cost> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Cost.class);
			crit.addOrder(Order.asc("costName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Cost> searchByCostName(String costName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Cost.class);
			crit.add(Restrictions.like("costName", costName, MatchMode.ANYWHERE));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByCostName failed", re);
			throw re;
		}
	}
	
	public Cost findByCostId(long costId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Cost.class);	
			crit.add(Restrictions.eq("costId", costId));	
			return (Cost)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCostId failed", re);
			throw re;
		}
	}
	public List<CostDetail> findInCostDetail(long costId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CostDetail.class);	
			crit.add(Restrictions.eq("costId", costId));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findInCostDetail failed", re);
			throw re;
		}
	}
	
	public Cost findByCostName(String costName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Cost.class);	
			crit.add(Restrictions.eq("costName", costName));	
			return (Cost)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCostName failed", re);
			throw re;
		}
	}
}
