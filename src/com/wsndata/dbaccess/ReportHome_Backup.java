package com.wsndata.dbaccess;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Branch;

public class ReportHome_Backup extends HibernateHome {

	private static final Logger log = Logger.getLogger(ReportHome_Backup.class);
	
	private String inBranch(List branch) {
		String bCode = "";
		for (int i = 0; i < branch.size(); i++) {
			if (i+1==branch.size()) {
				bCode += (((Branch)branch.get(i)).getBranchCode());
				break;
			}
			bCode += (((Branch)branch.get(i)).getBranchCode())+",";
		}
		return bCode;
	}
	
	private void setReportDate(Date effectiveDate) {
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SET @reportDate = :effectiveDate");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());	
			if (effectiveDate != null) {
			    query.setDate("effectiveDate", effectiveDate);
			}
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("setReportDate failed", re);
			throw re;
		}
	}
	
	public List findR101ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r101 r1 ");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(createDate) maxDate");
//			sqlQuery.append(" FROM r101");
//			sqlQuery.append(" where createDate <= :createDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,idCard,breedTypeName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.subDistrictName = r2.subDistrictName AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.createDate = r2.maxDate AND");
//			sqlQuery.append(" r1.plantId in (SELECT MAX(plantId) FROM r101 GROUP BY refplantid)");
			sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r101 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,idCard,breedTypeName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR101ByCriteria failed", re);
			throw re;
		}	
	}

	public List findR101_1ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r101_1 r1 ");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r101_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,idCard,breedGroupName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.subDistrictName = r2.subDistrictName AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.breedGroupName = r2.breedGroupName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r101_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND r1.effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,idCard,breedGroupName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR101_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR102ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo ,String breedTypeId,List branch)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.*,count(r1.countFarmer) ,sum(r1.PlantRai),sum(r1.PlantNgan),sum(r1.PlantWah),sum(r1.ForecastRecord) FROM r102 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r102");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,countFarmer,breedTypeName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.countFarmer = r2.countFarmer AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate AND");
//			sqlQuery.append(" r1.plantId in (SELECT MAX(plantId) FROM r102 GROUP BY refplantid)");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.branchCode IN("+inBranch(branch)+")");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r102 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND r1.EffectiveDate <= :effectiveDate");
//			}
		    
		    sqlQuery.append(" AND r1.BreedTypeId IN("+breedTypeId+")");
			
		    sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeName");
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(r1.breedTypeId,'"+breedTypeId+"')");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR102ByCriteria failed", re);
			throw re;
		}	
	}
	
//	public List getSubR102ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode, long provinceNo, String subDistrictName, String breedTypeId)
//	{	
//		try{
//			StringBuffer sqlQuery = new StringBuffer();
//			sqlQuery.append(" SELECT *,count(countFarmer) FROM r102");
//		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
//		    if (plantyear>0) {
//		    	sqlQuery.append(" AND PlantYear=:plantyear");
//			}
//		    if (plantNo>0) {
//		    	 sqlQuery.append(" AND PlantNo=:plantNo");
//			}
//		    if (provinceNo>0) {
//		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
//			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
//		    if (branchCode > 0) {
//		    	sqlQuery.append(" AND BranchCode =:branchCode");
//			}
//		    if (!subDistrictName.equals("")) {
//		    	sqlQuery.append(" AND SubDistrictName =:subDistrictName");
//			}
//		    
//		    sqlQuery.append(" AND BreedTypeId IN("+breedTypeId+")");
//		    
//		    sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeName");
//		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(breedTypeId,'"+breedTypeId+"')");
//			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//			
//			if (plantyear>0) {
//				query.setLong("plantyear", plantyear);
//			}
//		    if (plantNo>0) {
//		    	query.setLong("plantNo", plantNo);
//			}
//		    if (provinceNo>0) {
//		    	query.setLong("provinceNo", provinceNo);
//			}
//		    if (effectiveDate != null) {
//		    	query.setDate("effectiveDate", effectiveDate);
//			}
//		    if (branchCode > 0) {
//		    	query.setLong("branchCode", branchCode);
//			}
//		    if (!subDistrictName.equals("")) {
//		    	query.setString("subDistrictName", subDistrictName);
//			}
//			return query.list();
//		} catch (RuntimeException re) {
//			log.error("findR102ByCriteria failed", re);
//			throw re;
//		}	
//	}
	
	public List findR102_1ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode,long provinceNo ,String breedTypeId,List branch)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.*,count(r1.countFarmer) ,sum(r1.PlantRai),sum(r1.PlantNgan),sum(r1.PlantWah),sum(r1.ForecastRecord) FROM r102_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r102_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,countFarmer,breedTypeName,breedGroupName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.countFarmer = r2.countFarmer AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.breedGroupName = r2.breedGroupName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.branchCode IN("+inBranch(branch)+")");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r102_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND r1.EffectiveDate <= :effectiveDate");
