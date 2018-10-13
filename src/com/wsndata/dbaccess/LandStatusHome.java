package com.wsndata.dbaccess;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.LandStatus;
public class LandStatusHome extends HibernateHome 
{
	private static final Logger log = Logger.getLogger(LandStatusHome.class);
	public List<LandStatus> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(LandStatus.class);
			crit.addOrder(Order.asc("landStatusName"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}
	
	
	
	
	
	
}
