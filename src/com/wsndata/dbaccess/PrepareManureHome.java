package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PrepareManure;

public class PrepareManureHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PrepareManureHome.class);
	public List<PrepareManure> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PrepareManure.class);
			crit.addOrder(Order.asc("prepareManureName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
}
