package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.SubDistrict;

public class SubDistrictHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(SubDistrictHome.class);
	public List<SubDistrict> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<SubDistrict> findByDistrictNo(long districtNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByDistrictNo failed", re);
			throw re;
		}
	}
	
	public int getMaxSubDistrictNo()	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(SubDistrictNo) AS SubDistrictNo FROM SubDistrict");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxSubDistrictNo successful"); 
			if(maxId==null) {
				return 0;
			}
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getMaxSubDistrictNo failed", re);
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
			sqlQuery.append(" ,d.ThaiName AS districtName");
			sqlQuery.append(" ,s.SubDistrictNo AS subDistrictNo");
		    sqlQuery.append(" ,s.ThaiName AS thaiName");
		    sqlQuery.append(" ,s.EngName AS engName");
			sqlQuery.append(" ,s.PostCode AS postCode");
			sqlQuery.append(" FROM SubDistrict AS s");
		    sqlQuery.append(" LEFT JOIN Region AS r ON s.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS p ON s.ProvinceNo = p.ProvinceNo");
		    sqlQuery.append(" LEFT JOIN District AS d ON s.DistrictNo = d.DistrictNo");
		    sqlQuery.append(" WHERE r.RegionNo <> 'null' AND p.ProvinceNo <> 'null' AND d.DistrictNo <> 'null'");
		    sqlQuery.append(" ORDER BY s.thaiName ASC");
		    Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchAll failed", re);
			throw re;
		}
		
	}
	
	
	public List searchByCriteria(long regionNo, long provinceNo, long districtNo, String thaiName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append(" SELECT r.RegionNo AS regionNo");
			sqlQuery.append(" ,r.RegionName AS regionName");
			sqlQuery.append(" ,p.ProvinceNo AS provinceNo");
			sqlQuery.append(" ,p.ThaiName AS provinceName");
			sqlQuery.append(" ,d.DistrictNo AS districtNo");
			sqlQuery.append(" ,d.ThaiName AS districtName");
			sqlQuery.append(" ,s.SubDistrictNo AS subDistrictNo");
		    sqlQuery.append(" ,s.ThaiName AS thaiName");
		    sqlQuery.append(" ,s.EngName AS engName");
			sqlQuery.append(" ,s.PostCode AS postCode");
			sqlQuery.append(" FROM SubDistrict AS s");
		    sqlQuery.append(" LEFT JOIN Region AS r ON s.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS p ON s.ProvinceNo = p.ProvinceNo");
		    sqlQuery.append(" LEFT JOIN District AS d ON s.DistrictNo = d.DistrictNo");
		    sqlQuery.append(" WHERE r.regionNo IS NOT NULL"); 
		    if(regionNo > 0) 
		    	 sqlQuery.append(" AND r.regionNo=:regionNo"); 
			if(provinceNo > 0)
				sqlQuery.append(" AND p.provinceNo=:provinceNo"); 
			if(districtNo > 0)
				sqlQuery.append(" AND d.districtNo=:districtNo"); 
			if(!"".equals(thaiName))
				sqlQuery.append(" AND ( s.thaiName LIKE :thaiName OR s.engName LIKE :thaiName )");
			
			sqlQuery.append(" ORDER BY s.thaiName ASC"); 
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(!"".equals(thaiName))
				query.setString("thaiName", "%"+thaiName+"%");
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
		
	}
	
	public SubDistrict findByKey(long regionNo, long provinceNo, long districtNo, long subDistrictNo){
		try{
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			return (SubDistrict)crit.uniqueResult();
		}catch (RuntimeException re) {
			log.error("findByKey failed", re);
			throw re;
		}
	}
	
	public SubDistrict findBySubDistrictNo(long SubDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.add(Restrictions.eq("subDistrictNo", SubDistrictNo));
			return (SubDistrict) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findBySubDistrictName failed", re);
			throw re;
		}
	}
	
	
	public SubDistrict findByCriteria(long regionNo, long provinceNo, long districtNo, String thaiName)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			return (SubDistrict)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public List<SubDistrict> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));		
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<SubDistrict> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<SubDistrict> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<SubDistrict> checkDupSubDistrict(long regionNo, String thaiName, long provinceNo, long districtNo, long subDistrictNo){
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SubDistrict.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.not(Restrictions.eq("subDistrictNo", subDistrictNo)));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("checkDupSubDistrict failed", re);
			throw re;
		}
		
	}
	
	public boolean updateSubDistrict(SubDistrict subDistrict, long prevRegionNo, long prevProvinceNo, long prevDistrictNo)	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("update SubDistrict set RegionNo=?, ProvinceNo=?, DistrictNo=?, ThaiName=?, EngName=?, LastUpdateDate=?, LastUpdateBy=? where SubDistrictNo=? ");
			query.setLong(0, subDistrict.getRegionNo());
			query.setLong(1, subDistrict.getProvinceNo());
			query.setLong(2, subDistrict.getDistrictNo());
			query.setString(3, subDistrict.getThaiName());
			query.setString(4, subDistrict.getEngName());
			query.setDate(5, subDistrict.getLastUpdateDate());
			query.setString(6, subDistrict.getLastUpdateBy());
			query.setLong(7, subDistrict.getSubDistrictNo());
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
