package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.SeedSelect;

public class SeedSelectHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(SeedSelectHome.class);
	public List<SeedSelect> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SeedSelect.class);
			crit.addOrder(Order.asc("seedSelectName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
}
