package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerGroupAddress;

public class FarmerGroupAddressHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(FarmerGroupAddressHome.class);
	public List<FarmerGroupAddress> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);
			crit.addOrder(Order.asc("farmerGroupId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public FarmerGroupAddress findByFarmerGroupId(long farmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return (FarmerGroupAddress) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupId failed", re);
			throw re;
		}
	}
	
	public void saveFarmerGroupAddress(long farmerGroupId,long regionNo,long provinceNo,long districtNo,long subDistrictNo) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("INSERT INTO FarmerGroupAddress ");
		    sqlQuery.append("VALUES(?, ?, ?, ?, ?) ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, farmerGroupId);
			query.setLong(1, regionNo);
			query.setLong(2, provinceNo);
			query.setLong(3, districtNo);
			query.setLong(4, subDistrictNo);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("saveFarmerGroupAddress failed", re);
			throw re;
		}
	}
	
	public void deleteAddressByFarmerGroupId(long farmerGroupId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("DELETE FROM FarmerGroupAddress ");
			sqlQuery.append("WHERE FarmerGroupId=? ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, farmerGroupId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteAddressByFarmerGroupId failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupAddress> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupAddress> findByProvinceNo(long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupAddress> findByDistrictNo(long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);	
			crit.add(Restrictions.eq("districtNo", districtNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByDistrictNo failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupAddress> findBySubDistrictNo(long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupAddress.class);	
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findBySubDistrictNo failed", re);
			throw re;
		}
	}
	
}
