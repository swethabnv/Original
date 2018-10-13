package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.UserAuthorize;

public class UserAuthorizeHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(UserAuthorizeHome.class);

	public UserAuthorize findByKey(String userName, String menuId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(UserAuthorize.class);
			crit.add(Restrictions.eq("userName", userName));
			crit.add(Restrictions.eq("menuId", menuId));
			return (UserAuthorize) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByKey failed", re);
			throw re;
		}
	}
	
	public List<UserAuthorize> listAuthorize(String userName){
		try{
			Criteria query = sessionFactory.getCurrentSession().createCriteria(UserAuthorize.class);
			query.add(Restrictions.eq("userName", userName));
			return query.list();
		}catch (RuntimeException re) {
			log.error("listAuthorize failed", re);
			throw re;
		}
	}
	
	public List searchByKey(String userName)
	{
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT auth.UserName AS userName");
					sqlQuery.append(", auth.MenuId AS menuId");
							sqlQuery.append(", auth.Authorize AS authorize");
							sqlQuery.append(", menu.PMenuId AS pmenuId");
							sqlQuery.append(", menu.SEQ AS seq");
							sqlQuery.append(", menu.MText AS mtext");
							sqlQuery.append(", menu.MAction AS maction");
							sqlQuery.append(", menu.Visible AS visible");
			sqlQuery.append(" FROM Menu AS menu");
			sqlQuery.append("left join UserAuthorize AS auth");
			sqlQuery.append("ON menu.MenuId = auth.MenuId");
			sqlQuery.append("WHERE auth.userName <> 'null' and auth.MenuId <> 'null'");
			sqlQuery.append("AND auth.userName=:userName");
			//sqlQuery.append("AND auth.menuId=:menuId");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			if(!"".equals(userName))
				query.setString("userName", userName);
			/*
			if(!"".equals(menuId))
				query.setString("menuId", menuId);*/
			return query.list();
		} catch (RuntimeException re) {
			log.error("searchByKey failed", re);
			throw re;
		}
		
		
	}
	
}