//			}
		    if (branchCode > 0) {
		    	sqlQuery.append(" AND r1.BranchCode =:branchCode");
			}
		    
		    sqlQuery.append(" AND r1.BreedTypeId IN("+breedTypeId+")");
			
		    sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeName,breedGroupName");
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(r1.breedTypeId,'"+breedTypeId+"'),breedGroupName ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (branchCode > 0) {
		    	query.setLong("branchCode", branchCode);
			}
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR102_1ByCriteria failed", re);
			throw re;
		}	
	}
	
//	public List getSubR102_1ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode, long provinceNo, String subDistrictName, String breedTypeId,String breedGroupName)
//	{	
//		try{
//			StringBuffer sqlQuery = new StringBuffer();
//			sqlQuery.append(" SELECT * FROM r102_1");
//		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
//		    if (plantyear>0) {
//		    	sqlQuery.append(" AND PlantYear=:plantyear");
//			}
//		    if (plantNo>0) {
//		    	 sqlQuery.append(" AND PlantNo=:plantNo");
//			}
//		    if (provinceNo>0) {
//		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
//			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
//		    if (branchCode > 0) {
//		    	sqlQuery.append(" AND BranchCode =:branchCode");
//			}
//		    if (!subDistrictName.equals("")) {
//		    	sqlQuery.append(" AND SubDistrictName =:subDistrictName");
//			}
//		    
//		    sqlQuery.append(" AND BreedTypeId IN("+breedTypeId+")");
//		    
//		    if (!breedGroupName.equals("")) {
//		    	sqlQuery.append(" AND BreedGroupName =:breedGroupName");
//		    	
//			}else{
//				sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeId");
//			}
//		    
//		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(breedTypeId,'"+breedTypeId+"')");
//			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//			
//			if (plantyear>0) {
//				query.setLong("plantyear", plantyear);
//			}
//		    if (plantNo>0) {
//		    	query.setLong("plantNo", plantNo);
//			}
//		    if (provinceNo>0) {
//		    	query.setLong("provinceNo", provinceNo);
//			}
//		    if (effectiveDate != null) {
//		    	query.setDate("effectiveDate", effectiveDate);
//			}
//		    if (branchCode > 0) {
//		    	query.setLong("branchCode", branchCode);
//			}
//		    if (!subDistrictName.equals("")) {
//		    	query.setString("subDistrictName", subDistrictName);
//			}
//		    if (!breedGroupName.equals("")) {
//		    	query.setString("breedGroupName", breedGroupName);
//			}
//			return query.list();
//		} catch (RuntimeException re) {
//			log.error("findR102ByCriteria failed", re);
//			throw re;
//		}	
//	}
	
	public List findR103ByCriteria(long breedTypeId, long breedGroupId, long provinceNo, long branchCode, Date forecastDatestr, Date forecastDateend)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			
			sqlQuery.append(" SELECT * FROM r103");
		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
//		    sqlQuery.append(" AND FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND PlantDate IS NOT NULL");
//		    sqlQuery.append(" AND BranchCode IN ("+inBranch(bCode)+")");
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND BranchCode=:branchCode");
		    }
		    if (forecastDatestr != null) {
		    	sqlQuery.append(" AND forecastDate BETWEEN :forecastDatestr AND :forecastDateend");
			}
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,farmerGroupName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (forecastDatestr != null) {
		    	query.setDate("forecastDatestr", forecastDatestr);
				query.setDate("forecastDateend", forecastDateend);
			}
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR103ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR103_1ByCriteria(long breedTypeId, long breedGroupId, long provinceNo, long branchCode, Date forecastDatestr, Date forecastDateend)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM r103_1");
		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
