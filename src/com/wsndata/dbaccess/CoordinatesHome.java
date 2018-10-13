package com.wsndata.dbaccess;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Coordinates;

public class CoordinatesHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(CoordinatesHome.class);
	public List<Coordinates> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Coordinates.class);
			crit.addOrder(Order.asc("landCheckId"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	public List<Coordinates> findByLandCheckId(long landCheckId) 
	{
		try {	
			Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from Coordinates where landCheckId=:landCheckId").addEntity(Coordinates.class);
			query.setLong("landCheckId", landCheckId);	
			return query.list();
		} catch (RuntimeException re) {
			log.error("findByLandCheckId failed", re);
			throw re;
		}
	}
	
	public Coordinates findByCoordinatesId(long coordinatesId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Coordinates.class);	
			crit.add(Restrictions.eq("coordinatesId", coordinatesId));	
			return (Coordinates)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCoordinatesId failed", re);
			throw re;
		}
	}
	
	public List<Coordinates> findByCoordinatesKey(int plantYear, int plantNo, String idCard, long typeId, long breedTypeId, String docNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Coordinates.class);	
			crit.add(Restrictions.eq("plantYear", plantYear));	
			crit.add(Restrictions.eq("plantNo", plantNo));	
			crit.add(Restrictions.eq("idCard", idCard));	
			crit.add(Restrictions.eq("typeId", typeId));	
			crit.add(Restrictions.eq("docNo", docNo));	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByCoordinatesKey failed", re);
			throw re;
		}
	}
	
	public Coordinates findOneByCoordinatesKey(int plantYear, int plantNo, String idCard, long typeId, long breedTypeId, String docNo) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Coordinates.class);	
			crit.add(Restrictions.eq("plantYear", plantYear));	
			crit.add(Restrictions.eq("plantNo", plantNo));	
			crit.add(Restrictions.eq("idCard", idCard));	
			crit.add(Restrictions.eq("typeId", typeId));	
			crit.add(Restrictions.eq("docNo", docNo));	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.setMaxResults(1);
			return (Coordinates)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCoordinatesKey failed", re);
			throw re;
		}
	}
	
	public List<Coordinates> findDuplicateByKey(int plantYear, int plantNo, String idCard, long typeId, long breedTypeId, String docNo, String latitude, String longitude) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Coordinates.class);	
			crit.add(Restrictions.eq("plantYear", plantYear));	
			crit.add(Restrictions.eq("plantNo", plantNo));	
			crit.add(Restrictions.eq("idCard", idCard));	
			crit.add(Restrictions.eq("typeId", typeId));	
			crit.add(Restrictions.eq("docNo", docNo));	
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.add(Restrictions.eq("latitude", latitude));
			crit.add(Restrictions.eq("longitude", longitude));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByCoordinatesKey failed", re);
			throw re;
		}
	}
	
	public void saveCoordinates(Coordinates coordinates) 
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("INSERT INTO Coordinates ");
		    sqlQuery.append("(latitude, longitude, seq, plantYear, plantNo, idCard, typeId, docNo, breedTypeId) " +
		    		"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setString(0, coordinates.getLatitude());
			query.setString(1, coordinates.getLongitude());
			query.setInteger(2, coordinates.getSeq());
			query.setInteger(3, coordinates.getPlantYear());
			query.setInteger(4, coordinates.getPlantNo());
			query.setString(5, coordinates.getIdCard());
			query.setLong(6, coordinates.getTypeId());
			query.setString(7, coordinates.getDocNo());
			query.setLong(8, coordinates.getBreedTypeId());
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("saveCoordinates failed", re);
			throw re;
		}
	}
	
	public void deleteCoordinates(long coordinatesId)	{
		try {
			String sqlQuery = "DELETE FROM Coordinates WHERE CoordinatesId=:coordinatesId";
			Query uQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			uQuery.setLong("coordinatesId", coordinatesId);
			uQuery.executeUpdate();
			log.info("deleteCoordinates successful");
		}catch (RuntimeException re) {
			log.error("deleteCoordinates failed", re);
			throw re;
		}
	}
	
	public void deleteByCoordinatesKey(int plantYear, int plantNo, String idCard, long typeId, long breedTypeId, String docNo) 
	{
		try {
			String sqlQuery = "DELETE FROM Coordinates WHERE PlantYear=:plantYear ";
			sqlQuery += "AND PlantNo=:plantNo AND IdCard=:idCard ";
			sqlQuery += "AND TypeId=:typeId AND DocNo=:docNo ";
			sqlQuery += "AND BreedTypeId=:breedTypeId ";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
			query.setInteger("plantYear", plantYear);
			query.setInteger("plantNo", plantNo);
			query.setString("idCard", idCard);
			query.setLong("typeId", typeId);
			query.setString("docNo", docNo);
			query.setLong("breedTypeId", breedTypeId);
			query.executeUpdate();
			log.info("deleteByLandCheckId successful");
		} catch (RuntimeException re) {
			log.error("deleteByLandCheckId failed", re);
			throw re;
		}
	}
}
