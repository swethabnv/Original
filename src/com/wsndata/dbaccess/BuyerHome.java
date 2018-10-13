package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Buyer;

public class BuyerHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(BuyerHome.class);
	public List<Buyer> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);
			crit.addOrder(Order.asc("buyerId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	public List<Buyer> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<Buyer> searchByBuyerName(String buyerName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);
			if(!"".equals(buyerName))
				crit.add(Restrictions.like("buyerName", buyerName, MatchMode.START));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByBuyerName failed", re);
			throw re;
		}
	}
	
	public Buyer findByBuyerId(long buyerId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("buyerId", buyerId));	
			return (Buyer)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBuyerId failed", re);
			throw re;
		}
	}
	
	public List searchByBuyerId(long buyerId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT b.BuyerId AS buyerId");
			sqlQuery.append(", b.BuyerName AS buyerName");
			sqlQuery.append(", b.AddressNo AS addressNo");
			sqlQuery.append(", b.Moo AS moo");
			sqlQuery.append(", b.Soi As soi");
			sqlQuery.append(", b.Road As road");
			sqlQuery.append(", b.ProvinceNo AS provinceNo");
			sqlQuery.append(", b.DistrictNo AS districtNo");
			sqlQuery.append(", b.SubDistrictNo As subDistrictNo");
			sqlQuery.append(", prov.ThaiName AS provinceName");
			sqlQuery.append(", dist.ThaiName AS districtName");
			sqlQuery.append(", sub.ThaiName As subDistrictName");
			sqlQuery.append(" FROM Buyer AS b");
		    sqlQuery.append(" LEFT JOIN Province AS prov ON b.ProvinceNo = prov.ProvinceNo");
		    sqlQuery.append(" LEFT JOIN District AS dist ON b.DistrictNo = dist.DistrictNo");
		    sqlQuery.append(" LEFT JOIN SubDistrict AS sub ON b.SubDistrictNo = sub.SubDistrictNo");
		    sqlQuery.append(" WHERE b.BuyerId=:buyerId");
		    Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("buyerId", buyerId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}	
	}
	
	
	public Buyer findByBuyer(String buyerName,long provinceNo,long districtNo,long subDistrictNo,String address) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("buyerName", buyerName));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			crit.add(Restrictions.eq("addressNo", address));
			return (Buyer)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBuyer failed", re);
			throw re;
		}
	}
	
	
	public List<Buyer> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<Buyer> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<Buyer> findByRegionAndProvinceAndDistrictAndSubDistrict(long regionNo,long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Buyer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrictAndSubDistrict failed", re);
			throw re;
		}
	}
	
	public List<Buyer> findByCriteria(String buyerName, long regionNo, long provinceNo, long districtNo, long subDistrictNo)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("SELECT buyer.BuyerId AS buyerId");
			sqlQuery.append(", buyer.BuyerName AS buyerName");
			sqlQuery.append(", province.ThaiName AS provinceName");
			sqlQuery.append(", district.ThaiName AS districtName");
			sqlQuery.append(", subDistrict.ThaiName AS subDistrictName");
			sqlQuery.append(", buyer.ProvinceNo AS provinceNo");
			sqlQuery.append(", buyer.DistrictNo AS districtNo");
			sqlQuery.append(", buyer.SubDistrictNo AS subDistrictNo");
			sqlQuery.append(", buyer.Mobile AS mobile");
			sqlQuery.append(" FROM Buyer AS buyer");
			sqlQuery.append(" left join Region AS region on buyer.RegionNo = region.RegionNo");
			sqlQuery.append(" left join Province AS province on buyer.ProvinceNo = province.ProvinceNo");
			sqlQuery.append(" left join District AS district on buyer.DistrictNo = district.DistrictNo");
			sqlQuery.append(" left join SubDistrict AS subDistrict on buyer.SubDistrictNo = subDistrict.SubDistrictNo");
			sqlQuery.append(" WHERE buyer.BuyerId is NOT NULL");
			if(!"".equals(buyerName))
				sqlQuery.append(" AND buyer.BuyerName LIKE :buyerName");
			if(regionNo != 0)
				sqlQuery.append(" AND region.regionNo = :regionNo");
			if(provinceNo != 0)
				sqlQuery.append(" AND province.ProvinceNo = :provinceNo");
			if(districtNo != 0)
				sqlQuery.append(" AND district.DistrictNo = :districtNo");
			if(subDistrictNo != 0)
				sqlQuery.append(" AND subDistrict.SubDistrictNo = :subDistrictNo");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(buyerName))
				query.setString("buyerName", "%"+buyerName+"%");
			if(regionNo != 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo != 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo != 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo != 0)
				query.setLong("subDistrictNo", subDistrictNo);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
}
