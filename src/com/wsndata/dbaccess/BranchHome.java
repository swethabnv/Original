package com.wsndata.dbaccess;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Branch;

public class BranchHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(BranchHome.class);
	
	public List<Branch> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Branch> findByRegionNo(long regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	public List<Branch> findByProvinceNo(long provinceNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<Branch> findByPBranch(long pbranchCode)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.add(Restrictions.eq("pbranchCode", pbranchCode));
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByPBranch failed", re);
			throw re;
		}
	}
	
	public Branch findByBranchCode(long branchCode) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.add(Restrictions.eq("branchCode", branchCode));
			return (Branch) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBranchCode failed", re);
			throw re;
		}
	}
	
	public Branch findByBranchNameAndAddress(long branchCode,String branchName, String address, long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);
			crit.add(Restrictions.not(Restrictions.eq("branchCode", branchCode)));
			crit.add(Restrictions.eq("branchName", branchName));
			crit.add(Restrictions.eq("address", address));
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			return (Branch) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBranchName failed", re);
			throw re;
		}
	}
	
	public boolean findbyTable(String table,long branchCode)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM "+table);
			sqlQuery.append(" WHERE branchCode = ?");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, branchCode);
			if (query.list().size()==0) {
				return true;
			}else {
				return false;
			}
		} catch (RuntimeException re) {
			log.error("findbyTable failed", re);
			throw re;
		}
		
	}
	
	public boolean findbyPBranch(long branchCode)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM Branch");
			sqlQuery.append(" WHERE pbranchCode = ?");
			sqlQuery.append(" AND branchCode <> ? ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, branchCode);
			query.setLong(1, branchCode);
			if (query.list().size()==0) {
				return true;
			}else {
				return false;
			}
		} catch (RuntimeException re) {
			log.error("findbyPBranch failed", re);
			throw re;
		}
		
	}
	
	public List<Branch> searchByCriteria(String branchName, String provinceName, String districtName, String subDistrictName,List branch)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			String bCode = "";
			for (int i = 0; i < branch.size(); i++) {
				if (i+1==branch.size()) {
					bCode += (((Branch)branch.get(i)).getBranchCode());
					break;
				}
				bCode += (((Branch)branch.get(i)).getBranchCode())+",";
			}
			
			sqlQuery.append("SELECT branch.BranchName AS branchName");
			sqlQuery.append(", branch.BranchCode AS branchCode");
			sqlQuery.append(", branch.PBranchCode AS pbranchCode");
			sqlQuery.append(", (SELECT pbranch.BranchName FROM Branch AS pbranch WHERE pbranch.BranchCode = branch.pbranchCode) AS pbranchName");
			sqlQuery.append(", branch.Address AS address");
			sqlQuery.append(", branch.Tel AS tel");
			sqlQuery.append(", branch.Fax AS fax");
			sqlQuery.append(", province.ThaiName AS provinceName");
			sqlQuery.append(", district.ThaiName AS districtName");
			sqlQuery.append(", subDistrict.ThaiName AS subDistrictName");
			sqlQuery.append(", branch.RegionNo AS regionNo");
			sqlQuery.append(", branch.ProvinceNo AS provinceNo");
			sqlQuery.append(", branch.DistrictNo AS districtNo");
			sqlQuery.append(", branch.SubDistrictNo AS subDistrictNo");
			sqlQuery.append(" FROM Branch AS branch");
			sqlQuery.append(" left join District AS district");
			sqlQuery.append(" on branch.DistrictNo = district.DistrictNo");
			sqlQuery.append(" left join SubDistrict AS subDistrict");
			sqlQuery.append(" on branch.SubDistrictNo = subDistrict.SubDistrictNo");
			sqlQuery.append(" left join Province AS province");
			sqlQuery.append(" on branch.ProvinceNo = province.ProvinceNo");
			sqlQuery.append(" WHERE branch.BranchCode IN("+bCode+")");
			if(!"".equals(branchName))
				sqlQuery.append(" AND branch.BranchName LIKE :branchName");
			if(!"".equals(provinceName))
				sqlQuery.append(" AND province.ThaiName LIKE :provinceName");
			if(!"".equals(districtName))
				sqlQuery.append(" AND district.ThaiName LIKE :districtName");
			if(!"".equals(subDistrictName))
				sqlQuery.append(" AND subDistrict.ThaiName LIKE :subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(branchName))
				query.setString("branchName", branchName+"%");
			if(!"".equals(provinceName))
				query.setString("branchName", provinceName+"%");
			if(!"".equals(districtName))
				query.setString("districtName", districtName+"%");
			if(!"".equals(subDistrictName))
				query.setString("subDistrictName", subDistrictName+"%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public List<Branch> searchByCriteria2(String branchName, long regionNo, long provinceNo, long districtNo, long subDistrictNo)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("SELECT branch.BranchName AS branchName");
			sqlQuery.append(", branch.BranchCode AS branchCode");
			sqlQuery.append(", branch.PBranchCode AS pbranchCode");
			sqlQuery.append(", (SELECT pbranch.BranchName FROM Branch AS pbranch WHERE pbranch.BranchCode = branch.pbranchCode) AS pbranchName");
			sqlQuery.append(", branch.Address AS address");
			sqlQuery.append(", branch.Tel AS tel");
			sqlQuery.append(", branch.Fax AS fax");
			sqlQuery.append(", province.ThaiName AS provinceName");
			sqlQuery.append(", district.ThaiName AS districtName");
			sqlQuery.append(", subDistrict.ThaiName AS subDistrictName");
			sqlQuery.append(", branch.RegionNo AS regionNo");
			sqlQuery.append(", branch.ProvinceNo AS provinceNo");
			sqlQuery.append(", branch.DistrictNo AS districtNo");
			sqlQuery.append(", branch.SubDistrictNo AS subDistrictNo");
			sqlQuery.append(" FROM Branch AS branch");
			sqlQuery.append(" left join Region AS region on branch.RegionNo = region.RegionNo");
			sqlQuery.append(" left join Province AS province on branch.ProvinceNo = province.ProvinceNo");
			sqlQuery.append(" left join District AS district on branch.DistrictNo = district.DistrictNo");
			sqlQuery.append(" left join SubDistrict AS subDistrict on branch.SubDistrictNo = subDistrict.SubDistrictNo");
			sqlQuery.append(" WHERE branch.BranchCode is NOT NULL");
			if(!"".equals(branchName))
				sqlQuery.append(" AND branch.BranchName LIKE :branchName");
			if(regionNo != 0)
				sqlQuery.append(" AND region.regionNo = :regionNo");
			if(provinceNo != 0)
				sqlQuery.append(" AND province.ProvinceNo = :provinceNo");
			if(districtNo !=0)
				sqlQuery.append(" AND district.DistrictNo = :districtNo");
			if(subDistrictNo !=0)
				sqlQuery.append(" AND subDistrict.SubDistrictNo = :subDistrictNo");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(branchName))
				query.setString("branchName", "%"+branchName+"%");
			if(regionNo != 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo != 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo !=0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo !=0)
				query.setLong("subDistrictNo", subDistrictNo);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria2 failed", re);
			throw re;
		}
	}
	
	public int getSeqbyPbranchCode(long PbranchCode) 
	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(SEQ) AS Seq FROM Branch WHERE PbranchCode = '"+PbranchCode+"'");
			BigDecimal maxId = (BigDecimal)query.uniqueResult();
			log.debug("getSeqbyPbranchCode successful"); 
			if (maxId==null) {
				return 0;
			}
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getSeqbyPbranchCode failed", re);
			throw re;
		}
	}
	
	public int getBranchCode() 
	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(BranchCode) AS branchCode FROM Branch");
			BigInteger maxId = (BigInteger)query.uniqueResult();
			log.debug("getBranchCode successful"); 
			if (maxId==null) {
				return 0;
			}
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getBranchCode failed", re);
			throw re;
		}
	}
	
	public List<Branch> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<Branch> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<Branch> findByRegionAndProvinceAndDistrictAndSubDistrict(long regionNo, long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Branch.class);	
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

	public List searchByAll()
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append("SELECT b.branchCode,b.branchName,b.address,pd.provinceThai,");
			sqlQuery.append("pd.districtThai,pd.subDistrictThai FROM branch b ");
			sqlQuery.append("LEFT JOIN provincedistrict pd ON pd.SubDistrictNo = b.SubDistrictNo");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByAll failed", re);
			throw re;
		}
	}
	
	public List searchByNotIn(long bankId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();

			sqlQuery.append("SELECT b.branchCode,b.branchName,b.address,pd.provinceThai,");
			sqlQuery.append("pd.districtThai,pd.subDistrictThai FROM branch b LEFT JOIN provincedistrict pd ");
			sqlQuery.append("ON pd.SubDistrictNo = b.SubDistrictNo WHERE BranchCode ");
		    sqlQuery.append("not in(select bankbranch.BranchCode from bankbranch where BankId=?)");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, bankId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByNotIn failed", re);
			throw re;
		}
	}
	
	public List searchByIn(long bankId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();

			sqlQuery.append("SELECT b.branchCode,b.branchName,b.address,pd.provinceThai,");
			sqlQuery.append("pd.districtThai,pd.subDistrictThai FROM branch b LEFT JOIN provincedistrict pd ");
			sqlQuery.append("ON pd.SubDistrictNo = b.SubDistrictNo WHERE BranchCode ");
		    sqlQuery.append("in(select bankbranch.BranchCode from bankbranch where BankId=?)");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, bankId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByIn failed", re);
			throw re;
		}
	}
	
}
