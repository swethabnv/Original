package com.wsndata.data;

import java.io.Serializable;

import com.dcs.dcswc.common.ICheckOnGrid;

public class UserProvince implements Serializable, ICheckOnGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private long regionNo;
	private long provinceNo;
	private User user;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}

	public long getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}

	@Override
	public boolean getCheckBox() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCheckBox(boolean checkBox) {
		// TODO Auto-generated method stub
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
