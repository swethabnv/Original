package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.IrrigationArea;

public class IrrigationAreaHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(IrrigationAreaHome.class);
	public List<IrrigationArea> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(IrrigationArea.class);
			crit.addOrder(Order.asc("irrigationAreaName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
}
