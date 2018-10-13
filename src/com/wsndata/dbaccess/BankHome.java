package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Bank;

public class BankHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(BankHome.class);
	public List<Bank> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Bank.class);
			crit.addOrder(Order.asc("bankId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Bank> searchByBankName(String bankName) 
	{
		try {	
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Bank.class);
			crit.add(Restrictions.like("bankName", "%"+bankName+"%"));
			crit.addOrder(Order.asc("bankName"));
			log.debug("findByBankName successful"); 
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBankName failed", re);
			throw re;
		}
	}
	
	public Bank findByBankName(String bankName) 
	{
		try {	
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Bank.class);
			crit.add(Restrictions.eq("bankName", bankName));
			crit.addOrder(Order.asc("bankName"));
			log.debug("findByBankName successful"); 
			return (Bank)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBankName failed", re);
			throw re;
		}
	}
	
	public Bank findByBankId(long bankId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Bank.class);
			crit.add(Restrictions.eq("bankId", bankId));
			return (Bank) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBankId failed", re);
			throw re;
		}
	}

}
