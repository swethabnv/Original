package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.AssetDetail;

public class AssetDetailHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(AssetDetailHome.class);
	public List<AssetDetail> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(AssetDetail.class);
			crit.addOrder(Order.asc("assetDate"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public AssetDetail findByCriteria(long assetId,long plantId,long seq) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(AssetDetail.class);
			if (assetId>0) {
				crit.add(Restrictions.eq("assetId", assetId));	
			}
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (seq>0) {
				crit.add(Restrictions.eq("seq", seq));	
			}
			return (AssetDetail)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	public List<AssetDetail> findByAssetID(long assetId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(AssetDetail.class);
			crit.add(Restrictions.eq("assetId", assetId));	
			return (List)crit.list();
		} catch (RuntimeException re) {
			log.error("findByAssetID failed", re);
			throw re;
		}
	}
}
