package com.wsndata.dbaccess;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.PlantChemical;

public class PlantChemicalHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(PlantChemicalHome.class);
	public List<PlantChemical> findAll()
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantChemical.class);
			crit.addOrder(Order.asc("seq"));
			return crit.list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	public PlantChemical findByCriteria(long typeId,long plantId,int seq) 
	{
		try {			
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(PlantChemical.class);
			if (typeId>0) {
				crit.add(Restrictions.eq("typeId", typeId));	
			}
			if (plantId>0) {
				crit.add(Restrictions.eq("plantId", plantId));	
			}
			if (seq>0) {
				crit.add(Restrictions.eq("seq", seq));	
			}
			return (PlantChemical)crit.uniqueResult();
		} catch (RuntimeException re) {
			log.error("findByCriteria failed", re);
			throw re;
		}
	}
	
	
	public List getPlantChemicalList(){
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT cType.TypeId");
			sqlQuery.append(", cType.TypeName");
			sqlQuery.append(", pChem.Formula");
			sqlQuery.append(", pChem.ChemicalName");
			sqlQuery.append(", pChem.QtyPerRai");
			sqlQuery.append(", pChem.CostPerRai");
			sqlQuery.append(", pChem.Seq ");
			sqlQuery.append(", pChem.PlantId");
			sqlQuery.append(" FROM ChemicalType as cType");
			sqlQuery.append(" LEFT JOIN PlantChemical as pChem");
			sqlQuery.append(" ON cType.TypeId = pChem.TypeId");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			return query.list();
		} catch (RuntimeException re) {
			log.error("getPlantManureList failed", re);
			throw re;
		}
		
	}
	
}
