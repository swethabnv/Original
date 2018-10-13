package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.ManureType;

public class ManureTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(ManureTypeHome.class);
	public List<ManureType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ManureType.class);
			crit.addOrder(Order.asc("typeName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
	public ManureType findByTypeId(long typeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ManureType.class);	
			crit.add(Restrictions.eq("typeId", typeId));	
			return (ManureType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByTypeId failed", re);
			throw re;
		}
	}
}
