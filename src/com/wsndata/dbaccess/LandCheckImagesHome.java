package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandCheckImages;

public class LandCheckImagesHome extends HibernateHome{
	private static final Logger log = Logger.getLogger(LandCheckImagesHome.class);
	
	public LandCheckImages findByImageId(long imageId){
		try{
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandCheckImages.class);
			crit.add(Restrictions.eq("imageId", imageId));
			return  (LandCheckImages)crit.uniqueResult();
		}catch (RuntimeException re) {
			log.error("findByImageId failed", re);
			throw re;
		}
	}
	
	public List<LandCheckImages> findImageByLandCheckId(long landCheckId){
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from LandCheckImages where landCheckId=:landCheckId").addEntity(LandCheckImages.class);
			query.setLong("landCheckId", landCheckId);
			return query.list();
		}catch(RuntimeException re){
			log.error("findImageByLandCheckId failed", re);
			throw re;
		}
	}
	
	public void deleteImagebyLandCheckId(long landCheckId){
		try{
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("delete from LandCheckImages where LandCheckId=:landCheckId");
			Query delete = sessionFactory.getCurrentSession().createSQLQuery(strSQL.toString());			
			delete.setLong("landCheckId", landCheckId);
			delete.executeUpdate();
		}catch (RuntimeException re) {
			log.error("deleteImagebyLandCheckId failed", re);
			throw re;
		}
	}
	
	public void deleteImagebyImageId(long imgId){
		try{
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("delete from LandCheckImages where ImageId=:imageId");
			Query delete = sessionFactory.getCurrentSession().createSQLQuery(strSQL.toString());			
			delete.setLong("imageId", imgId);
			delete.executeUpdate();
		}catch (RuntimeException re) {
			log.error("deleteImagebyImageId failed", re);
			throw re;
		}
	}
}
