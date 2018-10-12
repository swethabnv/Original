package com.dcs.hibernate;

import java.io.Serializable;

public class MySQLPartitionInfo implements Serializable{
	private static final long serialVersionUID = -8524606208770364534L;
	
	private String partitionName;
	private long tableRows;
	private long dataLength;
	private long indexLength;
	
	public long getDataLength() {
		return dataLength;
	}
	public void setDataLength(long dataLength) {
		this.dataLength = dataLength;
	}
	public long getIndexLength() {
		return indexLength;
	}
	public void setIndexLength(long indexLength) {
		this.indexLength = indexLength;
	}
	public String getPartitionName() {
		return partitionName;
	}
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	public long getTableRows() {
		return tableRows;
	}
	public void setTableRows(long tableRows) {
		this.tableRows = tableRows;
	}
}
