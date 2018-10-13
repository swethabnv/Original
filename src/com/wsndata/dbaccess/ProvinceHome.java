package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Branch;
import com.wsndata.data.Province;

public class ProvinceHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(ProvinceHome.class);
	public List<Province> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Province> retrieveByRegionNo(long regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("retrieveByRegionNo failed", re);
			throw re;
		}
	}
	
	
	public List<Province> retrieveByRegionNoList(List<Long> regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			crit.add(Restrictions.in("regionNo", regionNo));
			crit.addOrder(Order.asc("thaiName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("retrieveByRegionNoList failed", re);
			throw re;
		}
	}
	
	/*
	public List searchByProvinceNo(long provinceNo) {
		try {			
			StringBuffer strSQL = new StringBuffer();
			strSQL.append( "select * from Province" );
			if(provinceNo > 0)
				strSQL.append(" where ProvinceNo = '" + provinceNo + "' ") ;
				
			Query searchCriteria = sessionFactory.getCurrentSession().createSQLQuery(strSQL.toString()).addEntity(Province.class);			
			List results = searchCriteria.list();
			log.debug("searchByProvinceNo successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("searchByProvinceNo failed", re);
			throw re;
		}
	}*/
	
	public List searchByName(String provinceName) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Province ");
			if(!"".equals(provinceName)){
				sqlQuery.append("WHERE ThaiName LIKE '%" + provinceName + "%' OR EngName LIKE '%" + provinceName + "%' ");
			}
			sqlQuery.append("ORDER BY ThaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Province.class);			
		    log.debug("searchByName successful"); 		    
		    return query.list();
		}catch (RuntimeException re) {
			log.error("searchByName failed", re);
			throw re;
		}
	}
	
	public Province searchByKey(long regionNo, long provinceNo){
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			return (Province) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("searchByKey failed", re);
			throw re;
		}
		
	}
	
	public List<Province> findByCriteria(long regionNo, String thaiName){
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			if(regionNo > 0)
    			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			 return crit.list();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
		
	}
	
	public List searchByCriteria(long regionNo, String thaiName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r.RegionNo AS regionNo");
			sqlQuery.append(" ,r.RegionName AS regionName");
			sqlQuery.append(" ,p.ProvinceNo AS provinceNo");
			sqlQuery.append(" ,p.ThaiName AS thaiName");
		    sqlQuery.append(" ,p.EngName AS engName");
			sqlQuery.append(" FROM Province AS p");
		    sqlQuery.append(" LEFT JOIN Region AS r ON p.RegionNo = r.RegionNo");
		    sqlQuery.append(" WHERE r.RegionNo IS NOT NULL");
		    if (regionNo>0) {
		    	sqlQuery.append(" AND r.RegionNo=:regionNo");
			}
		    if (!"".equals(thaiName)) {
		    	sqlQuery.append(" AND ( p.ThaiName LIKE :thaiName OR p.EngName LIKE :thaiName )");
			}
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if (regionNo>0) {
				query.setLong("regionNo", regionNo);
			}
			if (!"".equals(thaiName)) {
				query.setString("thaiName", "%"+thaiName+"%");
			}
			sqlQuery.append("ORDER BY thaiName ASC");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}	
	}
	
	public int getMaxProvinceNo()	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(ProvinceNo) AS ProvinceNo FROM Province");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxProvinceNo successful"); 
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getMaxProvinceNo failed", re);
			throw re;
		}
	}
	
	
	public long getRegionNo(long provinceNo) {
		try {
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);			
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			Province prov = (Province)crit.uniqueResult();
			return prov.getRegionNo();
		} catch (RuntimeException re) {
			log.error("getRegionNo failed", re);
			throw re;
		}
	}
	
	public Province searchByProvinceNo(long provinceNo) {
		try {
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);			
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			return (Province) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("searchByProvinceNo failed", re);
			throw re;
		}
	}
	
	public Province findByName(String provinceName) {
		try {
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);			
			crit.add(Restrictions.eq("thaiName", provinceName));
			return (Province) crit.uniqueResult();
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
			sqlQuery.append(" ,p.ThaiName AS thaiName");
		    sqlQuery.append(" ,p.EngName AS engName");
			sqlQuery.append(" FROM Province AS p");
		    sqlQuery.append(" LEFT JOIN Region AS r ON p.RegionNo = r.RegionNo");
		    sqlQuery.append(" ORDER BY thaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchAll failed", re);
			throw re;
		}
		
	}
	
	public List<Province> searchByBranch(List branch)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			String pCode = "";
			for (int i = 0; i < branch.size(); i++) {
				if (i+1==branch.size()) {
					pCode += (((Branch)branch.get(i)).getProvinceNo());
					break;
				}
				pCode += (((Branch)branch.get(i)).getProvinceNo())+",";
			}
			sqlQuery.append(" SELECT *");
			sqlQuery.append(" FROM Province ");
		    
		    if (!"".equals(pCode)) {
		    	sqlQuery.append(" WHERE ProvinceNo IN (" + pCode + ")");
			}
		    sqlQuery.append("ORDER BY ThaiName ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Province.class);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}	
	}
	
	
	public long getMaxId() 
	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(ProvinceNo)+1 AS maxId FROM Province");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getMaxId successful"); 
			if (maxId==null) {
				return 1;
			}
			return maxId.longValue();
		}catch (RuntimeException re) {
			log.error("getMaxId failed", re);
			throw re;
		}
	}
	
	public List<Province> checkDupProvince(long regionNo, String thaiName, long provinceNo){
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			crit.add(Restrictions.eq("thaiName", thaiName));
			if(provinceNo>0) {
				crit.add(Restrictions.not(Restrictions.eq("provinceNo", provinceNo)));
			}
			return crit.list();
		} catch (RuntimeException re) {
			log.error("checkDupProvince failed", re);
			throw re;
		}
		
	}
	
	
	public boolean updateProvince(Province province)	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("update Province set RegionNo=?, ThaiName=?, EngName=?, LastUpdateDate=?, LastUpdateBy=? where ProvinceNo=?");
			query.setLong(0, province.getRegionNo());
			query.setString(1, province.getThaiName());
			query.setString(2, province.getEngName());
			query.setDate(3, province.getLastUpdateDate());
			query.setString(4, province.getLastUpdateBy());
			query.setLong(5, province.getProvinceNo());
			
			if(query.executeUpdate() >0) {
				return true;
			}else{
				return false;
			}
		}catch (RuntimeException re) {
			log.error("updateProvince failed", re);
			throw re;
		}
	}
	
	public List<Province> searchByProvinceNoList(List<String> provinceNo) {
		try {
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Province.class);			
			crit.add(Restrictions.in("provinceNo", provinceNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByProvinceNo failed", re);
			throw re;
		}
	}
}
