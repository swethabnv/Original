package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.District;



public class DistrictHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(DistrictHome.class);
	
	public List<District> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	public List<District> findByRegionNo(long regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<District> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	
	public List<District> findDistrictByKeyNo(long regionNo, long provinceNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findDistrictByKeyNo failed", re);
			throw re;
		}
	}
	
	
	public List<District> findByProvinceNo(long provinceNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	
	
	public District findByName(long provinceNo, String thaiName)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			return (District)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByName failed", re);
			throw re;
		}
	}
	
	public List searchAll()
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r.RegionNo AS regionNo");
			sqlQuery.append(" ,r.RegionName AS regionName");
			sqlQuery.append(" ,p.ProvinceNo AS provinceNo");
			sqlQuery.append(" ,p.ThaiName AS provinceName");
			sqlQuery.append(" ,d.DistrictNo AS districtNo");
			sqlQuery.append(" ,d.ThaiName AS thaiName");
		    sqlQuery.append(" ,d.EngName AS engName");
			sqlQuery.append(" FROM District AS d");
		    sqlQuery.append(" LEFT JOIN Region AS r ON d.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS p ON d.ProvinceNo = p.ProvinceNo");
		    sqlQuery.append(" WHERE r.RegionNo <> 'null' AND p.ProvinceNo <> 'null'");
		    sqlQuery.append(" ORDER BY  d.thaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchAll failed", re);
			throw re;
		}
		
	}
	
	public List searchByKey(long regionNo, long provinceNo, String thaiName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r.RegionNo AS regionNo");
			sqlQuery.append(" ,r.RegionName AS regionName");
			sqlQuery.append(" ,p.ProvinceNo AS provinceNo");
			sqlQuery.append(" ,p.ThaiName AS provinceName");
			sqlQuery.append(" ,d.DistrictNo AS districtNo");
			sqlQuery.append(" ,d.ThaiName AS thaiName");
		    sqlQuery.append(" ,d.EngName AS engName");
			sqlQuery.append(" FROM District AS d");
		    sqlQuery.append(" LEFT JOIN Region AS r ON d.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS p ON d.ProvinceNo = p.ProvinceNo");
		    sqlQuery.append(" WHERE r.RegionNo <> 'null'");
			if(regionNo > 0)
				sqlQuery.append(" AND r.regionNo=:regionNo"); 
			if(provinceNo > 0)
				sqlQuery.append(" AND p.provinceNo=:provinceNo"); 
			if(!"".equals(thaiName))
				sqlQuery.append(" AND d.thaiName LIKE :thaiName OR d.engName LIKE :thaiName");
			sqlQuery.append(" ORDER BY  d.thaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(!"".equals(thaiName))
				query.setString("thaiName", "%"+thaiName+"%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByKey failed", re);
			throw re;
		}
		
	}
	
	public List searchByCriteria(long regionNo, long provinceNo, String thaiName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM District WHERE ");
			if(regionNo > 0)
				sqlQuery.append("regionNo=:regionNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND provinceNo=:provinceNo "); 
			if(!"".equals(thaiName))
				sqlQuery.append("AND ( thaiName LIKE :thaiName OR engName LIKE :thaiName )");
			sqlQuery.append(" ORDER BY thaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(!"".equals(thaiName))
				query.setString("thaiName", "%"+thaiName+"%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
		
	}
	
	public District findByKey(long regionNo, long provinceNo, long districtNo){
		try{
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));	
			return (District)crit.uniqueResult();
		}catch (RuntimeException re) {
			log.error("findByKey failed", re);
			throw re;
		}
	}
	
	public District findByDistrictNo(long districtNo) {
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);	
			crit.add(Restrictions.eq("districtNo", districtNo));	
			return (District)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByDistrictNo failed", re);
			throw re;
		}
	}
	
	public long getMaxDistrictNo()	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(DistrictNo)+1 AS DistrictNo FROM District");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxDistrictNo successful");
			if (maxId==null) {
				return 1;
			}
			return maxId.longValue();
		}catch (RuntimeException re) {
			log.error("getMaxDistrictNo failed", re);
			throw re;
		}
	}
	
	
	public List<District> checkDupDistrict(long regionNo, String thaiName, long provinceNo, long districtNo){
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(District.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.not(Restrictions.eq("districtNo", districtNo)));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("checkDupDistrict failed", re);
			throw re;
		}
		
	}
	
	
	public boolean updateDistrict(District district, long prevRegionNo, long prevProvinceNo)	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("update District set RegionNo=?, ProvinceNo=?, ThaiName=?, EngName=?, LastUpdateDate=?, LastUpdateBy=? where DistrictNo=? and RegionNo=? and ProvinceNo=?");
			query.setLong(0, district.getRegionNo());
			query.setLong(1, district.getProvinceNo());
			query.setString(2, district.getThaiName());
			query.setString(3, district.getEngName());
			query.setDate(4, district.getLastUpdateDate());
			query.setString(5, district.getLastUpdateBy());
			query.setLong(6, district.getDistrictNo());
			query.setLong(7, prevRegionNo);
			query.setLong(8, prevProvinceNo);
			if(query.executeUpdate() >0) {
				return true;
			}else{
				return false;
			}
		}catch (RuntimeException re) {
			log.error("updateDistrict failed", re);
			throw re;
		}
	}
	
}
