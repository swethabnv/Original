package com.wsndata.dbaccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import com.dcs.hibernate.HibernateHome;

public class ReportHome extends HibernateHome {

	private static final Logger log = Logger.getLogger(ReportHome.class);
	String[] strSeason = new String[] {"", "นาปี", "นาปรัง"};
	String[] strObjective = new String[] {"", "เพื่อบริโภค", "เพื่อทำพันธุ์"};
	String[] strFta = new String[] {"", "1", "0"};
	String[] strQualifi = new String[] {"", "เกษตรอินทรีย์แท้", "เกษตรอินทรีย์", "", "เกษตรทั่วไป"};
	
	public List findR008ByCriteria(String forecastDateStr, String forecastDateEnd, long plantYear, long provinceNo, long cooperativeId ,long breedTypeId, String[] season, String[] objective, String[] qualification, String[] fta)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT * FROM r008 ");
			sqlQuery.append("WHERE PlantYear = :plantYear ");
		    if (provinceNo>0) {
		    	sqlQuery.append("AND ProvinceNo=:provinceNo ");
			}
		    if (cooperativeId>0) {
		    	sqlQuery.append("AND CooperativeId=:cooperativeId ");
			}
		    if (season != null && season.length > 0) {
		    	sqlQuery.append("AND PlantNo IN (");
				for(int i=0;i<season.length;i++) {
					sqlQuery.append(season[i]);
					if((i+1)<season.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(") AND BreedTypeName = 'ข้าว' ");
		    }
		    if (objective != null && objective.length > 0) {
		    	sqlQuery.append("AND PlantObjective IN (");
				for(int i=0;i<objective.length;i++) {
					sqlQuery.append("'"+strObjective[Integer.parseInt(objective[i])]+"'");
					if((i+1)<objective.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(") ");
		    }
		    if (qualification != null && qualification.length > 0) {
		    	sqlQuery.append("AND qualification IN (");
				for(int i=0;i<qualification.length;i++) {
					sqlQuery.append("'"+strQualifi[Integer.parseInt(qualification[i])]+"'");
					if((i+1)<qualification.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(") ");
		    }
		    if (fta != null && fta.length > 0) {
		    	sqlQuery.append("AND FTA IN (");
				for(int i=0;i<fta.length;i++) {
					sqlQuery.append("'"+strFta[Integer.parseInt(fta[i])]+"'");
					if((i+1)<fta.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(") ");
		    }
		    if (forecastDateStr != null) {
		    	sqlQuery.append(" AND PublicMarketDate BETWEEN DATE_FORMAT(STR_TO_DATE(:forecastDateStr, '%d/%m/%Y'), '%d/%m/%Y')");
		    	sqlQuery.append(" AND DATE_FORMAT(STR_TO_DATE(:forecastDateEnd, '%d/%m/%Y'), '%d/%m/%Y')");
		    	sqlQuery.append(" AND plantId in (SELECT MAX(pt.plantId) FROM plant pt where DATE_FORMAT(pt.CreateDate, '%Y/%m/%d') <= DATE_FORMAT(DATE_ADD(STR_TO_DATE(:forecastDateEnd, '%d/%m/%Y'), interval -543 year), '%Y/%m/%d') GROUP BY pt.refplantid)");
			}
		    
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			query.setLong("plantYear", plantYear);
		    if (provinceNo>0) {
		    	query.setLong("provinceNo", provinceNo);
			}
		    if (cooperativeId>0) {
				query.setLong("cooperativeId", cooperativeId);
			}
		    if (forecastDateStr != null) {
		    	query.setString("forecastDateStr", forecastDateStr);
				query.setString("forecastDateEnd", forecastDateEnd);
			}
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR008ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR009ByCriteria(Date forecastDateStr, Date forecastDateEnd, long month, long regionNo, String cooperative, long breedTypeId, long breedGroupId, String[] Objective, String[] Qualification, String[] fta)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT r.* ");
		    
		    SimpleDateFormat ftDate = new SimpleDateFormat("yyyy-MM-dd");
		    Calendar start = Calendar.getInstance();
		    start.setTime(forecastDateStr);
		    Calendar startDate = Calendar.getInstance();
		    Calendar endDate = Calendar.getInstance();
		    int dd=1,mm=0,yy=0;
		    String allMM = "";
		    for(int i=0;i<month;i++) {
		    	mm = start.get(Calendar.MONTH)+i;
		    	yy = start.get(Calendar.YEAR);
		    	if(mm>11)
		    		yy++;
		    	startDate.set(yy, (mm%12), dd);
		    	
		    	if(mm<12 && (mm+1)>11)
		    		yy++;
		    	endDate.set(yy, (mm+1)%12, dd);
		    	endDate.add(Calendar.DAY_OF_MONTH, -1);
		    	sqlQuery.append(",IFNULL((SELECT DISTINCT SUM( forecastRecord ) FROM r009 ");
		    	sqlQuery.append("WHERE ForecastDate between '"+ftDate.format(startDate.getTime())+"' and '"+ftDate.format(endDate.getTime())+"' ");
		    	sqlQuery.append("AND ProvinceNo=r.ProvinceNo and CooperativeId= r.CooperativeId ");
		    	sqlQuery.append("AND DistrictNo= r.DistrictNo and SubDistrictNo = r.SubDistrictNo ");
		    	if(breedTypeId>0)
		    		sqlQuery.append("AND BreedTypeId = r.BreedTypeId ");
		    	if(breedGroupId>0)
		    		sqlQuery.append("AND BreedGroupId = r.BreedGroupId ");
		    	sqlQuery.append(")/1000, 0) AS m"+(i+1));
		    	allMM += "+m"+(i+1);
		    }
		    sqlQuery.append(" FROM r009 r WHERE forecastRecord > 0 ");
		   
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (!cooperative.equals("()")) {
		    	sqlQuery.append(" AND CooperativeId IN " + cooperative);
		    }
		    if (forecastDateStr != null) {
		    	sqlQuery.append(" AND forecastDate BETWEEN :forecastDateStr AND :forecastDateEnd");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (Objective != null && Objective.length > 0) {
		    	sqlQuery.append(" AND PlantObjective IN (");
				for(int i=0;i<Objective.length;i++) {
					sqlQuery.append("'"+strObjective[Integer.parseInt(Objective[i])]+"'");
					if((i+1)<Objective.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }
		    if (Qualification != null && Qualification.length > 0) {
		    	sqlQuery.append(" AND Qualification IN (");
				for(int i=0;i<Qualification.length;i++) {
					sqlQuery.append("'"+strQualifi[Integer.parseInt(Qualification[i])]+"'");
					if((i+1)<Qualification.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }
		    if (fta != null && fta.length > 0) {
		    	sqlQuery.append(" AND FTA IN (");
				for(int i=0;i<fta.length;i++) {
					sqlQuery.append("'"+strFta[Integer.parseInt(fta[i])]+"'");
					if((i+1)<fta.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }
		    
		    sqlQuery.append(" AND plantId in (SELECT MAX(pt.plantId) FROM plant pt where DATE_FORMAT(pt.CreateDate, '%Y/%m/%d') <= DATE_FORMAT(CURTIME(), '%Y/%m/%d') GROUP BY pt.refplantid)");
		    sqlQuery.append(" GROUP BY ProvinceNo, CooperativeId, DistrictNo, SubdistrictNo");
		    
		    if (breedTypeId>0)
		    	sqlQuery.append(",BreedTypeId");
		    if (breedGroupId>0)
		    	 sqlQuery.append(",BreedGroupId");
		    //sqlQuery.append(" HAVING ("+allMM.substring(1)+") > 0");
		    sqlQuery.append(" Order By RegionNo, CooProvinceName, CooperativeId, DistrictThai, SubDistrictThai, ForecastDate");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (forecastDateStr != null) {
		    	query.setDate("forecastDateStr", forecastDateStr);
				query.setDate("forecastDateEnd", forecastDateEnd);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR009ByCriteria failed", re);
			throw re;
		}	
	}
	
	public List findR010ByCriteria(Date forecastDateStr, Date forecastDateEnd, long month, long regionNo, String cooperative, long breedTypeId, long breedGroupId, String[] Objective, String[] Qualification, String[] fta)
	{	
		try{
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("SELECT DISTINCT r.* ");
		    
		    SimpleDateFormat ftDate = new SimpleDateFormat("yyyy-MM-dd");
		    Calendar start = Calendar.getInstance();
		    start.setTime(forecastDateStr);
		    Calendar startDate = Calendar.getInstance();
		    Calendar endDate = Calendar.getInstance();
		    int dd=1,mm=0,yy=0;
		    String allMM = "";
		    for(int i=0;i<month;i++) {
		    	mm = start.get(Calendar.MONTH)+i;
		    	yy = start.get(Calendar.YEAR);
		    	if(mm>11)
		    		yy++;
		    	startDate.set(yy, (mm%12), dd);
		    	
		    	if(mm<12 && (mm+1)>11)
		    		yy++;
		    	endDate.set(yy, (mm+1)%12, dd);
		    	endDate.add(Calendar.DAY_OF_MONTH, -1);
		    	sqlQuery.append(",IFNULL((SELECT DISTINCT SUM( PublicCrop ) FROM r010 ");
		    	sqlQuery.append("WHERE PublicMarketDate between '"+ftDate.format(startDate.getTime())+"' and '"+ftDate.format(endDate.getTime())+"' ");
		    	sqlQuery.append("AND ProvinceNo=r.ProvinceNo and CooperativeId= r.CooperativeId ");
		    	sqlQuery.append("AND DistrictNo= r.DistrictNo and SubDistrictNo = r.SubDistrictNo ");
		    	if(breedTypeId>0)
		    		sqlQuery.append("AND BreedTypeId = r.BreedTypeId ");
		    	if(breedGroupId>0)
		    		sqlQuery.append("AND BreedGroupId = r.BreedGroupId ");
			    if (Objective != null && Objective.length > 0)
			    	sqlQuery.append("AND PlantObjective = r.PlantObjective ");
			    if (Qualification != null && Qualification.length > 0)
			    	sqlQuery.append("AND Qualification = r.Qualification ");
		    	sqlQuery.append(")/1000, 0) AS m"+(i+1));
		    	allMM += "+m"+(i+1);
		    }
		    sqlQuery.append(" FROM r010 r WHERE PublicMarketDate > 0 ");
		   
		    if (regionNo>0) {
		    	sqlQuery.append(" AND regionNo=:regionNo");
			}
		    if (!cooperative.equals("()")) {
		    	sqlQuery.append(" AND CooperativeId IN " + cooperative);
		    }
		    if (forecastDateStr != null) {
		    	sqlQuery.append(" AND PublicMarketDate BETWEEN :forecastDateStr AND :forecastDateEnd");
			}
		    if (breedTypeId>0) {
		    	sqlQuery.append(" AND BreedTypeId=:breedTypeId");
			}
		    if (breedGroupId>0) {
		    	 sqlQuery.append(" AND BreedGroupId=:breedGroupId");
			}
		    if (Objective != null && Objective.length > 0) {
		    	sqlQuery.append(" AND PlantObjective IN (");
				for(int i=0;i<Objective.length;i++) {
					sqlQuery.append("'"+strObjective[Integer.parseInt(Objective[i])]+"'");
					if((i+1)<Objective.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }
		    if (Qualification != null && Qualification.length > 0) {
		    	sqlQuery.append(" AND Qualification IN (");
				for(int i=0;i<Qualification.length;i++) {
					sqlQuery.append("'"+strQualifi[Integer.parseInt(Qualification[i])]+"'");
					if((i+1)<Qualification.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }
		    if (fta != null && fta.length > 0) {
		    	sqlQuery.append(" AND FTA IN (");
				for(int i=0;i<fta.length;i++) {
					sqlQuery.append("'"+strFta[Integer.parseInt(fta[i])]+"'");
					if((i+1)<fta.length)
						sqlQuery.append(", ");
				}
		    	sqlQuery.append(")");
		    }

		    sqlQuery.append(" AND plantId in (SELECT MAX(pt.plantId) FROM plant pt where DATE_FORMAT(pt.CreateDate, '%Y/%m/%d') <= DATE_FORMAT(CURTIME(), '%Y/%m/%d') GROUP BY pt.refplantid)");
		    sqlQuery.append(" GROUP BY ProvinceNo, CooperativeId, DistrictNo, SubdistrictNo");
		    
		    if (breedTypeId>0)
		    	sqlQuery.append(",BreedTypeId");
		    if (breedGroupId>0)
		    	 sqlQuery.append(",BreedGroupId");
		    //sqlQuery.append(" HAVING ("+allMM.substring(1)+") > 0");
		    sqlQuery.append(" ORDER BY RegionNo, CooProvinceName, CooperativeId, DistrictThai, SubDistrictThai, PublicMarketDate");
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
			
		    if (regionNo>0) {
		    	query.setLong("regionNo", regionNo);
			}
		    if (forecastDateStr != null) {
		    	query.setDate("forecastDateStr", forecastDateStr);
				query.setDate("forecastDateEnd", forecastDateEnd);
			}
		    if (breedTypeId>0) {
				query.setLong("breedTypeId", breedTypeId);
			}
		    if (breedGroupId>0) {
		    	query.setLong("breedGroupId", breedGroupId);
			}
		    
			return query.list();
		} catch (RuntimeException re) {
			log.error("findR010ByCriteria failed", re);
			throw re;
		}	
	}
}
