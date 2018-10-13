package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.CheckingDisease;

public class CheckingDiseaseHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CheckingDiseaseHome.class);
	public List<CheckingDisease> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckingDisease.class);
			crit.addOrder(Order.asc("checkingDiseaseId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public CheckingDisease findByCheckingDiseaseId(long checkingDiseaseId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckingDisease.class);
			crit.add(Restrictions.eq("checkingDiseaseId", checkingDiseaseId));
			return (CheckingDisease)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCheckingDiseaseId failed", re);
			throw re;
		}
	}

}
