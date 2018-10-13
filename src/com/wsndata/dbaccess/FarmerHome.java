package com.wsndata.dbaccess;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Branch;
import com.wsndata.data.Farmer;


public class FarmerHome extends HibernateHome {
	private static final Logger log = Logger.getLogger(FarmerHome.class);
	public List<Farmer> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
	public List<Farmer> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));		
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	
	public List<Farmer> findByName(String name)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);
			crit.add(Restrictions.like("firstName", name, MatchMode.ANYWHERE));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByName failed", re);
			throw re;
		}
	}
	
	public List findByCriteria(String idCard, String firstName, String lastName,List branch,long branchCode)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			String bCode = "";
			for (int i = 0; i < branch.size(); i++) {
				if (i+1==branch.size()) {
					bCode += (((Branch)branch.get(i)).getBranchCode());
					break;
				}
				bCode += (((Branch)branch.get(i)).getBranchCode())+",";
			}

			sqlQuery.append("SELECT pf.FullPrefix, f.*, p.ThaiName as provinceName, d.ThaiName as districtName, ");
			sqlQuery.append("s.ThaiName as subDistrictName FROM Farmer f left join Prefix pf on f.AbbrPrefix = pf.AbbrPrefix ");
			sqlQuery.append("LEFT JOIN province p ON f.ProvinceNo = p.ProvinceNo ");
			sqlQuery.append("LEFT JOIN district d ON f.DistrictNo = d.DistrictNo ");
			sqlQuery.append("LEFT JOIN subdistrict s ON f.SubDistrictNo = s.SubDistrictNo ");
			sqlQuery.append(" WHERE f.effectiveDate = (SELECT MAX(fm.effectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard ) ");
			if(!"".equals(idCard))
				sqlQuery.append(" AND f.idCard LIKE :idCard");
			if(!"".equals(firstName))
				sqlQuery.append(" AND f.firstName LIKE :firstName");
			if(!"".equals(lastName))
				sqlQuery.append(" AND f.lastName LIKE :lastName");
			if (branchCode>0)
				sqlQuery.append(" AND f.branchCode = :branchCode");
			sqlQuery.append(" ORDER BY f.IdCard ASC"); 
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(idCard))
				query.setString("idCard", ""+idCard+"%");
			if(!"".equals(firstName))
				query.setString("firstName", ""+firstName+"%");
			if(!"".equals(lastName))
				query.setString("lastName", ""+lastName+"%");
			if (branchCode>0)
				query.setLong("branchCode", branchCode);
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public List findByCriteria2(String idCard, String firstName, String lastName)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();

			sqlQuery.append("SELECT pf.FullPrefix, pt.MemberNo, pt.MemberNo2, f.IdCard, f.EffectiveDate, f.AbbrPrefix, ");
			sqlQuery.append("f.FirstName, f.LastName, p.ThaiName as provinceName, d.ThaiName as districtName, ");
			sqlQuery.append("s.ThaiName as subDistrictName, fg.FarmerGroupName FROM Farmer f ");
			sqlQuery.append("LEFT JOIN Prefix pf on f.AbbrPrefix = pf.AbbrPrefix ");
			sqlQuery.append("LEFT JOIN Plant pt ON f.IdCard = pt.IdCard ");
			sqlQuery.append("LEFT JOIN Province p ON f.ProvinceNo = p.ProvinceNo ");
			sqlQuery.append("LEFT JOIN District d ON f.DistrictNo = d.DistrictNo ");
			sqlQuery.append("LEFT JOIN SubDistrict s ON f.SubDistrictNo = s.SubDistrictNo ");
			sqlQuery.append("LEFT JOIN FarmerGroupFarmer ff ON f.IdCard = ff.IdCard ");
			sqlQuery.append("LEFT JOIN FarmerGroup fg ON ff.FarmerGroupId = fg.FarmerGroupId AND fg.FarmerGroupType='F' ");
			sqlQuery.append("WHERE f.EffectiveDate = (SELECT MAX(fm.EffectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard ) ");
			if(!"".equals(idCard))
				sqlQuery.append("AND f.IdCard LIKE :idCard ");
			if(!"".equals(firstName))
				sqlQuery.append("AND f.FirstName LIKE :firstName ");
			if(!"".equals(lastName))
				sqlQuery.append("AND f.LastName LIKE :lastName ");
			sqlQuery.append("GROUP BY f.IdCard ORDER BY f.AbbrPrefix "); 
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(idCard))
				query.setString("idCard", ""+idCard+"%");
			if(!"".equals(firstName))
				query.setString("firstName", ""+firstName+"%");
			if(!"".equals(lastName))
				query.setString("lastName", ""+lastName+"%");
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("findByCriteria2 failed", re);
			throw re;
		}
	}
	
	public List findByCriteria3(String idCard, String firstName, String lastName, long regionNo, long provinceNo, long districtNo, long subDistrictNo)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();

			sqlQuery.append("SELECT pf.FullPrefix, f.*, p.ThaiName as provinceName, d.ThaiName as districtName, ");
			sqlQuery.append("s.ThaiName as subDistrictName FROM Farmer f ");
			sqlQuery.append("LEFT JOIN Prefix pf on f.AbbrPrefix = pf.AbbrPrefix ");
		    sqlQuery.append("LEFT JOIN Region r ON f.RegionNo = r.RegionNo ");
			sqlQuery.append("LEFT JOIN Province p ON f.ProvinceNo = p.ProvinceNo ");
			sqlQuery.append("LEFT JOIN District d ON f.DistrictNo = d.DistrictNo ");
			sqlQuery.append("LEFT JOIN SubDistrict s ON f.SubDistrictNo = s.SubDistrictNo ");
			sqlQuery.append("WHERE f.EffectiveDate = (SELECT MAX(fm.EffectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard ) ");
			//--- Add condition for select farmer have plant information, Not select all --- Update 2015/02/17
			sqlQuery.append("AND f.IdCard IN (SELECT DISTINCT p.IdCard FROM Plant p WHERE p.Flag <> 'D' ");
			sqlQuery.append("AND p.CreateDate = (select max(pl.CreateDate) from Plant pl WHERE pl.RefPlantId= p.RefPlantId)) ");
			//--- End update 2015/02/17
			if(!"".equals(idCard))
				sqlQuery.append("AND f.IdCard LIKE :idCard ");
			if(!"".equals(firstName))
				sqlQuery.append("AND f.FirstName LIKE :firstName ");
			if(!"".equals(lastName))
				sqlQuery.append("AND f.LastName LIKE :lastName ");
			if(regionNo > 0)
				sqlQuery.append("AND f.regionNo=:regionNo "); 
			if(provinceNo > 0)
				sqlQuery.append("AND f.provinceNo=:provinceNo "); 
			if(districtNo > 0)
				sqlQuery.append("AND f.districtNo=:districtNo ");
			if(subDistrictNo > 0)
				sqlQuery.append("AND s.subDistrictNo=:subDistrictNo ");
			sqlQuery.append("ORDER BY f.AbbrPrefix "); 
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(idCard))
				query.setString("idCard", "%"+idCard+"%");
			if(!"".equals(firstName))
				query.setString("firstName", "%"+firstName+"%");
			if(!"".equals(lastName))
				query.setString("lastName", "%"+lastName+"%");
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
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public Farmer findByKey(String idCard, Date effectiveDate) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);
			crit.add(Restrictions.eq("idCard", idCard));
			crit.add(Restrictions.eq("effectiveDate", effectiveDate));
			return (Farmer) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByKey failed", re);
			throw re;
		}
	}
	
	
	public Farmer findFarmerDetail(Farmer f){
		Farmer farmer = null;
		try{
			StringBuffer buff = new StringBuffer();
			buff.append("select * from Farmer where IdCard=? and AbbrPrefix=? and FirstName=? and ");
			buff.append("LastName=? and AddressNo=? and Moo=? and PostCode=? and Tel=? and Mobile=? and Email=? and ");
			buff.append("RegionNo=? and ProvinceNo=? and DistrictNo=? and SubDistrictNo=? and Soi=? and Street=? and Village=?");
			buff.append("order by EffectiveDate desc Limit 1");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(buff.toString()).addEntity(Farmer.class);
			query.setString(0, f.getIdCard());
			query.setString(1, f.getAbbrPrefix());
			query.setString(2, f.getFirstName());
			query.setString(3, f.getLastName());
			query.setString(4, f.getAddressNo());
			query.setInteger(5, f.getMoo());
			query.setInteger(6, f.getPostCode());
			query.setString(7, f.getTel());
			query.setString(8, f.getMobile());
			query.setString(9, f.getEmail());
			query.setLong(10, f.getRegionNo());
			query.setLong(11, f.getProvinceNo());
			query.setLong(12, f.getDistrictNo());
			query.setLong(13, f.getSubDistrictNo());
			query.setString(14, f.getSoi());
			query.setString(15, f.getStreet());
			query.setString(16, f.getVillage());
			farmer = (Farmer)query.uniqueResult();
		}catch(RuntimeException re){
			log.error("findFarmerDetail failed", re);
		}
		return farmer;
	}
	
	public boolean findbyTable(String table, String idCard, String effectiveDate)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM " + table);
			sqlQuery.append(" WHERE idCard = ?");
			if(!"".equals(effectiveDate))
				sqlQuery.append(" AND effectiveDate = :effectiveDate");
			if("plant".equals(table.toLowerCase()))
				sqlQuery.append(" AND flag <> 'D'"); // stay plant
				
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, idCard);
			if(!"".equals(effectiveDate))
				query.setString("effectiveDate", effectiveDate);
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
	
	public boolean isDelete(String idCard,String effectiveDate)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append(" SELECT * FROM Plant");
			sqlQuery.append(" WHERE idCard = ?");
			if(!"".equals(effectiveDate))
				sqlQuery.append(" AND effectiveDate = :effectiveDate");
			sqlQuery.append(" AND flag = 'D'"); // stay plant
				
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, idCard);
			if(!"".equals(effectiveDate))
				query.setString("effectiveDate", effectiveDate);
			if (query.list().size()==0) {
				return false;
			}else {
				return true;
			}
		} catch (RuntimeException re) {
			log.error("isDelete failed", re);
			throw re;
		}
		
	}
	
	public void delFarmer(String idCard)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("delete from Farmer where idCard=?");
			uQuery.setString(0, idCard);
			uQuery.executeUpdate();
			log.info("delFarmer successful");
		}catch (RuntimeException re) {
			log.error("delFarmer failed", re);
			throw re;
		}
	}
	
	
	public List<Farmer> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	
	public List<Farmer> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<Farmer> findByRegionAndProvinceAndDistrictAndSubDistrict(long regionNo,long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Farmer.class);	
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
	
	public List findByCriteriaAndGetCustomerNo(String idCard, String firstName, String lastName,List branch,long branchCode)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			String bCode = "";
			for (int i = 0; i < branch.size(); i++) {
				if (i+1==branch.size()) {
					bCode += (((Branch)branch.get(i)).getBranchCode());
					break;
				}
				bCode += (((Branch)branch.get(i)).getBranchCode())+",";
			}
			
			sqlQuery.append("SELECT pref.FullPrefix, f.*, p.CustomerNo FROM Farmer f ");
			sqlQuery.append(" LEFT JOIN Plant p on f.IdCard = p.IdCard");
			sqlQuery.append(" LEFT JOIN Prefix pref on f.AbbrPrefix = pref.AbbrPrefix ");
			sqlQuery.append(" WHERE f.effectiveDate = (SELECT MAX(fm.effectiveDate) FROM Farmer fm WHERE fm.IdCard = f.IdCard ) ");
			if(!"".equals(idCard))
				sqlQuery.append(" AND f.idCard =:idCard");
			if(!"".equals(firstName))
				sqlQuery.append(" AND f.firstName LIKE :firstName");
			if(!"".equals(lastName))
				sqlQuery.append(" AND f.lastName LIKE :lastName");
			if (branchCode>0)
				sqlQuery.append(" AND f.branchCode =:branchCode");
			
			sqlQuery.append(" AND p.plantId = (SELECT MAX(pl.plantId) FROM Plant pl WHERE pl.IdCard =:idCard AND pl.Flag <> 'D' )");
			sqlQuery.append(" AND p.Flag <> 'D'"); 
			sqlQuery.append(" ORDER BY f.IdCard ASC"); 
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(idCard))
				query.setString("idCard", idCard);
			if(!"".equals(firstName))
				query.setString("firstName", firstName+"%");
			if(!"".equals(lastName))
				query.setString("lastName", lastName+"%");
			if (branchCode>0)
				query.setLong("branchCode", branchCode);
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("findByCriteriaAndGetCustomerNo failed", re);
			throw re;
		}
	}
	
}
