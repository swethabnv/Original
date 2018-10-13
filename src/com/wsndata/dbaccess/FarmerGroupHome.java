package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerGroup;

public class FarmerGroupHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(FarmerGroupHome.class);
	public List<FarmerGroup> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.addOrder(Order.asc("farmerGroupName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> findGroup(String groupType)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.addOrder(Order.asc("farmerGroupName"));
			crit.add(Restrictions.eq("farmerGroupType", groupType));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findGroup failed", re);
			throw re;
		}
	}
	
	public FarmerGroup findByFarmerGroupId(long farmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return (FarmerGroup) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupId failed", re);
			throw re;
		}
	}
	
	
	public String getByCooperativeId(long farmerGroupId) 
	{
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT FarmerGroupName FROM FarmerGroup where FarmerGroupId=? and FarmerGroupType = 'C'");
			
			query.setLong(0, farmerGroupId);
			
			String cooperativeId = (String)query.uniqueResult();
			log.debug("getByCooperativeId successful");
			
			return cooperativeId;
		} catch (RuntimeException re) {
			log.error("getByCooperativeId failed", re);
			throw re;
		}
	}
	
	
	
	public FarmerGroup findByFarmerGroupName(String farmerGroupName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.add(Restrictions.eq("farmerGroupName", farmerGroupName));
			return (FarmerGroup) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupName failed", re);
			throw re;
		}
	}
	
	public FarmerGroup findByFarmerGroupNameAndAddress(String farmerGroupName,String addressNo,long subDistrictNo,long districtNo,long provinceNo,String groupType) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM FarmerGroup fg ");
			sqlQuery.append("LEFT JOIN farmergroupaddress fgAddr ON fg.FarmerGroupId = fgAddr.FarmerGroupId ");
			sqlQuery.append("WHERE fg.FarmerGroupType = :groupType ");
			if(!"".equals(farmerGroupName))
				sqlQuery.append("AND fg.farmerGroupName = :farmerGroupName "); 
			if(!"".equals(addressNo))
				sqlQuery.append("AND fg.AddressNo = :addressNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND fgAddr.ProvinceNo = :provinceNo ");
			if(districtNo > 0)
				sqlQuery.append("AND fgAddr.DistrictNo = :districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND fgAddr.SubDistrictNo = :subDistrictNo ");
		    sqlQuery.append(" ORDER BY FarmerGroupName ASC ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			query.setString("groupType", groupType);
			if(!"".equals(farmerGroupName))
				query.setString("farmerGroupName", farmerGroupName);
			if(!"".equals(addressNo))
				query.setString("addressNo", addressNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			return (FarmerGroup)query.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupNameAndAddress failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> findByFarmerGroupType(String farmerGroupType) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.add(Restrictions.eq("farmerGroupType", farmerGroupType));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupType failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> searchByFarmerGroupName(String farmerGroupName,List branch) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM FarmerGroup");
		    sqlQuery.append(" WHERE FarmerGroupName LIKE ? ORDER BY FarmerGroupName ASC ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			//query.setString(0, bCode);
			query.setString(0, "%"+farmerGroupName+"%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByFarmerGroupName failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> getFarmerGroupId(long branchCode){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroup.class);
			crit.add(Restrictions.eq("branchCode", branchCode));
			crit.addOrder(Order.asc("farmerGroupName"));// order
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getFarmerGroupId failed", re);
			throw re;
		}
		
	}
	
	public List<FarmerGroup> searchByCriteria(String farmerGroupType, String farmerGroupName, String cooperativeType, long regionNo, long provinceNo, long districtNo, long subDistrictNo, long farmerGroupId, String userLogin, long cooperativeGroupId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct fs.* FROM (");
			
			sqlQuery.append("SELECT f.FarmerGroupId, f.FarmerGroupName, fg.FarmerGroupName As CooperativeName, ");
			sqlQuery.append("pd.provinceThai As provinceName, pd.districtThai As districtName, pd.subDistrictThai As subDistrictName, ");
			if(farmerGroupType.equals("F")){
				sqlQuery.append("(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE f.FarmerGroupId = ff.FarmerGroupId), ");
			} else {
				sqlQuery.append("(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE ff.FarmerGroupId IN (SELECT fc.FarmerGroupId FROM farmergroupchild fc WHERE fc.ChildFarmerGroupId=f.FarmerGroupId))");
				sqlQuery.append("+(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE ff.FarmerGroupId=f.FarmerGroupId) as numId, ");
			}
			sqlQuery.append("f.Target FROM FarmerGroup As f ");
			sqlQuery.append("LEFT JOIN FarmerGroupAddress As a ON f.FarmerGroupId = a.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN FarmerGroupChild AS fc ON f.FarmerGroupId = fc.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN provincedistrict pd ON pd.RegionNo= a.RegionNo AND pd.ProvinceNo=a.ProvinceNo ");
			sqlQuery.append("AND pd.DistrictNo= a.DistrictNo AND pd.SubDistrictNo = a.SubDistrictNo ");

			sqlQuery.append("LEFT JOIN FarmerGroup AS fg ON fc.ChildFarmerGroupId = fg.FarmerGroupId ");
			String prov = "pd";
			if(farmerGroupType.equals("F")) {
				sqlQuery.append("LEFT JOIN FarmerGroupAddress As fa ON fg.FarmerGroupId = fa.FarmerGroupId ");
				sqlQuery.append("LEFT JOIN provincedistrict pd2 ON pd2.RegionNo= fa.RegionNo AND pd2.ProvinceNo=fa.ProvinceNo ");
				sqlQuery.append("AND pd2.DistrictNo= fa.DistrictNo AND pd2.SubDistrictNo = fa.SubDistrictNo ");
				prov = "pd2";
			}
			sqlQuery.append("WHERE f.FarmerGroupType=:farmerGroupType ");
			if(farmerGroupId > 0) {
				if(farmerGroupType.equals("F")) {
					sqlQuery.append("AND fg.FarmerGroupId=:farmerGroupId "); 
				} else {
					sqlQuery.append("AND f.FarmerGroupId=:farmerGroupId "); 
				}
			}
			if(regionNo > 0)
				sqlQuery.append("AND "+prov+".regionNo=:regionNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND "+prov+".provinceNo=:provinceNo "); 
			if(districtNo > 0)
				sqlQuery.append("AND "+prov+".districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND "+prov+".subDistrictNo=:subDistrictNo ");
			if(!"".equals(farmerGroupName))
				sqlQuery.append("AND f.farmerGroupName LIKE :farmerGroupName "); 

			if(cooperativeGroupId>0)
				sqlQuery.append("AND fg.FarmerGroupId =:cooperativeGroupId ");
			if(!"".equals(cooperativeType)) {
				if("Cooperative".equals(cooperativeType))
					sqlQuery.append("AND fg.FarmerGroupName IS NOT NULL ");
				else
					sqlQuery.append("AND fg.FarmerGroupName IS NULL ");
			}
			 
			sqlQuery.append("UNION ");

			sqlQuery.append("SELECT f.FarmerGroupId, f.FarmerGroupName, fg.FarmerGroupName As CooperativeName, ");
			sqlQuery.append("'' As provinceName, '' As districtName, '' As subDistrictName, ");
			if(farmerGroupType.equals("F")){
				sqlQuery.append("(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE f.FarmerGroupId = ff.FarmerGroupId), ");
			} else {
				sqlQuery.append("(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE ff.FarmerGroupId IN (SELECT fc.FarmerGroupId FROM farmergroupchild fc WHERE fc.ChildFarmerGroupId=f.FarmerGroupId))");
				sqlQuery.append("+(SELECT count(ff.FarmerGroupId) FROM FarmerGroupFarmer As ff WHERE ff.FarmerGroupId=f.FarmerGroupId) as numId, ");
			}
			sqlQuery.append("f.Target FROM FarmerGroup As f ");
			sqlQuery.append("LEFT JOIN FarmerGroupChild AS fc ON f.FarmerGroupId = fc.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN FarmerGroup AS fg ON fc.ChildFarmerGroupId = fg.FarmerGroupId ");
			sqlQuery.append("WHERE  f.FarmerGroupType=:farmerGroupType AND f.farmergroupid NOT IN ");
			sqlQuery.append("(SELECT fa.farmergroupid FROM farmergroupaddress fa)");
			if(cooperativeGroupId>0)
				sqlQuery.append("AND fg.FarmerGroupId =:cooperativeGroupId ");
			sqlQuery.append("AND f.CreateBy =:userLogin ");
			if(!"".equals(farmerGroupName))
				sqlQuery.append("AND f.farmerGroupName LIKE :farmerGroupName "); 
			if(!"".equals(cooperativeType)) {
				if("Cooperative".equals(cooperativeType))
					sqlQuery.append("AND fg.FarmerGroupName IS NOT NULL ");
				else
					sqlQuery.append("AND fg.FarmerGroupName IS NULL ");
			}
			sqlQuery.append(") fs GROUP BY fs.FarmerGroupId ORDER BY fs.FarmerGroupName");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("farmerGroupType", farmerGroupType);
			if(farmerGroupId > 0)
				query.setLong("farmerGroupId", farmerGroupId);
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			if(!"".equals(farmerGroupName))
				query.setString("farmerGroupName", "%"+farmerGroupName+"%");
			query.setString("userLogin", userLogin);
			if(cooperativeGroupId>0)
				query.setLong("cooperativeGroupId", cooperativeGroupId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> searchByAnauthorize(String farmerGroupType, long regionNo, long provinceNo, long districtNo, long subDistrictNo, long farmerGroupId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct fs.* FROM (");
			
			sqlQuery.append("SELECT f.* FROM FarmerGroup As f ");
			sqlQuery.append("LEFT JOIN FarmerGroupAddress As a ON f.FarmerGroupId = a.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN FarmerGroupChild AS fc ON f.FarmerGroupId = fc.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN provincedistrict pd ON pd.RegionNo= a.RegionNo AND pd.ProvinceNo=a.ProvinceNo ");
			sqlQuery.append("AND pd.DistrictNo= a.DistrictNo AND pd.SubDistrictNo = a.SubDistrictNo ");

			sqlQuery.append("LEFT JOIN FarmerGroup AS fg ON fc.ChildFarmerGroupId = fg.FarmerGroupId ");
			String prov = "pd";
			if(farmerGroupType.equals("F")) {
				sqlQuery.append("LEFT JOIN FarmerGroupAddress As fa ON fg.FarmerGroupId = fa.FarmerGroupId ");
				sqlQuery.append("LEFT JOIN provincedistrict pd2 ON pd2.RegionNo= fa.RegionNo AND pd2.ProvinceNo=fa.ProvinceNo ");
				sqlQuery.append("AND pd2.DistrictNo= fa.DistrictNo AND pd2.SubDistrictNo = fa.SubDistrictNo ");
				prov = "pd2";
			}
			sqlQuery.append("WHERE f.FarmerGroupType=:farmerGroupType ");
			if(farmerGroupId > 0) {
				if(farmerGroupType.equals("F")) {
					sqlQuery.append("AND fg.FarmerGroupId=:farmerGroupId "); 
				} else {
					sqlQuery.append("AND f.FarmerGroupId=:farmerGroupId "); 
				}
			}
			if(regionNo > 0)
				sqlQuery.append("AND "+prov+".regionNo=:regionNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND "+prov+".provinceNo=:provinceNo "); 
			if(districtNo > 0)
				sqlQuery.append("AND "+prov+".districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND "+prov+".subDistrictNo=:subDistrictNo ");
			 
			sqlQuery.append(") fs GROUP BY fs.FarmerGroupId ORDER BY fs.FarmerGroupName");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			query.setString("farmerGroupType", farmerGroupType);
			if(farmerGroupId > 0)
				query.setLong("farmerGroupId", farmerGroupId);
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByAnauthorize failed", re);
			throw re;
		}
	}
	
	
	
	public List<FarmerGroup> searchByAnauthorizeFilteredByCriteria(String farmerGroupType, List<String> regionNo, List<String> provinceNo, long districtNo, long subDistrictNo, List<String> farmerGroupId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct fs.* FROM (");
			
			sqlQuery.append("SELECT f.* FROM FarmerGroup As f ");
			sqlQuery.append("LEFT JOIN FarmerGroupAddress As a ON f.FarmerGroupId = a.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN FarmerGroupChild AS fc ON f.FarmerGroupId = fc.FarmerGroupId ");
			sqlQuery.append("LEFT JOIN provincedistrict pd ON pd.RegionNo= a.RegionNo AND pd.ProvinceNo=a.ProvinceNo ");
			sqlQuery.append("AND pd.DistrictNo= a.DistrictNo AND pd.SubDistrictNo = a.SubDistrictNo ");

			sqlQuery.append("LEFT JOIN FarmerGroup AS fg ON fc.ChildFarmerGroupId = fg.FarmerGroupId ");
			String prov = "pd";
			if(farmerGroupType.equals("F")) {
				sqlQuery.append("LEFT JOIN FarmerGroupAddress As fa ON fg.FarmerGroupId = fa.FarmerGroupId ");
				sqlQuery.append("LEFT JOIN provincedistrict pd2 ON pd2.RegionNo= fa.RegionNo AND pd2.ProvinceNo=fa.ProvinceNo ");
				sqlQuery.append("AND pd2.DistrictNo= fa.DistrictNo AND pd2.SubDistrictNo = fa.SubDistrictNo ");
				prov = "pd2";
			}
			sqlQuery.append("WHERE f.FarmerGroupType=:farmerGroupType ");
			if(farmerGroupId != null && farmerGroupId.size() > 0) {
				if(farmerGroupType.equals("F")) {
					sqlQuery.append("AND fg.FarmerGroupId=:farmerGroupId "); 
				} else {
					if (farmerGroupId.size() != 1 && !farmerGroupId.get(0).equals("0"))
						sqlQuery.append("AND f.FarmerGroupId in (:farmerGroupId) "); 
				}
			}
			if(regionNo != null && regionNo.size() > 0)
				sqlQuery.append("AND "+prov+".regionNo in (:regionNo) "); 
			if(provinceNo != null && provinceNo.size() > 0)
				sqlQuery.append("AND "+prov+".provinceNo in (:provinceNo) "); 
			if(districtNo > 0)
				sqlQuery.append("AND "+prov+".districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND "+prov+".subDistrictNo=:subDistrictNo ");
			 
			sqlQuery.append(") fs GROUP BY fs.FarmerGroupId ORDER BY fs.FarmerGroupName");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			query.setString("farmerGroupType", farmerGroupType);
			if(farmerGroupId != null && farmerGroupId.size() > 0)
				if(farmerGroupType.equals("F")) {
					query.setString("farmerGroupId", farmerGroupId.get(0));
				} else {
					if (farmerGroupId.size() != 1 && !farmerGroupId.get(0).equals("0"))
						query.setParameterList("farmerGroupId", farmerGroupId);
				}
			if(regionNo != null && regionNo.size() > 0)
				query.setParameterList("regionNo", regionNo);
			if(provinceNo != null && provinceNo.size() > 0)
				query.setParameterList("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByAnauthorizeFilteredByCriteria failed", re);
			throw re;
		}
	}
	
	
	public List<FarmerGroup> findByProvinceNo(long provinceNo) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM FarmerGroup fg ");
			sqlQuery.append("LEFT JOIN farmergroupaddress fgAddr ON fg.FarmerGroupId = fgAddr.FarmerGroupId ");
			sqlQuery.append("WHERE fg.FarmerGroupId IS NOT NULL ");
			sqlQuery.append("AND fg.FarmerGroupType = 'C' ");
			if(provinceNo > 0)
				sqlQuery.append("AND fgAddr.ProvinceNo = :provinceNo ");
		    sqlQuery.append(" ORDER BY FarmerGroupName ASC ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroup> findByCooperativeId(long cooperativeId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT fgChild.ChildFarmerGroupId,fg.* FROM farmergroupchild fgChild ");
			sqlQuery.append("LEFT JOIN farmergroup fg ON fg.FarmerGroupId = fgChild.FarmerGroupId ");
			sqlQuery.append("WHERE fgChild.FarmerGroupId IS NOT NULL ");
			if(cooperativeId > 0)
				sqlQuery.append("AND fgChild.ChildFarmerGroupId = :cooperativeId ");
		    sqlQuery.append(" ORDER BY FarmerGroupName ASC ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroup.class);
			if(cooperativeId > 0)
				query.setLong("cooperativeId", cooperativeId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	public void deleteFarmerGroup(long farmerGroupId)	{
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("delete from FarmerGroup where farmerGroupId=:farmerGroupId");
			uQuery.setLong("farmerGroupId", farmerGroupId);
			uQuery.executeUpdate();
			log.info("deleteFarmerGroup successful");
		}catch (RuntimeException re) {
			log.error("deleteFarmerGroup failed", re);
			throw re;
		}
	}
}
