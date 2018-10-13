package com.wsndata.dbaccess;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheck;
public class LandCheckHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandCheckHome.class);
	public List<LandCheck> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheck.class);
			crit.addOrder(Order.asc("idCard"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public LandCheck findByLandCheckId(long landCheckId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheck.class);	
			crit.add(Restrictions.eq("landCheckId", landCheckId));
			return (LandCheck)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByLandCheckId failed", re);
			throw re;
		}
	}
	
	public List<LandCheck> searchByCriteria(String idCard, String firstName, String lastName, int plantYear, int plantNo, String startDate, String endDate, 
			long checkPeriodId, String result, long regionNo, long provinceNo, long districtNo, long subDistrictNo, long farmerGroupId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct ls.* FROM (");
			
			sqlQuery.append("SELECT lc.LandCheckId, lc.IdCard, CONCAT(lc.FirstName,' ',lc.LastName) as FarmerName, ");
			sqlQuery.append("DATE_FORMAT(DATE_ADD(lc.CheckDate, INTERVAL 543 year), '%d/%m/%Y') CheckDate, ");
			sqlQuery.append("cp.Description as CheckPeriod, CONCAT(IFNULL(lt.TypeName,''),'/',lc.DocNo) LandRight, lc.Result, lc.PlantYear, lc.PlantNo, ");
			sqlQuery.append("f.RegionNo, f.ProvinceNo, f.DistrictNo, f.SubDistrictNo FROM LandCheck lc ");
			sqlQuery.append("LEFT JOIN checkperiod cp ON lc.CheckPeriodId = cp.CheckPeriodId ");
			sqlQuery.append("LEFT JOIN Landrighttype lt ON lc.TypeId = lt.TypeId ");
			sqlQuery.append("LEFT JOIN Landright lr ON lc.DocNo = lr.DocNo AND lc.TypeId = lr.TypeId ");
			sqlQuery.append("AND lr.DocRai = lc.DocRai AND lr.DocNgan = lc.DocNgan AND lr.DocWah = lc.DocWah ");
			sqlQuery.append("AND lr.PlantId IN (SELECT MAX(pl.plantId) FROM Plant pl group by pl.RefPlantId) ");
			sqlQuery.append("LEFT JOIN Plant p ON p.PlantId = lr.PlantId ");
			sqlQuery.append("LEFT JOIN FarmerGroupAddress f ON f.FarmerGroupId = p.FarmerGroupId2 ");
			sqlQuery.append("WHERE lc.LandCheckId IS NOT NULL ");
			if(!idCard.equals(""))
				sqlQuery.append("AND lc.IdCard=:idCard ");
			if(!firstName.equals(""))
				sqlQuery.append("AND lc.FirstName LIKE :firstName ");
			if(!lastName.equals(""))
				sqlQuery.append("AND lc.LastName LIKE :lastName ");
			if(plantYear>0)
				sqlQuery.append("AND lc.PlantYear=:plantYear ");
			if(plantNo>0)
				sqlQuery.append("AND lc.PlantNo=:plantNo ");
			if(!startDate.equals(""))
				sqlQuery.append("AND lc.CheckDate >= :startDate ");
			if(!endDate.equals(""))
				sqlQuery.append("AND lc.CheckDate <= :endDate ");
			if(checkPeriodId > 0)
				sqlQuery.append("AND lc.CheckPeriodId=:checkPeriodId ");
			if(!result.equals(""))
				sqlQuery.append("AND lc.Result=:result ");
			if(regionNo > 0)
				sqlQuery.append("AND f.regionNo=:regionNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND f.provinceNo=:provinceNo "); 
			if(districtNo > 0)
				sqlQuery.append("AND f.districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND f.subDistrictNo=:subDistrictNo ");
			if(farmerGroupId > 0)
				sqlQuery.append("AND f.FarmerGroupId=:farmerGroupId ");
			
			sqlQuery.append(") ls ORDER BY ls.IdCard");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!idCard.equals(""))			
				query.setString("idCard", idCard);
			if(!firstName.equals(""))
				query.setString("firstName", "%"+firstName+"%");
			if(!lastName.equals(""))
				query.setString("lastName", "%"+lastName+"%");
			if(plantYear>0)
				query.setInteger("plantYear", plantYear);
			if(plantNo>0)
				query.setInteger("plantNo", plantNo);
			if(!startDate.equals(""))
				query.setString("startDate", startDate);
			if(!endDate.equals(""))
				query.setString("endDate", endDate);
			if(checkPeriodId > 0)
				query.setLong("checkPeriodId", checkPeriodId);
			if(!result.equals(""))
				query.setString("result", result);
			if(regionNo > 0)
				query.setLong("regionNo", regionNo);
			if(provinceNo > 0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo > 0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo > 0)
				query.setLong("subDistrictNo", subDistrictNo);
			if(farmerGroupId > 0)
				query.setLong("farmerGroupId", farmerGroupId);
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	
	public List<LandCheck> getLandChecked(String idCard, int plantYear, int plantNo, long breedTypeId){
		
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT land.*, b.BreedTypeName, c.Description, p.PlantId, p.refPlantId, lr.TypeName from landcheck as land"); 
			sqlQuery.append(" LEFT JOIN Plant AS p on land.PlantYear =  p.PlantYear");
			sqlQuery.append(" AND land.PlantNo = p.PlantNo");
			sqlQuery.append(" AND land.IdCard  = p.IdCard");
			sqlQuery.append(" AND land.BreedTypeId  = p.BreedTypeId");
			sqlQuery.append(" LEFT JOIN BreedType AS b");
			sqlQuery.append(" ON b.BreedTypeId  = land.BreedTypeId");
			sqlQuery.append(" LEFT JOIN CheckPeriod AS c");
			sqlQuery.append(" ON land.CheckPeriodId = c.CheckPeriodId");
			sqlQuery.append(" LEFT JOIN landrighttype as lr ON land.typeId=lr.TypeId");
			sqlQuery.append(" WHERE land.PlantYear =:plantYear ");
			sqlQuery.append(" AND land.PlantNo =:plantNo ");
			sqlQuery.append(" AND land.IdCard =:idCard");
			sqlQuery.append(" AND land.BreedTypeId =:breedTypeId");
			sqlQuery.append(" AND p.PlantId = (SELECT MAX(p.plantId) FROM Plant pl WHERE pl.RefPlantId = p.RefPlantId)"); 
			sqlQuery.append(" GROUP BY land.landcheckId, p.RefPlantId");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if(!idCard.equals(""))			
				query.setString("idCard", idCard);
			if(plantYear > 0)
				query.setInteger("plantYear", plantYear);
			if(plantNo > 0)
				query.setInteger("plantNo", plantNo);
			if(breedTypeId > 0)
				query.setLong("breedTypeId", breedTypeId);
			
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("getLandChecked failed", re);
			throw re;
		}
	}

	public List<LandCheck> findLandCheck(int plantYear, int plantNo, String idCard, long typeId, 
			long breedTypeId, String docNo, Date checkDate, long checkPeriodId) {
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheck.class);	
			crit.add(Restrictions.eq("plantYear", plantYear));
			crit.add(Restrictions.eq("plantNo", plantNo));
			crit.add(Restrictions.eq("idCard", idCard));
			crit.add(Restrictions.eq("typeId", typeId));
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.add(Restrictions.eq("docNo", docNo));
			crit.add(Restrictions.eq("checkDate", checkDate));	
			crit.add(Restrictions.eq("checkPeriodId", checkPeriodId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findLandCheck failed", re);
			throw re;
		}
	}
	
	public void deleteLandChecked(long landCheckId){
		
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("DELETE FROM LandCheck WHERE LandCheckId = :landCheckId ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if(landCheckId > 0)
				query.setLong("landCheckId", landCheckId);
			
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("deleteLandChecked failed", re);
			throw re;
		}
	}
	
}
