package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.SellingDetail;

public class SellingDetailHome  extends HibernateHome {

	private static final Logger log = Logger.getLogger(SellingDetailHome.class);
	
	public List<SellingDetail> findByRegionNo(long regionNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SellingDetail.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	public List<SellingDetail> findByRegionAndProvince(long regionNo,long provinceNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SellingDetail.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvince failed", re);
			throw re;
		}
	}
	
	public List<SellingDetail> findByRegionAndProvinceAndDistrict(long regionNo,long provinceNo, long districtNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SellingDetail.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	
	public List<SellingDetail> findByRegionAndProvinceAndDistrictAndSubDistrict(long regionNo,long provinceNo, long districtNo, long subDistrictNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SellingDetail.class);	
			crit.add(Restrictions.eq("regionNo", regionNo));	
			crit.add(Restrictions.eq("provinceNo", provinceNo));	
			crit.add(Restrictions.eq("districtNo", districtNo));
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionAndProvinceAndDistrict failed", re);
			throw re;
		}
	}
	public List<SellingDetail> findByBreedTypeId(long breedTypeId)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(SellingDetail.class);
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByBreedTypeId failed", re);
			throw re;
		}
	}
}
