package com.dcs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


public class LicenseData implements Serializable{
	private static final Logger log = Logger.getLogger(LicenseData.class);
	private static final long serialVersionUID = -8308017468203002727L;
	
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private int userLimit;
	private String customerName;
	private String ipAddress;
	private String systemId;
	private String productId;
	private String productName;
	private Date expireDate;
	private Date issueDate;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public int getUserLimit() {
		return userLimit;
	}
	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getExpire() {		
		if (this.expireDate==null) {
			return "";
		} else {
			return dateFormat.format(this.expireDate);
		}
	}
	public void setExpire(String expire) {
		try {
			this.expireDate = dateFormat.parse(expire);
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	public String getIssue() {
		if (this.issueDate==null) {
			return "";
		} else {
			return dateFormat.format(this.issueDate);
		}
	}
	public void setIssue(String issue) {
		try {
			this.issueDate = dateFormat.parse(issue);
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
}
