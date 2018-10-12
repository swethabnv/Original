package com.dcs.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;

public class MySQLPartitionHome extends HibernateHome {
	
	public List listPartitioInfo(String schemaName, String tableName) {
		String sqlString = "select PARTITION_NAME,TABLE_ROWS,DATA_LENGTH,INDEX_LENGTH from information_schema.PARTITIONS where TABLE_NAME=:tableName ";
		if (null!=schemaName) {
			sqlString += " and TABLE_SCHEMA=:schemaName";
		}
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString).addEntity(MySQLPartitionInfo.class);
		sqlQuery.setParameter("tableName", tableName);
		if (null!=schemaName) {
			sqlQuery.setParameter("schemaName", schemaName);
		}
		return sqlQuery.list();
	}
	
	public boolean isPartitioned(String schemaName, String tableName) {
		String sqlString = "select PARTITION_NAME from information_schema.PARTITIONS where TABLE_NAME=:tableName ";
		if (null!=schemaName) {
			sqlString += " and TABLE_SCHEMA=:schemaName";
		}
		sqlString += " and PARTITION_NAME is null";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString);
		sqlQuery.setParameter("tableName", tableName);
		if (null!=schemaName) {
			sqlQuery.setParameter("schemaName", schemaName);
		}
		int x = sqlQuery.list().size();
		return (x==0);
	}
	
	public boolean isPartitioned(String schemaName, String tableName, String partitionName) {
		if (partitionName==null) {
			return isPartitioned(schemaName, tableName);
		}
		String sqlString = "select PARTITION_NAME from information_schema.PARTITIONS where TABLE_NAME=:tableName ";
		if (null!=schemaName) {
			sqlString += " and TABLE_SCHEMA=:schemaName";
		}
		if (null!=partitionName) {
			sqlString += " and PARTITION_NAME=:partitionName";
		}
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString);
		sqlQuery.setParameter("tableName", tableName);
		if (null!=schemaName) {
			sqlQuery.setParameter("schemaName", schemaName);
		}
		if (null!=partitionName) {
			sqlQuery.setParameter("partitionName", partitionName);
		}
		Object result = sqlQuery.uniqueResult();
		return (result!=null);
	}
	
	public void createPartition(String tableName, String range, String[] partitionName, String[] partitionExpression) throws Exception {
		if (partitionName.length!=partitionExpression.length && partitionName.length<=0) {
			throw new Exception("Parameters array size missmatch");
		}
		int i = 0;
		String sqlString = "alter table "+tableName+" partition by range("+range+") (";
		for (; i<partitionName.length-1; i++) {
			sqlString += "partition "+partitionName[i]+" values less than ("+partitionExpression[i]+"), ";
		}
		sqlString += "partition "+partitionName[i]+" values less than ("+partitionExpression[i]+")";
		sqlString += ")";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString);
		sqlQuery.executeUpdate();
	}
	
	public void createPartition(String tableName, String partitionName, String partitionExpression) {
		String sqlString = "alter table "+tableName+" add partition (";
		sqlString += "partition "+partitionName+" values less than ("+partitionExpression+")";
		sqlString += ")";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString);
		sqlQuery.executeUpdate();
	}
	
	public void dropPartition(String tableName, String partitionName) {
		String sqlString = "alter table "+tableName+" drop partition "+partitionName;
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlString);
		sqlQuery.executeUpdate();
	}
}
