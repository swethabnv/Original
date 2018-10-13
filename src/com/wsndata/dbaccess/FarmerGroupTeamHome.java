package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerGroupTeam;

public class FarmerGroupTeamHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(FarmerGroupTeamHome.class);
	public List<FarmerGroupTeam> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupTeam.class);
			crit.addOrder(Order.asc("farmerGroupId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public FarmerGroupTeam findByFarmerGroupTeamId(long farmerGroupTeamId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupTeam.class);
			crit.add(Restrictions.eq("farmerGroupTeamId", farmerGroupTeamId));
			return (FarmerGroupTeam) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupTeamId failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupTeam> getByFarmerGroupId(long farmerGroupId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupTeam.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			//crit.addOrder(Order.asc("farmerGroupTeamId"));// order
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByFarmerGroupId failed", re);
			throw re;
		}
		
	}
	
	public List<FarmerGroupTeam> getTop3ByFarmerGroupId(long farmerGroupId){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupTeam.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			crit.setFetchSize(3);
			//crit.addOrder(Order.asc("farmerGroupTeamId"));// order
			return crit.list();
		} catch (RuntimeException re) {
			log.error("getByFarmerGroupId failed", re);
			throw re;
		}
		
	}
	
	public List<FarmerGroupTeam> searchByCriteria(String farmerGroupName, String cooperativeType, long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			
			String cooperative = "(Select fg.FarmerGroupName from FarmerGroupChild As fc LEFT JOIN FarmerGroup As fg ON fc.ChildFarmerGroupId = fg.FarmerGroupId where f.FarmerGroupId=fc.FarmerGroupId) ";
			sqlQuery.append("SELECT f.FarmerGroupId, f.FarmerGroupName, ");
			sqlQuery.append(cooperative+"As cooperativeName, ");
			sqlQuery.append("p.ThaiName As provinceName, d.ThaiName As districtName, s.ThaiName As subDistrictName, ");
			sqlQuery.append("(Select count(ff.FarmerGroupId) from FarmerGroupFarmer As ff where f.FarmerGroupId = ff.FarmerGroupId), ");
			sqlQuery.append("f.Target FROM FarmerGroup As f ");
			sqlQuery.append("LEFT JOIN FarmerGroupAddress As a ON f.FarmerGroupId = a.FarmerGroupId ");
		    sqlQuery.append("LEFT JOIN Province AS p ON a.ProvinceNo = p.ProvinceNo ");
		    sqlQuery.append("LEFT JOIN District AS d ON a.DistrictNo = d.DistrictNo ");
		    sqlQuery.append("LEFT JOIN SubDistrict AS s ON a.SubDistrictNo = s.SubDistrictNo ");
		    sqlQuery.append("WHERE f.FarmerGroupType = 'F' ");
			if(provinceNo > 0)
				sqlQuery.append("AND p.provinceNo=:provinceNo ");
			if(provinceNo > 0)
				sqlQuery.append("AND p.provinceNo=:provinceNo "); 
			if(districtNo > 0)
				sqlQuery.append("AND d.districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND s.subDistrictNo=:subDistrictNo ");
			if(!"".equals(farmerGroupName))
				sqlQuery.append("AND f.farmerGroupName LIKE :farmerGroupName "); 
			if(!"".equals(cooperativeType)) {
				if("Cooperative".equals(cooperativeType))
					sqlQuery.append("AND "+cooperative+"IS NOT NULL ");
				else
					sqlQuery.append("AND "+cooperative+"IS NULL ");
			}
		    sqlQuery.append("ORDER BY FarmerGroupName ASC ");
		  
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			if(!"".equals(farmerGroupName))
				query.setString("farmerGroupName", farmerGroupName+"%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	public void saveFarmerGroupTeam(String teamFName, String teamLName, String teamPosition, long farmerGroupId) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("INSERT INTO FarmerGroupTeam ");
		    sqlQuery.append("(FirstName,LastName,Position,FarmerGroupId) VALUES(?, ?, ?, ?) ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, teamFName);
			query.setString(1, teamLName);
			query.setString(2, teamPosition);
			query.setLong(3, farmerGroupId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("saveFarmerGroupTeam failed", re);
			throw re;
		}
	}
	
	public void deleteFarmerGroupTeam(long farmerGroupTeamId)	{
		try {
			String sqlQuery = "Delete from FarmerGroupTeam where FarmerGroupTeamId=:farmerGroupTeamId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("farmerGroupTeamId", farmerGroupTeamId);
			uQuery.executeUpdate();
			log.info("deleteFarmerGroupTeam successful");
		}catch (RuntimeException re) {
			log.error("deleteFarmerGroupTeam failed", re);
			throw re;
		}
	}
}