//		    sqlQuery.append(" AND FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND PlantDate IS NOT NULL");
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND BranchCode=:branchCode");
		    }
		    if (forecastDatestr != null) {
		    	sqlQuery.append(" AND publicMarketDate BETWEEN :forecastDatestr AND :forecastDateend");
			}
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,farmerGroupName,publicMarketDate,idCard");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (forecastDatestr != null) {
		    	query.setDate("forecastDatestr", forecastDatestr);
				query.setDate("forecastDateend", forecastDateend);
			}
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR103_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR104ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r104 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r104");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,idCard,breedTypeName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.subDistrictName = r2.subDistrictName AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r104 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,idCard,breedTypeName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR104ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR104_1ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r104_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r104_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,idCard,breedGroupName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.subDistrictName = r2.subDistrictName AND");
//			sqlQuery.append(" r1.breedGroupName = r2.breedGroupName AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r104_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
		    if (breedGroupId>0) {
		    	sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,idCard,breedGroupName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR104_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR104_2ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r104_2 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r104_2");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r104_2 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,farmerGroupName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR104_2ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR104_3ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r104_3 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r104_3");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.PlantDate IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r104_3 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND HarvestDate IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,farmerGroupName,districtName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR104_3ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR105ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo ,String breedTypeId, List branch)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.*,count(distinct(r1.countFarmer)) ,sum(r1.PlantRai),sum(r1.PlantNgan),sum(r1.PlantWah),sum(r1.ForecastRecord),sum(r1.TotalHarvest) FROM r105 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r105");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,countFarmer,breedTypeName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.countFarmer = r2.countFarmer AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
//		    sqlQuery.append(" AND TotalHarvest IS NOT NULL");
		    sqlQuery.append(" AND r1.branchCode IN("+inBranch(branch)+")");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r105 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
		    
		    sqlQuery.append(" AND r1.BreedTypeId IN("+breedTypeId+")");
			
		    sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeName");
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(r1.breedTypeId,'"+breedTypeId+"')");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR105ByCriteria failed", re);
			throw re;
		}	
	}
	
//	public List getSubR105ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode, long provinceNo, String subDistrictName, String breedTypeId)
//	{	
//		try{
//			StringBuffer sqlQuery = new StringBuffer();
//			sqlQuery.append(" SELECT * FROM r105");
//		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
////		    sqlQuery.append(" AND TotalHarvest IS NOT NULL");
//		    if (plantyear>0) {
//		    	sqlQuery.append(" AND PlantYear=:plantyear");
//			}
//		    if (plantNo>0) {
//		    	 sqlQuery.append(" AND PlantNo=:plantNo");
//			}
//		    if (provinceNo>0) {
//		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
//			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
//		    if (branchCode > 0) {
//		    	sqlQuery.append(" AND BranchCode =:branchCode");
//			}
//		    if (!subDistrictName.equals("")) {
//		    	sqlQuery.append(" AND SubDistrictName =:subDistrictName");
//			}
//		    
//		    sqlQuery.append(" AND BreedTypeId IN("+breedTypeId+")");
//			
//		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(breedTypeId,'"+breedTypeId+"')");
//			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//			
//			if (plantyear>0) {
//				query.setLong("plantyear", plantyear);
//			}
//		    if (plantNo>0) {
//		    	query.setLong("plantNo", plantNo);
//			}
//		    if (provinceNo>0) {
//		    	query.setLong("provinceNo", provinceNo);
//			}
//		    if (effectiveDate != null) {
//		    	query.setDate("effectiveDate", effectiveDate);
//			}
//		    if (branchCode > 0) {
//		    	query.setLong("branchCode", branchCode);
//			}
//		    if (!subDistrictName.equals("")) {
//		    	query.setString("subDistrictName", subDistrictName);
//			}
//			return query.list();
//		} catch (RuntimeException re) {
//			log.error("findR105ByCriteria failed", re);
//			throw re;
//		}	
//	}
	
	public List findR105_1ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode,long provinceNo ,String breedTypeId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.*,count(r1.countFarmer) ,sum(r1.PlantRai),sum(r1.PlantNgan),sum(r1.PlantWah),sum(r1.ForecastRecord),sum(r1.TotalHarvest) FROM r105_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r105_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,countFarmer,breedTypeName,breedGroupName");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.countFarmer = r2.countFarmer AND");
