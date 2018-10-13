package com.wsndata.dbaccess;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.FarmerGroupFarmer;

public class FarmerGroupFarmerHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(FarmerGroupFarmerHome.class);
	public List<FarmerGroupFarmer> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupFarmer.class);
			crit.addOrder(Order.asc("farmerGroupId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupFarmer> findByfarmerGroup(long farmerGroupId)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT * FROM FarmerGroupFarmer fg ");
			//sqlQuery.append("WHERE fg.EffectiveDate = (SELECT MAX(fm.EffectiveDate) FROM FarmerGroupFarmer fm WHERE fm.IdCard = fg.IdCard) ");
			if(!"".equals(farmerGroupId))
				sqlQuery.append("WHERE fg.farmerGroupId = :farmerGroupId ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroupFarmer.class);
			if(!"".equals(farmerGroupId))
				query.setLong("farmerGroupId", farmerGroupId);
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("findByfarmerGroup failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupFarmer> findByfarmerGroup2(long farmerGroupId)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT ff.* FROM FarmerGroupFarmer ff ");
			//sqlQuery.append("WHERE fg.EffectiveDate = (SELECT MAX(fm.EffectiveDate) FROM FarmerGroupFarmer fm WHERE fm.IdCard = fg.IdCard) ");
			if(!"".equals(farmerGroupId))
				sqlQuery.append("WHERE ff.FarmerGroupId IN (SELECT fc.FarmerGroupId FROM farmergroupchild fc WHERE fc.ChildFarmerGroupId=:farmerGroupId) UNION SELECT ff.* from FarmerGroupFarmer As ff WHERE ff.FarmerGroupId=:farmerGroupId ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(FarmerGroupFarmer.class);
			if(!"".equals(farmerGroupId))
				query.setLong("farmerGroupId", farmerGroupId);
			return query.list();
		
		} catch (RuntimeException re) {
			log.error("findByfarmerGroup2 failed", re);
			throw re;
		}
	}
	
	public FarmerGroupFarmer findByKey(String idCard, Date effectiveDate)
	{	
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupFarmer.class);
			crit.add(Restrictions.eq("idCard", idCard));
			crit.add(Restrictions.eq("effectiveDate", effectiveDate));
			return (FarmerGroupFarmer) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByKey failed", re);
			throw re;
		}
	}
	
	public FarmerGroupFarmer findById(String idCard){
		
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupFarmer.class);
			crit.add(Restrictions.eq("idCard", idCard));
			return (FarmerGroupFarmer) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupFarmer> findByIdCardAndFarmerGroupId(String idCard, long farmerGroupId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupFarmer.class);
			crit.add(Restrictions.eq("idCard", idCard));
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByIdCardAndFarmerGroupId failed", re);
			throw re;
		}
	}
	
	
	public void delFarmerGroup(long farmerGroupId, String idCard)	{
		
		try {			
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery("delete from FarmerGroupFarmer where farmerGroupId=:farmerGroupId and idCard=:idCard");
			uQuery.setLong("farmerGroupId", farmerGroupId);
			uQuery.setString("idCard", idCard);
			uQuery.executeUpdate();
			log.info("delFarmerGroup successful");
		}catch (RuntimeException re) {
			log.error("delFarmerGroup failed", re);
			throw re;
		}
	}
	
	public List<FarmerGroupFarmer> findByFamerGroupId(long farmerGroupId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(FarmerGroupFarmer.class);
			crit.add(Restrictions.eq("farmerGroupId", farmerGroupId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByFamerGroupId failed", re);
			throw re;
		}
	}
}
