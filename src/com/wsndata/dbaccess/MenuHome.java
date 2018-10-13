package com.wsndata.dbaccess;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Menu;

public class MenuHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(MenuHome.class);
	
	
	public Menu findByMenuId(String menuId) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(Menu.class);
			crit.add(Restrictions.eq("menuId", menuId));
			return (Menu) crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByMenuId failed", re);
			throw re;
		}
	}
	
	public List<Menu> listAllMenu(){
		try{
			Criteria query = sessionFactory.getCurrentSession().createCriteria(Menu.class);
			query.add(Restrictions.eq("visible", true));
			query.addOrder(Order.asc("seq"));
			return query.list();
		}catch (RuntimeException re) {
			log.error("listAllMenu failed", re);
			throw re;
		}
	}
}
