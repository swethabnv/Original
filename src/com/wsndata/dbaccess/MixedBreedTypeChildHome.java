package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.MixedBreedTypeChild;

public class MixedBreedTypeChildHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(MixedBreedTypeChildHome.class);
	public List<MixedBreedTypeChild> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(MixedBreedTypeChild.class);
			crit.addOrder(Order.asc("childId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public MixedBreedTypeChild findByChildId(long childId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(MixedBreedTypeChild.class);
			crit.add(Restrictions.eq("childId", childId));
			return (MixedBreedTypeChild)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByChildId failed", re);
			throw re;
		}
	}

}
