package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.EliminateMixedBreed;

public class EliminateMixedBreedHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(EliminateMixedBreedHome.class);
	public List<EliminateMixedBreed> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EliminateMixedBreed.class);
			crit.addOrder(Order.asc("eliminateMixedBreedId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public EliminateMixedBreed findByEliminateMixedBreedId(long eliminateMixedBreedId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(EliminateMixedBreed.class);
			crit.add(Restrictions.eq("eliminateMixedBreedId", eliminateMixedBreedId));
			return (EliminateMixedBreed)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByEliminateMixedBreedId failed", re);
			throw re;
		}
	}

}
