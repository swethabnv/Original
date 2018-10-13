package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.DiseaseType;

public class DiseaseTypeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(DiseaseTypeHome.class);
	public List<DiseaseType> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(DiseaseType.class);
			crit.addOrder(Order.asc("diseaseTypeId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public DiseaseType findByDiseaseTypeId(long diseaseTypeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(DiseaseType.class);
			crit.add(Restrictions.eq("diseaseTypeId", diseaseTypeId));
			return (DiseaseType)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByDiseaseTypeId failed", re);
			throw re;
		}
	}
	
	public List<DiseaseType> findByDiseaseChildId(long diseaseChildId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(DiseaseType.class);
			crit.add(Restrictions.eq("diseaseChildId", diseaseChildId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByDiseaseChildId failed", re);
			throw re;
		}
	}

}
