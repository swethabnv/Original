package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandRightType;

public class LandRightTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandRightTypeHome.class);
	public List<LandRightType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRightType.class);
			crit.addOrder(Order.asc("typeName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public LandRightType findByTypeId(long typeId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRightType.class);	
			crit.add(Restrictions.eq("typeId", typeId));	
			return (LandRightType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByTypeId failed", re);
			throw re;
		}
	}
	
	public LandRightType findByTypeName(String typeName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRightType.class);
			crit.add(Restrictions.eq("typeName", typeName));		
			return (LandRightType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByTypeName failed", re);
			throw re;
		}
	}
}
