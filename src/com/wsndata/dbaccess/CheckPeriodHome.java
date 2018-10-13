package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.CheckPeriod;

public class CheckPeriodHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CheckPeriodHome.class);
	public List<CheckPeriod> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckPeriod.class);
			crit.addOrder(Order.asc("checkPeriodId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public CheckPeriod findByCheckPeriod(long checkPeriod)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckPeriod.class);
			crit.add(Restrictions.eq("checkPeriodId", checkPeriod));
			return (CheckPeriod)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCheckPeriod failed", re);
			throw re;
		}
	}

}
