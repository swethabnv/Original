package com.wsndata.dbaccess;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandRight;


public class LandRightHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(LandRightHome.class);
	public List<LandRight> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	public List<LandRight> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	public List<LandRight> findbyPlantDetail(long plantId, long breedTypeId, long breedGroupId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (breedTypeId>0) {
				crit.add(Restrictions.eq("breedTypeId", breedTypeId));	
			}
			if (breedGroupId>0) {
				crit.add(Restrictions.eq("breedGroupId", breedGroupId));	
			}
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public LandRight findByCriteria(long plantId, long typeId, int seq, String docNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);
				crit.add(Restrictions.eq("plantId", plantId));	
				crit.add(Restrictions.eq("typeId", typeId));		
				crit.add(Restrictions.eq("docNo", docNo));
				if(seq>0)
					crit.add(Restrictions.eq("seq", seq));	
			return (LandRight)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public int getSeqbyPlantId(long plantId) 
	{
		try {			
			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT MAX(SEQ) AS Seq FROM LandRight WHERE ");
			sqlQuery.append("plantId ='"+plantId+"'");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if (query.uniqueResult()==null) {
				return 0;
			}
			BigDecimal maxId = (BigDecimal)query.uniqueResult();
			log.debug("getSeqbyPlantId successful"); 
			return maxId.intValue();
		}catch (RuntimeException re) {
			log.error("getSeqbyPlantId failed", re);
			throw re;
		}
	}
	
	
	public List<LandRight> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<LandRight> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<LandRight> findByRegionAndProvinceAndDistrictAndSubDistrict(long regionNo, long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);	
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
	
	public List<LandRight> findByBreedTypeId(long breedTypeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
	
	public List<LandRight> findByPlantId(long plantId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandRight.class);
			crit.add(Restrictions.eq("plantId", plantId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByPlantId failed", re);
			throw re;
		}
	}
	
	public void deleteLandright(long plantId)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM LandRight WHERE plantId=:plantId");
			uQuery.setLong("plantId", plantId);
			uQuery.executeUpdate();
			log.info("deleteLandright successful");
		}catch (RuntimeException re) {
			log.error("deleteLandright failed", re);
			throw re;
		}
	}
	
	public Object findByPrimaryKey(long plantId, long typeId, String docNo) 
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT (select lt.TypeName FROM landrighttype lt WHERE lt.TypeId = lr.TypeId) TypeName, ");
			sqlQuery.append("lr.DocNo, lr.PlantId, lr.TypeId, lr.DocRai, lr.DocNgan, lr.DocWah ");
			sqlQuery.append("FROM LandRight lr ");
			sqlQuery.append("WHERE lr.PlantId = :plantId ");
			sqlQuery.append("AND lr.TypeId = :typeId ");
			sqlQuery.append("AND lr.DocNo = :docNo ");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(plantId > 0)
				query.setLong("plantId", plantId);
			if(typeId > 0)
				query.setLong("typeId", typeId);
			if(!"".equals(docNo))
				query.setString("docNo", docNo);
			return query.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByPrimaryKey failed", re);
			throw re;
		}
	}
	
	public List findLandRightByCondition(int plantYear, String idCard, int plantNo, long farmerGroupId){
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT p.PlantNo, p.PlantYear, p.Idcard, p.BreedTypeId, REPLACE(lr.DocNo, ',', '&'), lr.TypeId, lr.PlantId, lr.DocRai, ");
			sqlQuery.append("lr.DocNgan, lr.DocWah, lr.SEQ, lt.TypeName, f.FirstName, f.LastName, p.RefPlantId ");
			sqlQuery.append("FROM LandRight lr ");
			sqlQuery.append("JOIN Plant p ON lr.PlantId = p.PlantId AND p.Flag <> 'D' ");
			sqlQuery.append("JOIN LandrightType lt ON lr.TypeId = lt.TypeId ");
			sqlQuery.append("JOIN Farmer f ON p.IdCard = f.IdCard ");
			sqlQuery.append("AND p.EffectiveDate = f.EffectiveDate ");
			sqlQuery.append("WHERE p.PlantNo = :plantNo AND p.PlantYear = :plantYear AND p.IdCard = :idCard ");
			if(farmerGroupId > 0)
				sqlQuery.append("AND p.FarmerGroupId2 = :farmerGroupId ");
			sqlQuery.append("AND p.PlantId in (select max(PlantId) from plant where PlantNo = p.PlantNo and PlantYear = p.PlantYear ");
			sqlQuery.append("GROUP BY refplantid) ");
			sqlQuery.append("ORDER BY lr.PlantId, lr.SEQ ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setInteger("plantNo", plantNo);
			query.setInteger("plantYear", plantYear);
			query.setString("idCard", idCard);
			if(farmerGroupId > 0)
				query.setLong("farmerGroupId", farmerGroupId);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findLandRightByCondition failed", re);
			throw re;
		}
	}
	
	public List searchApproveList(String idCard, String firstName, String lastName, int plantYear, int plantNo, String startDate, String endDate, 
			 String approved, String approvedDate, long farmerGroupId)
	{
		try {
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT p.IdCard, p.PlantYear, p.PlantNo, CONCAT(f.FirstName,' ',f.LastName) as FarmerName, ");
			sqlQuery.append("CONCAT(IFNULL(lt.TypeName,''),'/',lr.DocNo) LandRight, lr.Approved, ");
			sqlQuery.append("DATE_FORMAT(DATE_ADD(lr.ApprovedDate, INTERVAL 543 year), '%d/%m/%Y') ApprovedDate, lr.ApprovedBy, p.PlantId, lr.SEQ, lt.TypeId, lr.DocNo ");
			sqlQuery.append("FROM plant p LEFT JOIN farmer f ON p.IdCard=f.IdCard ");
			sqlQuery.append("LEFT JOIN landright lr ON lr.PlantId=p.PlantId ");
			sqlQuery.append("LEFT JOIN landrighttype lt ON lt.TypeId=lr.TypeId ");
			sqlQuery.append("WHERE p.Flag <> 'D' AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId= p.RefPlantId) AND ((lt.TypeName AND lr.DocNo) IS NOT NULL)  ");
						
			if(!idCard.equals(""))
				sqlQuery.append("AND p.IdCard=:idCard ");
			if(!firstName.equals(""))
				sqlQuery.append("AND f.FirstName LIKE :firstName ");
			if(!lastName.equals(""))
				sqlQuery.append("AND f.LastName LIKE :lastName ");
			if(plantYear>0)
				sqlQuery.append("AND p.PlantYear=:plantYear ");
			if(plantNo>0)
				sqlQuery.append("AND p.PlantNo=:plantNo ");
			if(!startDate.equals(""))
				sqlQuery.append("AND DATE_ADD(lr.ApprovedDate,interval  543 year) >= STR_TO_DATE(:startDate '00:00:00', '%d/%m/%Y %H:%i:%s') ");
			if(!endDate.equals(""))
				sqlQuery.append("AND DATE_ADD(lr.ApprovedDate,interval  543 year) <= STR_TO_DATE(:endDate '23:59:59', '%d/%m/%Y %H:%i:%s') ");
			if(approved!=null&&!approved.equals("")&&!approved.equals("W"))
				sqlQuery.append("AND lr.Approved LIKE :approved ");
			if(approved.equals("W"))
				sqlQuery.append("AND (lr.Approved is null ) ");
			if(farmerGroupId>0)
				sqlQuery.append("AND p.FarmerGroupId2=:farmerGroupId ");
			
			sqlQuery.append("ORDER BY p.idCard");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
			if(!idCard.equals(""))
				query.setString("idCard", idCard);
			if(!firstName.equals(""))
				query.setString("firstName", "%"+firstName+"%");
			if(!lastName.equals(""))
				query.setString("lastName", "%"+lastName+"%");
			if(plantYear>0)
				query.setInteger("plantYear",plantYear);
			if(plantNo>0)
				query.setInteger("plantNo", plantNo);
			if(!startDate.equals(""))
				query.setString("startDate", startDate);
			if(!endDate.equals(""))
				query.setString("endDate", endDate);
			if(approved!=null&&!approved.equals("")&&!approved.equals("W"))
				query.setString("approved",approved );
			if(farmerGroupId>0)
				query.setLong("farmerGroupId", farmerGroupId);
			
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteriaApproveList failed", re);
			throw re;
		}
		
	}
	
	public void setApproved(int plantId, int SEQ, int typeId, String docNo, String approveResult, String userName, String approvedDate){
		
	try {
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("UPDATE landright SET Approved =:approveResult, ApprovedBy =:userName, ApprovedDate =:approvedDate WHERE plantId =:plantId and SEQ =:SEQ and  TypeId=:typeId and DocNo LIKE :docNo ");
			uQuery.setString("approveResult", approveResult);
			uQuery.setString("userName", userName);
			uQuery.setString("approvedDate", approvedDate);
			uQuery.setLong("plantId", plantId);
			uQuery.setInteger("SEQ", SEQ);
			uQuery.setLong("typeId", typeId);
			uQuery.setString("docNo", docNo);
			uQuery.executeUpdate();
			log.info("setApproved successful");
			
	}catch (RuntimeException re) {
		log.error("setApproved failed", re);
		throw re;
	}
	}	
			
}
