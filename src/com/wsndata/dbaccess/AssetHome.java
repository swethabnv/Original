package com.wsndata.dbaccess;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Asset;

public class AssetHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(AssetHome.class);
	public List<Asset> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Asset.class);
			crit.addOrder(Order.asc("assetName"));
			crit.add(Restrictions.or(Restrictions.isNull("isTotal"), Restrictions.eq("isTotal", "N")));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Asset> searchByAssetName(String assetName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Asset.class);
			if(assetName !=null && !"".equals(assetName)){
				crit.add(Restrictions.like("assetName", assetName, MatchMode.ANYWHERE));
			}
			crit.add(Restrictions.or(Restrictions.isNull("isTotal"), Restrictions.eq("isTotal", "N")));
			crit.addOrder(Order.asc("assetName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("searchByAssetName failed", re);
			throw re;
		}
	}
	
	public Asset findByAssetId(long assetId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Asset.class);	
			crit.add(Restrictions.eq("assetId", assetId));	
			return (Asset)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByAssetId failed", re);
			throw re;
		}
	}
	
	public Asset checkTotal() 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Asset.class);	
			crit.add(Restrictions.eq("isTotal", "Y"));	
			return (Asset)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("checkTotal failed", re);
			throw re;
		}
	}
	
	public Asset findByAssetName(String assetName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Asset.class);	
			crit.add(Restrictions.eq("assetName", assetName));	
			return (Asset)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByAssetName failed", re);
			throw re;
		}
	}
}
