package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.wsndata.data.Province;
import com.wsndata.data.User;
import com.wsndata.data.UserProvince;
import com.dcs.hibernate.HibernateHome;

public class UserHome extends HibernateHome 
{
	private static final Logger log = Logger.getLogger(UserHome.class);

	public User findByUser(String userName) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("userName", userName));
			return (User) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByUser failed", re);
			throw re;
		}
	}
	
	public List<User> findByPrefix(String abbrPrefix) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);	
			crit.add(Restrictions.eq("abbrPrefix", abbrPrefix));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByPrefix failed", re);
			throw re;
		}
	}	

	
	public User authenticateUser(String userName, String password)
	{
		try {	
			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			
			crit.add(Restrictions.eq("userName", userName));
			crit.add(Restrictions.eq("password", password));
			crit.add(Restrictions.eq("active", true));
			System.out.println("entered database2");
			return (User) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("authenticateUsers failed", re);
			throw re;
		}
	}
	
	public boolean authenUser(String userName, String password)
	{
		try {		
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("userName", userName));
			crit.add(Restrictions.eq("password", password));
			User getUser = (User) crit.uniqueResult();	
			if(getUser !=null) {
				return true;
			} else {
				return false;
			}				
		} catch (RuntimeException re) {
			log.error("authenUser failed", re);
			throw re;
		}
	}
	
	
	public List findAll()
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct user.UserName AS userName");
			sqlQuery.append(", user.Password AS password");
			sqlQuery.append(", user.BranchCode AS branchCode");
			sqlQuery.append(", branch.BranchName AS BranchName");
			sqlQuery.append(", user.AbbrPrefix AS abbrPrefix");
		    sqlQuery.append(", user.FirstName AS firstName");
			sqlQuery.append(", user.LastName AS lastName");
			sqlQuery.append(", user.Email AS email");
			sqlQuery.append(", user.Active AS active");
			sqlQuery.append(" FROM User AS user left join Branch AS branch");
			sqlQuery.append(" ON user.BranchCode = branch.BranchCode");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
		
	}
	
	public List findAll(long level)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT distinct user.UserName AS userName");
			sqlQuery.append(", user.Password AS password");
			sqlQuery.append(", user.BranchCode AS branchCode");
			sqlQuery.append(", branch.BranchName AS BranchName");
			sqlQuery.append(", user.AbbrPrefix AS abbrPrefix");
			sqlQuery.append(", user.FirstName AS firstName");
			sqlQuery.append(", user.LastName AS lastName");
			sqlQuery.append(", user.Email AS email");
			sqlQuery.append(", user.Active AS active");
			sqlQuery.append(", GROUP_CONCAT(DISTINCT r.RegionName) AS region");
			sqlQuery.append(", GROUP_CONCAT(DISTINCT p.ThaiName) AS province");
			sqlQuery.append(", d.ThaiName AS district");
			sqlQuery.append(", s.ThaiName AS subDistrict");
			sqlQuery.append(", user.Level AS level");
			sqlQuery.append(" FROM User AS user left join Branch AS branch");
			sqlQuery.append(" ON user.BranchCode = branch.BranchCode");
			sqlQuery.append(" LEFT JOIN UserRegion ur ON user.UserName = ur.UserName");
			sqlQuery.append(" LEFT JOIN Region r ON ur.RegionNo = r.RegionNo");
			sqlQuery.append(" LEFT JOIN UserProvince up ON user.UserName = up.UserName");
			sqlQuery.append(" LEFT JOIN Province p ON up.ProvinceNo = p.ProvinceNo");
			sqlQuery.append(" LEFT JOIN District d ON user.DistrictNo = d.DistrictNo");
			sqlQuery.append(" LEFT JOIN SubDistrict s ON user.SubDistrictNo = s.SubDistrictNo");
			sqlQuery.append(" WHERE user.Level >= ? GROUP BY user.UserName");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong(0, level);
			return query.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
		
	}
	
	public List searchByCriteria(String userName, String firstName, String lastName, long level, long userLevel, long groupUser, long regionNo, long provinceNo, long districtNo, long subDistrictNo)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT user.UserName AS userName");
			sqlQuery.append(", user.Password AS password");
			sqlQuery.append(", user.BranchCode AS branchCode");
			sqlQuery.append(", branch.BranchName AS BranchName");
			sqlQuery.append(", user.AbbrPrefix AS abbrPrefix");
			sqlQuery.append(", user.FirstName AS firstName");
			sqlQuery.append(", user.LastName AS lastName");
			sqlQuery.append(", user.Email AS email");
			sqlQuery.append(", user.Active AS active");
			sqlQuery.append(", GROUP_CONCAT(DISTINCT r.RegionName) AS region");
			sqlQuery.append(", GROUP_CONCAT(DISTINCT p.ThaiName) AS province");
			sqlQuery.append(", d.ThaiName AS district");
			sqlQuery.append(", s.ThaiName AS subDistrict");
			sqlQuery.append(", user.Level AS level");
			sqlQuery.append(" FROM User AS user");
			sqlQuery.append(" left join Branch AS branch");
			sqlQuery.append(" on user.BranchCode = branch.BranchCode");
			sqlQuery.append(" LEFT JOIN UserRegion ur ON user.UserName = ur.UserName");
			sqlQuery.append(" LEFT JOIN Region r ON ur.RegionNo = r.RegionNo");
			sqlQuery.append(" LEFT JOIN UserProvince up ON user.UserName = up.UserName");
			sqlQuery.append(" LEFT JOIN Province p ON up.ProvinceNo = p.ProvinceNo");
			sqlQuery.append(" LEFT JOIN District d ON user.DistrictNo = d.DistrictNo");
			sqlQuery.append(" LEFT JOIN SubDistrict s ON user.SubDistrictNo = s.SubDistrictNo");
			if(level > 0)
				sqlQuery.append(" WHERE user.Level = :level");
			else
				sqlQuery.append(" WHERE user.Level >= :level");
			if(regionNo!=0)
				sqlQuery.append(" AND user.RegionNo = :regionNo");
			if(provinceNo!=0)
				sqlQuery.append(" AND user.ProvinceNo = :provinceNo");
			if(districtNo!=0)
				sqlQuery.append(" AND user.DistrictNo = :districtNo");
			if(subDistrictNo!=0)
				sqlQuery.append(" AND user.SubDistrictNo = :subDistrictNo");
			if(!"".equals(userName))
				sqlQuery.append(" AND user.UserName LIKE :userName");
			if(!"".equals(firstName))
				sqlQuery.append(" AND user.FirstName LIKE :firstName");
			if(!"".equals(lastName))
				sqlQuery.append(" AND user.LastName LIKE :lastName");
			if(groupUser > 0 )
				sqlQuery.append(" AND user.GroupUser = :groupUser");
			sqlQuery.append(" GROUP BY user.UserName");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(level > 0)
				query.setLong("level", level);
			else
				query.setLong("level", userLevel);
			if(regionNo!=0)
				query.setLong("regionNo", regionNo);
			if(provinceNo!=0)
				query.setLong("provinceNo", provinceNo);
			if(districtNo!=0)
				query.setLong("districtNo", districtNo);
			if(subDistrictNo!=0)
				query.setLong("subDistrictNo", subDistrictNo);
			if(!"".equals(userName))
				query.setString("userName", "%" + userName+"%");
			if(!"".equals(firstName))
				query.setString("firstName", "%"+ firstName+"%");
			if(!"".equals(lastName))
				query.setString("lastName", "%" +lastName+"%");
			if(groupUser > 0 )
				query.setLong("groupUser", groupUser);
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByCriteria failed", re);
			throw re;
		}
	}
	
	
	
	public List<UserProvince> findProvinceByUserName(String userName)
	{
		try {			
			StringBuffer sqlQuery = new StringBuffer();
				sqlQuery.append(" SELECT DISTINCT p.ProvinceNo ");
				sqlQuery.append(" FROM UserProvince p ");
				sqlQuery.append(" WHERE p.UserName =:userName ");
				//Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString()).addEntity(Province.class);
				query.setString("userName", userName);
				return query.list();
		} catch (RuntimeException re) {
			log.error("findProvinceByUserName failed", re);
			throw re;
		}
	}
	
	
	
	public void activeUser(String userName, String passWord){
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE User SET Active = not(Active), Password=:passWord WHERE UserName=:userName");
			query.setString("userName", userName);
			query.setString("passWord", passWord);
			query.executeUpdate();
			log.info("activeUser successful");
		}catch (RuntimeException re) {
			log.error("activeUser failed", re);
			throw re;
		}
	}
	
	public void delUser(String userName){
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM User WHERE UserName=:userName");
			query.setString("userName", userName);
			query.executeUpdate();
			log.info("delUser successful");
		}catch (RuntimeException re) {
			log.error("delUser failed", re);
			throw re;
		}
	}
	
	public List<User> findByRegionNo(long regionNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("regionNo", regionNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByRegionNo failed", re);
			throw re;
		}
	}
	
	
	
	
	public List<User> findByProvinceNo(long provinceNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("provinceNo", provinceNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByProvinceNo failed", re);
			throw re;
		}
	}
	
	public List<User> findByDistrictNo(long districtNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("districtNo", districtNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findByDistrictNo failed", re);
			throw re;
		}
	}
	
	public List<User> findBySubDistrictNo(long subDistrictNo)
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
			crit.add(Restrictions.eq("subDistrictNo", subDistrictNo));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findBySubDistrictNo failed", re);
			throw re;
		}
	}
	
	
	public void delUserRegion(String userName){
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM UserRegion WHERE UserName=:userName");
			query.setString("userName", userName);
			query.executeUpdate();
			log.info("delUserRegion successful");
		}catch (RuntimeException re) {
			log.error("delUserRegion failed", re);
			throw re;
		}
	}
	
	
	public void delUserProvince(String userName){
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM UserProvince WHERE UserName=:userName");
			query.setString("userName", userName);
			query.executeUpdate();
			log.info("delUserProvince successful");
		}catch (RuntimeException re) {
			log.error("delUserProvince failed", re);
			throw re;
		}
	}
	
	public void delUserFarmerGroup(String userName){
		try {			
			Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM UserFarmerGroup WHERE UserName=:userName");
			query.setString("userName", userName);
			query.executeUpdate();
			log.info("delUserFarmerGroup successful");
		}catch (RuntimeException re) {
			log.error("delUserFarmerGroup failed", re);
			throw re;
		}
	}
	
		
}
