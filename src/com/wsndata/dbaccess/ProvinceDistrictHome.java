package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.ProvinceDistrict;

public class ProvinceDistrictHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(ProvinceDistrictHome.class);

	public List<ProvinceDistrict> filterByRegionNo(long regionNo) 
	{
		try 
		{	
		    Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProvinceDistrict.class);
		    crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.addOrder(Order.asc("regionNo"));
			return crit.list();

		}catch (RuntimeException re) {
			log.error("filterByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<ProvinceDistrict> filterByRegionNoDistinct(long regionNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo,'' as SubDistrictNo, RegionName, ProvinceThai, ProvinceEng,"); 
			sqlQuery.append("'' as DistrictNo, '' as DistrictThai, '' as DistrictEng,'' as SubDistrictThai,'' as SubDistrictEng,'' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE RegionNo=? ORDER BY ProvinceThai ASC ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, regionNo);
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByProvinceNo failed", re);
			throw re;
		}
	}
	
	
	public List<ProvinceDistrict> filterByRegionNoDistinctByList(List<String> regionNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo,'' as SubDistrictNo, RegionName, ProvinceThai, ProvinceEng,"); 
			sqlQuery.append("'' as DistrictNo, '' as DistrictThai, '' as DistrictEng,'' as SubDistrictThai,'' as SubDistrictEng,'' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE RegionNo in (:regionNo) ORDER BY ProvinceThai ASC ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
		
			
			if(regionNo != null && regionNo.size() > 0)
				query.setParameterList("regionNo", regionNo);
			
			
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByProvinceNo failed", re);
			throw re;
		}
	}
	
	
	public List<ProvinceDistrict> filterByProvinceNo(long provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, '' as SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, '' as SubDistrictThai, '' as SubDistrictEng, '' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo=? ORDER BY DistrictThai ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, provinceNo);
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByProvinceNo failed", re);
			throw re;
		}
	}
	
	
	public List<ProvinceDistrict> filterByProvinceNoList(List<String>  provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, '' as SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, '' as SubDistrictThai, '' as SubDistrictEng, '' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo in (:provinceNo) ORDER BY ProvinceNo,DistrictThai ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	


			if(provinceNo == null || provinceNo.size() == 0)
				provinceNo.add("0");
			query.setParameterList("provinceNo", provinceNo);
			
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<ProvinceDistrict> filterByDistrictNo(long districtNo, long provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, SubDistrictThai, SubDistrictEng, PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo=? and DistrictNo=? ORDER BY SubDistrictThai ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, provinceNo);
			query.setLong(1, districtNo);
			return query.list();
		    
		}catch (RuntimeException re) {
			log.error("filterByDistrictNo failed", re);
			throw re;
		}
	}
	
	
	
	public List<ProvinceDistrict> filterByProvinceAndDistrictNo(long districtNo, List<String>  provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, SubDistrictThai, SubDistrictEng, PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo in (:provinceNo) and DistrictNo=:districtNo ORDER BY SubDistrictThai ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	

			if(provinceNo != null && provinceNo.size() > 0)
				query.setParameterList("provinceNo", provinceNo);

			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
				
			
			return query.list();
		    
		}catch (RuntimeException re) {
			log.error("filterByDistrictNo failed", re);
			throw re;
		}
	}
	
	
	public ProvinceDistrict getPostCode(long districtNo, long provinceNo, long subDistrictNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, SubDistrictThai, SubDistrictEng, PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo=? and DistrictNo=? and SubDistrictNo=? ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, provinceNo);
			query.setLong(1, districtNo);
			query.setLong(2, subDistrictNo);
			return (ProvinceDistrict)query.uniqueResult();
		    
		}catch (RuntimeException re) {
			log.error("getPostCode failed", re);
			throw re;
		}
	}
	
	//Add by Thanaput.s
	public List<ProvinceDistrict> selectByProvinceNo(long provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, '' as SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, '' as SubDistrictThai, '' as SubDistrictEng, '' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo=? GROUP BY ProvinceNo");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, provinceNo);
			return query.list();
		}catch (RuntimeException re) {
			log.error("selectByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<ProvinceDistrict> selectByDistrictNo(long districtNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, SubDistrictThai, SubDistrictEng, PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE DistrictNo=? GROUP BY DistrictNo");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, districtNo);
			return query.list();
		    
		}catch (RuntimeException re) {
			log.error("selectByDistrictNo failed", re);
			throw re;
		}
	}

	public List<ProvinceDistrict> selectBySubDistrictNo(long subDistrictNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, DistrictNo, SubDistrictNo, RegionName, ");
			sqlQuery.append("ProvinceThai, ProvinceEng, DistrictThai, DistrictEng, SubDistrictThai, SubDistrictEng, PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE SubDistrictNo=?");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	
			query.setLong(0, subDistrictNo);
			return query.list();
		    
		}catch (RuntimeException re) {
			log.error("selectBySubDistrictNo failed", re);
			throw re;
		}
	}
	
	public List<ProvinceDistrict> selectByProvinceNoList(List<String>  provinceNo) 
	{
		try 
		{	
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT RegionNo, ProvinceNo, RegionName, ProvinceThai, ProvinceEng, ");
			sqlQuery.append("'' as DistrictNo, '' as SubDistrictNo, '' as DistrictThai, '' as DistrictEng, '' as SubDistrictThai, '' as SubDistrictEng, '' as PostCode ");
			sqlQuery.append("FROM ProvinceDistrict WHERE ProvinceNo in (:provinceNo) ORDER BY RegionNo, ProvinceNo ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(ProvinceDistrict.class);	


			if(provinceNo == null || provinceNo.size() == 0)
				provinceNo.add("0");
			query.setParameterList("provinceNo", provinceNo);
			
			return query.list();
		}catch (RuntimeException re) {
			log.error("filterByProvinceNo failed", re);
			throw re;
		}
	}
}
