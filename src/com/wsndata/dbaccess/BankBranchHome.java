package com.wsndata.dbaccess;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.BankBranch;

public class BankBranchHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(BankBranchHome.class);
	public List<BankBranch> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BankBranch.class);
			crit.addOrder(Order.asc("branchCode"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public BankBranch findByBranchCode(long branchCode, long bankId) 
	{
		try {	
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BankBranch.class);
			crit.add(Restrictions.eq("branchCode", branchCode));
			crit.add(Restrictions.eq("bankId", bankId));
			log.debug("findByBankName successful"); 
			return (BankBranch)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBranchCode failed", re);
			throw re;
		}
	}
	
	public List<BankBranch> searchByBankId(long bankId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(BankBranch.class);
			crit.add(Restrictions.eq("bankId", bankId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByBankId failed", re);
			throw re;
		}
	}
	
	
	public List filterByBankId(long bankId) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT b.BranchCode, b.BranchName, bb.BankId FROM BankBranch as bb ");
			sqlQuery.append("LEFT OUTER JOIN Branch as b ");
			sqlQuery.append("ON bb.BranchCode = b.BranchCode ");
			sqlQuery.append("WHERE bb.BankId=? ORDER BY b.BranchName ASC"); 
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());	
			query.setLong(0, bankId);
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByBankId failed", re);
			throw re;
		}
	}
	
	
	
	public void addBranchCode(long branchCode, long bankId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("INSERT INTO BankBranch ");
		    sqlQuery.append("VALUES(?, ?) ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, branchCode);
			query.setLong(1, bankId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("addBranchCode failed", re);
			throw re;
		}
	}
	
	public void deleteAllBranch(long bankId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append(" DELETE FROM BankBranch");
		    sqlQuery.append(" WHERE BankId = ?");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, bankId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteAllBranch failed", re);
			throw re;
		}
	}
	
	public void deleteByBranchCode(long branchCode, long bankId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("DELETE FROM BankBranch ");
		    sqlQuery.append("WHERE BankId=? ");
		    sqlQuery.append("And BranchCode=? ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, bankId);
			query.setLong(1, branchCode);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteByBranchCode failed", re);
			throw re;
		}
	}

}