//			sqlQuery.append(" r1.breedTypeName = r2.breedTypeName AND");
//			sqlQuery.append(" r1.breedGroupName = r2.breedGroupName AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.ProvinceNo IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r105_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
//		    sqlQuery.append(" AND TotalHarvest IS NOT NULL");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.PlantYear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
		    if (branchCode > 0) {
		    	sqlQuery.append(" AND r1.BranchCode =:branchCode");
			}
		    
		    sqlQuery.append(" AND r1.BreedTypeId IN("+breedTypeId+")");
			
		    sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeName,breedGroupName");
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(r1.breedTypeId,'"+breedTypeId+"'),breedGroupName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (branchCode > 0) {
		    	query.setLong("branchCode", branchCode);
			}
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR105_1ByCriteria failed", re);
			throw re;
		}	
	}
	
//	public List getSubR105_1ByCriteria(long plantyear, long plantNo, Date effectiveDate,long branchCode, long provinceNo, String subDistrictName, String breedTypeId,String breedGroupName)
//	{	
//		try{
//			StringBuffer sqlQuery = new StringBuffer();
//			sqlQuery.append(" SELECT * FROM r105_1");
//		    sqlQuery.append(" WHERE ProvinceNo IS NOT NULL");
//		    if (plantyear>0) {
//		    	sqlQuery.append(" AND PlantYear=:plantyear");
//			}
//		    if (plantNo>0) {
//		    	 sqlQuery.append(" AND PlantNo=:plantNo");
//			}
//		    if (provinceNo>0) {
//		    	sqlQuery.append(" AND ProvinceNo=:provinceNo");
//			}
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND EffectiveDate <= :effectiveDate");
//			}
//		    if (branchCode > 0) {
//		    	sqlQuery.append(" AND BranchCode =:branchCode");
//			}
//		    if (!subDistrictName.equals("")) {
//		    	sqlQuery.append(" AND SubDistrictName =:subDistrictName");
//			}
//		    
//		    sqlQuery.append(" AND BreedTypeId IN("+breedTypeId+")");
//		    
//		    if (!breedGroupName.equals("")) {
//		    	sqlQuery.append(" AND BreedGroupName =:breedGroupName");
//		    	
//			}else{
//				sqlQuery.append(" GROUP BY provinceName,branchName,subDistrictName,breedTypeId");
//			}
//		    
//		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,FIND_IN_SET(breedTypeId,'"+breedTypeId+"')");
//			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//			
//			if (plantyear>0) {
//				query.setLong("plantyear", plantyear);
//			}
//		    if (plantNo>0) {
//		    	query.setLong("plantNo", plantNo);
//			}
//		    if (provinceNo>0) {
//		    	query.setLong("provinceNo", provinceNo);
//			}
//		    if (effectiveDate != null) {
//		    	query.setDate("effectiveDate", effectiveDate);
//			}
//		    if (branchCode > 0) {
//		    	query.setLong("branchCode", branchCode);
//			}
//		    if (!subDistrictName.equals("")) {
//		    	query.setString("subDistrictName", subDistrictName);
//			}
//		    if (!breedGroupName.equals("")) {
//		    	query.setString("breedGroupName", breedGroupName);
//			}
//			return query.list();
//		} catch (RuntimeException re) {
//			log.error("findR105ByCriteria failed", re);
//			throw re;
//		}	
//	}
	
	public List findR106ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r106 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r106");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r106 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR106ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR107ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r107 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r107");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r107 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR107ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR108_1ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r108_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r108_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r108_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,subDistrictName,farmerGroupName");
		    Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR108_1ByCriteria failed", re);
			throw re;
		}	
	}
	public List findR108_3ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
		
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r108_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r108_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r108_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,farmerGroupName,districtName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR108_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR109_1ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r109_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r109_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r109_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,districtName,subDistrictName,farmerGroupName");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR109_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR109_3ByCriteria(long plantyear, long plantNo, Date effectiveDate, long provinceNo, long branchCode ,long breedTypeId, long breedGroupId)
	{	
		try{
			setReportDate(effectiveDate);
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT r1.* FROM r109_1 r1");
//			sqlQuery.append(" INNER JOIN ");
//			sqlQuery.append(" (SELECT *,MAX(effectiveDate) maxDate");
//			sqlQuery.append(" FROM r109_1");
//			sqlQuery.append(" where effectiveDate <= :effectiveDate");
//			sqlQuery.append(" GROUP BY provinceName,branchName,farmerGroupName,idCard,breedTypeId,breedGroupId");
//			sqlQuery.append(" ) r2 ON r1.provinceNo = r2.provinceNo AND");
//			sqlQuery.append(" r1.branchCode = r2.branchCode AND");
//			sqlQuery.append(" r1.idCard = r2.idCard AND");
//			sqlQuery.append(" r1.farmerGroupName = r2.farmerGroupName AND");
//			sqlQuery.append(" r1.breedTypeId = r2.breedTypeId AND");
//			sqlQuery.append(" r1.breedGroupId = r2.breedGroupId AND");
//			sqlQuery.append(" r1.plantyear = r2.plantyear AND");
//			sqlQuery.append(" r1.plantNo = r2.plantNo AND");
//			sqlQuery.append(" r1.effectiveDate = r2.maxDate");
		    sqlQuery.append(" WHERE r1.SumCost IS NOT NULL");
		    sqlQuery.append(" AND r1.FarmerGroupName IS NOT NULL");
		    sqlQuery.append(" AND r1.plantId in (SELECT MAX(plantId) FROM r109_1 where effectiveDate <= :effectiveDate GROUP BY refplantid)");
		    if (plantyear>0) {
		    	sqlQuery.append(" AND r1.Plantyear=:plantyear");
			}
		    if (plantNo>0) {
		    	 sqlQuery.append(" AND r1.PlantNo=:plantNo");
			}
		    if (provinceNo>0) {
		    	sqlQuery.append(" AND r1.ProvinceNo=:provinceNo");
			}
		    if (branchCode>0) {
		    	sqlQuery.append(" AND r1.BranchCode=:branchCode");
		    }
//		    if (effectiveDate != null) {
//		    	sqlQuery.append(" AND effectiveDate <= :effectiveDate");
//			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND r1.BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND r1.BreedGroupId=:breedGroupId");
			}
		    
		    sqlQuery.append(" ORDER BY provinceName,branchName,farmerGroupName,districtName,subDistrictName");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if (plantyear>0) {
				query.setLong("plantyear", plantyear);
			}
		    if (plantNo>0) {
		    	query.setLong("plantNo", plantNo);
			}
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (branchCode>0) {
		    	query.setLong("branchCode", branchCode);
		    }
		    if (effectiveDate != null) {
		    	query.setDate("effectiveDate", effectiveDate);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR109_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR009ByCriteria(Date forecastDatestr, Date forecastDateend, long regionNo ,long breedTypeId, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT *,SUM(forecastRecord) AS sumMonths, MONTH(forecastDate) as smonth, ");
		    sqlQuery.append("YEAR(forecastDate)-543 as syear FROM r009 WHERE forecastRecord > 0 ");
		   
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (forecastDatestr != null) {
		    	sqlQuery.append(" AND forecastDate BETWEEN :forecastDatestr AND :forecastDateend");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}

		    sqlQuery.append(" GROUP BY ProvinceNo, DistrictNo, SubdistrictNo, syear, smonth");
		    sqlQuery.append(" ORDER BY RegionNo, ProvinceNo, CooperativeId, DistrictNo, SubDistrictNo, ForecastDate");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (forecastDatestr != null) {
		    	query.setDate("forecastDatestr", forecastDatestr);
				query.setDate("forecastDateend", forecastDateend);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR009ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR010ByCriteria(Date forecastDateStr, Date forecastDateEnd, long regionNo ,long breedTypeId, long breedGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT *,SUM(PublicCrop) AS sumMonths, MONTH(PublicMarketDate) as smonth, ");
		    sqlQuery.append("YEAR(PublicMarketDate)-543 as syear FROM r010 WHERE PublicMarketDate > 0 ");
		   
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (forecastDateStr != null) {
		    	sqlQuery.append(" AND PublicMarketDate BETWEEN :forecastDateStr AND :forecastDateEnd");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}

		    sqlQuery.append(" GROUP BY ProvinceNo, DistrictNo, SubdistrictNo, syear, smonth");
		    sqlQuery.append(" ORDER BY RegionNo, ProvinceNo, CooperativeId, DistrictNo, SubDistrictNo, PublicMarketDate");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (forecastDateStr != null) {
		    	query.setDate("forecastDateStr", forecastDateStr);
				query.setDate("forecastDateEnd", forecastDateEnd);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR010ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List getSubR110ByCriteria( Date forecastDatestr, Date forecastDateend, long regionNo ,long breedTypeId, long breedGroupId ,String subDistrictName)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT *,SUM(forecastRecord) AS sumMonths FROM r009");
		    sqlQuery.append(" WHERE regionNo IS NOT NULL");
		    sqlQuery.append(" AND forecastRecord > 0");
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (forecastDatestr != null) {
		    	sqlQuery.append(" AND forecastDate BETWEEN :forecastDatestr AND :forecastDateend");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (!"".equals(subDistrictName)) {
		    	sqlQuery.append(" AND subDistrictThai=:subDistrictThai");
			}

		    sqlQuery.append(" GROUP BY ProvinceNo, DistrictNo, SubdistrictNo,DATE_FORMAT(forecastDate, \"%m-%y\")");
		    sqlQuery.append(" ORDER BY ProvinceNo, DistrictNo, SubdistrictNo");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (forecastDatestr != null) {
		    	query.setDate("forecastDatestr", forecastDatestr);
				query.setDate("forecastDateend", forecastDateend);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    if (!"".equals(subDistrictName)) {
		    	query.setString("subDistrictThai", subDistrictName);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("getSubR110ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR110_1ByCriteria( Date publicDatestr, Date publicDateend, long regionNo ,long breedTypeId, long breedGroupId,List branch)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT *,SUM(publicCrop) AS sumMonths FROM r110_1");
		    sqlQuery.append(" WHERE regionNo IS NOT NULL");
		    sqlQuery.append(" AND publicCrop > 0");
		    sqlQuery.append(" AND branchCode IN("+inBranch(branch)+")");
		   
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (publicDatestr != null) {
		    	sqlQuery.append(" AND publicMarketDate BETWEEN :publicDatestr AND :publicDateend");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}

		    sqlQuery.append(" GROUP BY provinceName,districtName,subDistrictName");
		    sqlQuery.append(" ORDER BY provinceName,districtName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (publicDatestr != null) {
		    	query.setDate("publicDatestr", publicDatestr);
				query.setDate("publicDateend", publicDateend);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR110_1ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List getSubR110_1ByCriteria( Date publicDatestr, Date publicDateend, long regionNo ,long breedTypeId, long breedGroupId ,String subDistrictName)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT *,SUM(publicCrop) AS sumMonths FROM r110_1");
		    sqlQuery.append(" WHERE regionNo IS NOT NULL");
		    sqlQuery.append(" AND publicCrop > 0");
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (publicDatestr != null) {
		    	sqlQuery.append(" AND publicMarketDate BETWEEN :publicDatestr AND :publicDateend");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (!"".equals(subDistrictName)) {
		    	sqlQuery.append(" AND subDistrictName=:subDistrictName");
			}

		    sqlQuery.append(" GROUP BY provinceName,districtName,subDistrictName,DATE_FORMAT(publicMarketDate, \"%m-%y\")");
		    sqlQuery.append(" ORDER BY provinceName,districtName,subDistrictName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (publicDatestr != null) {
		    	query.setDate("publicDatestr", publicDatestr);
				query.setDate("publicDateend", publicDateend);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    if (!"".equals(subDistrictName)) {
		    	query.setString("subDistrictName", subDistrictName);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("getSubR110_1ByCriteria failed", re);
			throw re;
		}	
	}
}
