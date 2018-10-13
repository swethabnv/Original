package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Plant;

public class PlantHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PlantHome.class);

	public Plant findById(long plantId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Plant.class);
			crit.add(Restrictions.eq("plantId", plantId));
			return (Plant) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}
	
	public List<Plant> findByRefId(long refPlantId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Plant.class);
			crit.add(Restrictions.eq("refPlantId", refPlantId));
			crit.addOrder(Order.asc("plantId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRefId failed", re);
			throw re;
		}
	}
	
	public List searchByCriteria(long breedTypeId, int plantYear, int plantNo, String idCard, String firstName, String lastName, long regionNo,long provinceNo, long districtNo, long subDistrictNo, long farmerGroupId, String plantStatus, long cooperativeGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT DISTINCT p.PlantId AS plantId");// 0
			//sqlQuery.append(", p.CustomerNo AS customerNo");
			sqlQuery.append(", f.IdCard AS idCard");//1
			sqlQuery.append(", f.AbbrPrefix As prefix");//2
			sqlQuery.append(", f.FirstName AS firstName");//3
			sqlQuery.append(", f.LastName AS lastName");//4
			sqlQuery.append(", d.ThaiName AS thaiName"); //5 district name
			sqlQuery.append(", p.BranchCode AS branchCode");//6
			sqlQuery.append(", b.BranchName AS branchName");//7
			sqlQuery.append(", p.BreedTypeId AS breedTypeId");//8
			sqlQuery.append(", t.BreedTypeName AS breedTypeName");//9
			sqlQuery.append(", p.PlantYear AS plantYear");//10
			sqlQuery.append(", p.PlantNo AS plantNo");//11
			sqlQuery.append(", p.RefPlantId As refPlantId");//12
			//sqlQuery.append(", p.PlantStatus AS PlantStatus");//13
			sqlQuery.append(", p.reason AS reason");//13
			sqlQuery.append(", LEFT(p.PlantStatus,1) AS plantStatus");//14
			sqlQuery.append(", p.status AS status");//15
			sqlQuery.append(", p.remark AS remark");//16
			sqlQuery.append(" FROM Plant AS p");
			sqlQuery.append(" LEFT JOIN BreedType AS t ON p.BreedTypeId = t.BreedTypeId");
			sqlQuery.append(" LEFT JOIN Farmer AS f ON p.IdCard = f.IdCard AND f.effectiveDate = p.effectiveDate");//(SELECT MAX(fm.effectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard)");
		    sqlQuery.append(" LEFT JOIN Branch AS b ON p.BranchCode = b.BranchCode");
		    sqlQuery.append(" LEFT JOIN Region AS r ON f.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS prov ON f.ProvinceNo = prov.ProvinceNo");
		    sqlQuery.append(" LEFT JOIN District AS d ON f.DistrictNo = d.DistrictNo");
		    sqlQuery.append(" LEFT JOIN SubDistrict AS sub ON f.SubDistrictNo = sub.SubDistrictNo");
		    sqlQuery.append(" LEFT JOIN FarmerGroup AS fg ON p.FarmerGroupId2 = fg.FarmerGroupId");
		    sqlQuery.append(" LEFT JOIN FarmerGroupAddress AS fga ON fg.FarmerGroupId = fga.FarmerGroupId");
		    sqlQuery.append(" WHERE p.Flag <> 'D' AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId= p.RefPlantId)");
		    
		    if(breedTypeId > 0)
		    	sqlQuery.append(" AND p.BreedTypeId=:breedTypeId");
		    if(plantYear > 0)
		    	sqlQuery.append(" AND p.PlantYear=:plantYear");
		    if(plantNo > 0)
		    	sqlQuery.append(" AND p.PlantNo LIKE :plantNo");
		    if(!"".equals(idCard))
		    	sqlQuery.append(" AND p.IdCard LIKE :idCard");
		    if(farmerGroupId > 0)
		    	sqlQuery.append(" AND p.FarmerGroupId2=:farmerGroupId");
		    if(regionNo > 0)
		    	sqlQuery.append(" AND fga.RegionNo=:regionNo");
		    if(provinceNo > 0)
		    	sqlQuery.append(" AND fga.ProvinceNo=:provinceNo");
		    if(districtNo > 0)
		    	sqlQuery.append(" AND fga.DistrictNo=:districtNo");
		    if(subDistrictNo > 0)
		    	sqlQuery.append(" AND fga.SubDistrictNo=:subDistrictNo");
		    if(!"".equals(firstName))
		    	sqlQuery.append(" AND f.FirstName LIKE :firstName");
		    if(!"".equals(lastName))
		    	sqlQuery.append(" AND f.LastName LIKE :lastName");
		    if(!"".equals(plantStatus))
		    	sqlQuery.append(" AND p.PlantStatus =:plantStatus");
		    if(cooperativeGroupId > 0)
		    	sqlQuery.append(" AND fg.FarmerGroupId =:cooperativeGroupId");
		    	
		    sqlQuery.append(" GROUP BY RefPlantId");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if(breedTypeId > 0)
		    	query.setLong("breedTypeId", breedTypeId);
		    if(plantYear > 0)
		    	query.setInteger("plantYear", plantYear);
		    if(plantNo > 0)
		    	query.setString("plantNo","%"+plantNo+"%");
		    if(!"".equals(idCard))
		    	query.setString("idCard","%"+idCard+"%");
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
		    if(!"".equals(firstName))
		    	query.setString("firstName","%"+firstName+"%");
		    if(!"".equals(lastName))
		    	query.setString("lastName","%"+lastName+"%");
		    if(!"".equals(plantStatus))
		    	query.setString("plantStatus", plantStatus);
		    if(cooperativeGroupId>0)
		    	query.setLong("cooperativeGroupId", cooperativeGroupId);
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}	
	}
	
	
	public List searchPlantByCriteria(long breedTypeId, int plantYear, int plantNo, String idCard, String firstName, String lastName, long regionNo, List<String> provinceNo, long districtNo, long subDistrictNo, List<String> farmerGroupId, String plantStatus, List<String> cooperativeGroupId)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT DISTINCT p.PlantId AS plantId");// 0
			//sqlQuery.append(", p.CustomerNo AS customerNo");
			sqlQuery.append(", f.IdCard AS idCard");//1
			sqlQuery.append(", f.AbbrPrefix As prefix");//2
			sqlQuery.append(", f.FirstName AS firstName");//3
			sqlQuery.append(", f.LastName AS lastName");//4
			sqlQuery.append(", d.ThaiName AS thaiName"); //5 district name
			sqlQuery.append(", p.BranchCode AS branchCode");//6
			sqlQuery.append(", b.BranchName AS branchName");//7
			sqlQuery.append(", p.BreedTypeId AS breedTypeId");//8
			sqlQuery.append(", t.BreedTypeName AS breedTypeName");//9
			sqlQuery.append(", p.PlantYear AS plantYear");//10
			sqlQuery.append(", p.PlantNo AS plantNo");//11
			sqlQuery.append(", p.RefPlantId As refPlantId");//12
			//sqlQuery.append(", p.PlantStatus AS PlantStatus");//13
			sqlQuery.append(", p.reason AS reason");//13
			sqlQuery.append(", LEFT(p.PlantStatus,1) AS plantStatus");//14
			sqlQuery.append(", p.status AS status");//15
			sqlQuery.append(", p.remark AS remark");//16
			sqlQuery.append(" FROM Plant AS p");
			sqlQuery.append(" LEFT JOIN BreedType AS t ON p.BreedTypeId = t.BreedTypeId");
			sqlQuery.append(" LEFT JOIN Farmer AS f ON p.IdCard = f.IdCard AND f.effectiveDate = p.effectiveDate");//(SELECT MAX(fm.effectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard)");
		    sqlQuery.append(" LEFT JOIN Branch AS b ON p.BranchCode = b.BranchCode");
		    sqlQuery.append(" LEFT JOIN Region AS r ON f.RegionNo = r.RegionNo");
		    sqlQuery.append(" LEFT JOIN Province AS prov ON f.ProvinceNo = prov.ProvinceNo");
		    sqlQuery.append(" LEFT JOIN District AS d ON f.DistrictNo = d.DistrictNo");
		    sqlQuery.append(" LEFT JOIN SubDistrict AS sub ON f.SubDistrictNo = sub.SubDistrictNo");
		    sqlQuery.append(" LEFT JOIN FarmerGroup AS fg ON p.FarmerGroupId2 = fg.FarmerGroupId");
		    sqlQuery.append(" LEFT JOIN FarmerGroupAddress AS fga ON fg.FarmerGroupId = fga.FarmerGroupId");
		    sqlQuery.append(" WHERE p.Flag <> 'D' AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId= p.RefPlantId)");
		    
		    if(breedTypeId > 0)
		    	sqlQuery.append(" AND p.BreedTypeId=:breedTypeId");
		    if(plantYear > 0)
		    	sqlQuery.append(" AND p.PlantYear=:plantYear");
		    if(plantNo > 0)
		    	sqlQuery.append(" AND p.PlantNo LIKE :plantNo");
		    if(!"".equals(idCard))
		    	sqlQuery.append(" AND p.IdCard LIKE :idCard");
		    
			
		    if(farmerGroupId != null && farmerGroupId.size() > 0)
		    	sqlQuery.append(" AND p.FarmerGroupId2 in (:farmerGroupId)");
		    
		    if(regionNo > 0)
		    	sqlQuery.append(" AND fga.RegionNo=:regionNo");
		    
		    if(provinceNo != null && provinceNo.size() > 0)
		    	sqlQuery.append(" AND fga.ProvinceNo in (:provinceNo)");
		    
		    if(districtNo > 0)
		    	sqlQuery.append(" AND fga.DistrictNo=:districtNo");
		    if(subDistrictNo > 0)
		    	sqlQuery.append(" AND fga.SubDistrictNo=:subDistrictNo");
		    if(!"".equals(firstName))
		    	sqlQuery.append(" AND f.FirstName LIKE :firstName");
		    if(!"".equals(lastName))
		    	sqlQuery.append(" AND f.LastName LIKE :lastName");
		    if(!"".equals(plantStatus))
		    	sqlQuery.append(" AND p.PlantStatus =:plantStatus");
		 
		    if(cooperativeGroupId != null && cooperativeGroupId.size() > 0)
		    	sqlQuery.append(" AND fg.FarmerGroupId in (:cooperativeGroupId)");
		    	
		    sqlQuery.append(" GROUP BY RefPlantId");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if(breedTypeId > 0)
		    	query.setLong("breedTypeId", breedTypeId);
		    if(plantYear > 0)
		    	query.setInteger("plantYear", plantYear);
		    if(plantNo > 0)
		    	query.setString("plantNo","%"+plantNo+"%");
		    if(!"".equals(idCard))
		    	query.setString("idCard","%"+idCard+"%");
			if(regionNo > 0)
		    	query.setLong("regionNo", regionNo);
			 if(provinceNo != null && provinceNo.size() > 0)
		    	query.setParameterList("provinceNo", provinceNo);
		    if(districtNo > 0)
		    	query.setLong("districtNo", districtNo);
		    if(subDistrictNo > 0)
		    	query.setLong("subDistrictNo", subDistrictNo);
		    if(farmerGroupId != null && farmerGroupId.size() > 0)
		    	query.setParameterList("farmerGroupId", farmerGroupId);
		    if(!"".equals(firstName))
		    	query.setString("firstName","%"+firstName+"%");
		    if(!"".equals(lastName))
		    	query.setString("lastName","%"+lastName+"%");
		    if(!"".equals(plantStatus))
		    	query.setString("plantStatus", plantStatus);
		    if(cooperativeGroupId != null && cooperativeGroupId.size() > 0)
		    	query.setParameterList("cooperativeGroupId", cooperativeGroupId);
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchPlantByCriteria failed", re);
			throw re;
		}	
	}
	
	
	public List getPlantYear(){
		String sql = "SELECT DISTINCT PlantYear FROM Plant ORDER BY PlantYear DESC";
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantYear failed", re);
			throw re;
		}	
	}
		
	public List getRefPlantId(String idCard, long refPlantId){
		StringBuffer sqlQuery = new StringBuffer();
		try{
			sqlQuery.append("SELECT DISTINCT RefPlantId FROM Plant WHERE IdCard=:idCard AND Flag <> 'D'");
			if(refPlantId > 0){
				sqlQuery.append(" AND RefPlantId <> :refPlantId");
			}
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("idCard", idCard);
			
			if(refPlantId > 0){
				query.setLong("refPlantId", refPlantId);
			}
			return query.list();
		} catch (RuntimeException re) {
			log.error("getRefPlantId failed", re);
			throw re;
		}	
	}
	
	public List getPlantNo(){
		String sql = "SELECT DISTINCT PlantNo FROM Plant ORDER BY PlantNo";
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantNo failed", re);
			throw re;
		}	
	}
	
	public boolean isDuplicate(long breedTypeId, int plantYear, int plantNo, String idCard, long refPlantId, long maxPlantId)
	{
		// if duplicate return true, otherwise false
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant as p ");
			sqlQuery.append("WHERE p.IdCard = :idCard "); 
			sqlQuery.append("AND p.PlantYear = :plantYear ");
			sqlQuery.append("AND p.PlantNo = :plantNo ");
			sqlQuery.append("AND p.BreedTypeId = :breedTypeId ");
			sqlQuery.append("AND p.PlantId = :maxPlantId ");
			log.debug("sqlQuery : " + sqlQuery);
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("idCard", idCard);
			query.setInteger("plantYear", plantYear);
			query.setInteger("plantNo", plantNo);
			query.setLong("breedTypeId", breedTypeId);
			query.setLong("maxPlantId", maxPlantId);
			if (query.list().size() > 0) 
				return true;
			else 
				return false;
		} catch (RuntimeException re) {
			log.error("isDuplicate failed", re);
			throw re;
		}	
		
	}
	
	public long getMaxPlantIdFromRefPlant(String idCard, long refPlantId){
		
		StringBuffer sqlQuery = new StringBuffer();
		try{
			sqlQuery.append("SELECT MAX(p.PlantId) as PlantId FROM Plant AS p WHERE p.IdCard=:idCard");
			sqlQuery.append(" and p.RefPlantId=:refPlantId AND p.Flag <> 'D'");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("idCard", idCard);
			query.setLong("refPlantId", refPlantId);
			long maxId = 0;
			maxId =  ((BigInteger)query.uniqueResult()).longValue();
			return maxId;
		} catch (RuntimeException re) {
			log.error("getMaxPlantIdFromRefPlant failed", re);
			throw re;
		}	
	}
	
	public boolean isDuplicateMainOfPlant(long breedTypeId, int plantYear, int plantNo, String idCard, long refPlantId)
	{
		// if duplicate return true, otherwise false		
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant as p");
			sqlQuery.append(" WHERE p.IdCard =:idCard"); 
			sqlQuery.append(" AND p.PlantYear =:plantYear");
			sqlQuery.append(" AND p.PlantNo =:plantNo");
			sqlQuery.append(" AND p.BreedTypeId =:breedTypeId");
			sqlQuery.append(" AND p.PlantId = (SELECT MAX(PlantId) FROM Plant WHERE RefPlantId = p.RefPlantId)");
			if(refPlantId > 0)
				sqlQuery.append(" AND p.RefPlantId <> '" + refPlantId + "'");
			sqlQuery.append(" AND p.Flag <> 'D'");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if(breedTypeId > 0)
			    query.setLong("breedTypeId", breedTypeId);
			if(plantYear > 0)
			    query.setInteger("plantYear", plantYear);
			if(plantNo > 0)
			    query.setInteger("plantNo", plantNo);
			if(!"".equals(idCard))
			    query.setString("idCard", idCard);
			
			log.debug("sqlQuery : " + sqlQuery);
			if (query.list().size() > 0) 
				return true;
			else 
				return false;
		} catch (RuntimeException re) {
			log.error("isDuplicateMainOfPlant failed", re);
			throw re;
		}	
		
	}
	
	public List getRefPlantId(String idCard, long refPlantId, long branchCode)
	{
		StringBuffer sqlQuery = new StringBuffer();
		try{
			sqlQuery.append("SELECT DISTINCT RefPlantId FROM Plant WHERE IdCard=:idCard AND Flag <> 'D'");
			if(refPlantId > 0)
				sqlQuery.append(" AND RefPlantId <> :refPlantId");
			if(branchCode > 0)
				sqlQuery.append(" AND BranchCode = :branchCode");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("idCard", idCard);
			if(refPlantId > 0)
				query.setLong("refPlantId", refPlantId);
			if(branchCode > 0)
				query.setLong("branchCode", branchCode);
			return query.list();
		} catch (RuntimeException re) {
			log.error("getRefPlantId failed", re);
			throw re;
		}	
	}
	
	public String getCustomerNo(String idCard, long branchCode, long refPlantId)
	{
		StringBuffer sqlQuery = new StringBuffer();
		try{
			sqlQuery.append("SELECT CustomerNo FROM Plant");
			sqlQuery.append(" WHERE PlantId = (SELECT MAX(PlantId) FROM Plant WHERE idCard=:idCard AND BranchCode=:branchCode)"); 
			sqlQuery.append(" AND IdCard=:idCard AND BranchCode=:branchCode AND Flag <> 'D'");
				
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("idCard", idCard);
			query.setLong("branchCode", branchCode);
			if(refPlantId > 0)
				query.setLong("refPlantId", refPlantId);
			
			String customerNo = "";
			if(query.uniqueResult() != null)
				customerNo =  ((String)query.uniqueResult()).toString();
			return customerNo;
		} catch (RuntimeException re) {
			log.error("getCustomer failed", re);
			throw re;
		}	
	}
	
	
	public List<Plant> getPlantNoList(long breedTypeId, String customerNo, int plantYear, String idCard, long refPlantId){
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT pt.PlantNo FROM Plant pt");
			sqlQuery.append(" WHERE pt.CustomerNo =:customerNo");
			sqlQuery.append(" AND pt.BreedTypeId =:breedTypeId");
			sqlQuery.append(" AND pt.IdCard =:idCard");
			sqlQuery.append(" AND pt.PlantYear =:plantYear");
			sqlQuery.append(" AND pt.plantId = (select max(p.plantid) from plant p where p.refplantid = pt.refplantid)");
			if(refPlantId > 0)
				sqlQuery.append(" AND pt.refPlantId <> " + refPlantId);//sqlQuery.append(" AND pt.refPlantId <> " + refPlantId);
			
			sqlQuery.append(" AND pt.Flag <> 'D' ORDER BY PlantNo ASC");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(customerNo))
				query.setString("customerNo", customerNo);
			if(breedTypeId > 0)
				query.setLong("breedTypeId", breedTypeId);
			if(!"".equals(idCard))
				query.setString("idCard", idCard);
			if(plantYear > 0)
				query.setInteger("plantYear", plantYear);
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantNoList failed", re);
			throw re;
		}
		
	}
	
	public Plant findByFarmerGroupId(long farmerGroupId, String idCard){
		
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant WHERE IdCard=:idCard AND FarmerGroupId=:farmerGroupId");
			sqlQuery.append(" AND EffectiveDate=(SELECT MAX(EffectiveDate) FROM Plant WHERE IdCard=:idCard AND FarmerGroupId=:farmerGroupId)");
			sqlQuery.append(" AND PlantId=(SELECT MAX(PlantId) FROM Plant WHERE IdCard=idCard AND FarmerGroupId=:farmerGroupId)");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Plant.class);			
			query.setString("idCard", idCard);
			query.setLong("farmerGroupId", farmerGroupId);
			return (Plant) query.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByFarmerGroupId failed", re);
			throw re;
		}
	}
	
	public void updateRefPlantId(long plantId, long refPlantId)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("update Plant set refPlantId=:refPlantId where plantId=:plantId");
			uQuery.setLong("plantId", plantId);
			uQuery.setLong("refPlantId", refPlantId);
			uQuery.executeUpdate();
			log.info("updateRefPlantId successful");
		}catch (RuntimeException re) {
			log.error("updateRefPlantId failed", re);
			throw re;
		}
	}

	public void deletePlant(long plantId)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("update Plant set flag='D' where plantId=:plantId");
			uQuery.setLong("plantId", plantId);
			uQuery.executeUpdate();
			log.info("deletePlant successful");
		}catch (RuntimeException re) {
			log.error("deletePlant failed", re);
			throw re;
		}
	}
	
	public List<Plant> findByFamerGroupId(long farmerGroupId) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant p ");
			sqlQuery.append("WHERE p.Flag <> 'D' ");
			sqlQuery.append("AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId = p.RefPlantId) ");
			sqlQuery.append("AND farmerGroupId = :farmerGroupId ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Plant.class);
			query.setLong("farmerGroupId", farmerGroupId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByFamerGroupId failed", re);
			throw re;
		}
	}
	
	public List<Plant> findByFamerGroupId2(long farmerGroupId2) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant p ");
			sqlQuery.append("WHERE p.Flag <> 'D' ");
			sqlQuery.append("AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId = p.RefPlantId) ");
			sqlQuery.append("AND farmerGroupId2 = :farmerGroupId2 ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Plant.class);
			query.setLong("farmerGroupId2", farmerGroupId2);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByFamerGroupId2 failed", re);
			throw re;
		}
	}
	
	public List<Plant> findByIdCard(String idCard) 
	{
		StringBuffer sqlQuery = new StringBuffer();
		try {			
			
			sqlQuery.append("SELECT * FROM Plant WHERE IdCard=:idCard AND Flag <> 'D'");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(idCard))
				query.setString("idCard", idCard);
			return query.list();
				
		} catch (RuntimeException re) {
			log.error("findByIdCard failed", re);
			throw re;
		}
	}
	
	public List<Plant> findByBankId(long bankId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Plant.class);
			crit.add(Restrictions.eq("bankId", bankId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBankId failed", re);
			throw re;
		}
	}
	
	public Object findByBankBranch(long bankId, long branchCode) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT COUNT(BranchCode) FROM Plant ");
			sqlQuery.append("WHERE BankId=:bankId AND BranchCode=:branchCode");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("bankId", bankId);
			query.setLong("branchCode", branchCode);
			return query.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByBankBranch failed", re);
			throw re;
		}
	}
	
	public boolean findbyCreateBy(String userName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM Plant ");
			sqlQuery.append("WHERE CreateBy = :userName ");
			sqlQuery.append("AND Flag <> 'D' ");
			sqlQuery.append("AND plantId IN (SELECT MAX(pt.plantId) FROM Plant pt ");
			sqlQuery.append("WHERE DATE_FORMAT(pt.CreateDate, '%Y/%m/%d') <= DATE_FORMAT(curdate(), '%Y/%m/%d')");
			sqlQuery.append("GROUP BY pt.refplantid)");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString("userName", userName);
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
	
	public List<Plant> getPlantList(int plantYear, int plantNo, String idCard){
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT (select lt.TypeName FROM landrighttype lt WHERE lt.TypeId = lr.TypeId) TypeName, ");
			sqlQuery.append("lr.DocNo, pt.PlantId, lr.TypeId, CONCAT(f.FirstName,' ',f.LastName) FarmerName ");
			sqlQuery.append("FROM Plant pt ");
			sqlQuery.append("LEFT JOIN Farmer f ON pt.IdCard = f.IdCard AND f.effectiveDate = pt.effectiveDate ");
			sqlQuery.append("LEFT JOIN LandRight lr ON pt.PlantId = lr.PlantId ");
			sqlQuery.append("WHERE pt.PlantYear = :plantYear ");
			sqlQuery.append("AND pt.PlantNo = :plantNo ");
			sqlQuery.append("AND pt.IdCard = :idCard ");
			sqlQuery.append("AND pt.plantId = (select max(p.plantid) from plant p where p.refplantid = pt.refplantid) ");
			sqlQuery.append("AND pt.Flag <> 'D' ORDER BY pt.PlantNo ASC");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(plantYear > 0)
				query.setInteger("plantYear", plantYear);
			if(plantNo > 0)
				query.setInteger("plantNo", plantNo);			
			if(!"".equals(idCard))
				query.setString("idCard", idCard);
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantList failed", re);
			throw re;
		}
	}
	
	public long getMaxPlantIdFromRefPlant(long refPlantId){
		
		StringBuffer sqlQuery = new StringBuffer();
		try{
			sqlQuery.append("SELECT MAX(p.PlantId) as PlantId FROM Plant AS p WHERE p.RefPlantId=:refPlantId AND p.Flag <> 'D' ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("refPlantId", refPlantId);
			long maxId = 0;
			if(query.uniqueResult()!=null)
				maxId =  ((BigInteger)query.uniqueResult()).longValue();
			
			return maxId;
		} catch (RuntimeException re) {
			log.error("getMaxPlantIdFromRefPlant failed", re);
			throw re;
		}	
	}
}
