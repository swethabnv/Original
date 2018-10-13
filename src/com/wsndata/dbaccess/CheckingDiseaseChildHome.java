package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.CheckingDiseaseChild;

public class CheckingDiseaseChildHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CheckingDiseaseChildHome.class);
	public List<CheckingDiseaseChild> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckingDiseaseChild.class);
			crit.addOrder(Order.asc("diseaseChildId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<CheckingDiseaseChild> findByCheckingDiseaseId(long checkingDiseaseId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(CheckingDiseaseChild.class);
			crit.add(Restrictions.eq("checkingDiseaseId", checkingDiseaseId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByCheckingDiseaseId failed", re);
			throw re;
		}
	}

}
