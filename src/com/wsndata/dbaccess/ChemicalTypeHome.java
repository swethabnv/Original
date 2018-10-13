package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.ChemicalType;

public class ChemicalTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(ChemicalTypeHome.class);
	public List<ChemicalType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ChemicalType.class);
			crit.addOrder(Order.asc("typeName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public ChemicalType findByTypeId(long typeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(ChemicalType.class);	
			crit.add(Restrictions.eq("typeId", typeId));	
			return (ChemicalType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByTypeId failed", re);
			throw re;
		}
	}
}
