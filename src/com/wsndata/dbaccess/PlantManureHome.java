package com.wsndata.dbaccess;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PlantManure;

public class PlantManureHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PlantManureHome.class);
	public List<PlantManure> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantManure.class);
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public PlantManure findByCriteria(long typeId,long plantId,int seq) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantManure.class);
			if (typeId>0) {
				crit.add(Restrictions.eq("typeId", typeId));	
			}
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (seq>0) {
				crit.add(Restrictions.eq("seq", seq));	
			}
			return (PlantManure)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public List getPlantManureList(){
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT mType.TypeId");
			sqlQuery.append(", mType.TypeName");
			sqlQuery.append(", pManure.ManureName");
			sqlQuery.append(", pManure.Formula");
			sqlQuery.append(", pManure.QtyPerRai");
			sqlQuery.append(", pManure.CostPerRai");
			sqlQuery.append(", pManure.Seq ");
			sqlQuery.append(", pManure.PlantId");
			sqlQuery.append(" FROM ManureType as mType");
			sqlQuery.append(" LEFT JOIN PlantManure as pManure");
			sqlQuery.append(" ON mType.TypeId = pManure.TypeId");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantManureList failed", re);
			throw re;
		}
		
	}
	
	public List<PlantManure> findByTypeId(long typeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantManure.class);
			crit.add(Restrictions.eq("typeId", typeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByTypeId failed", re);
			throw re;
		}
	}
	
}
