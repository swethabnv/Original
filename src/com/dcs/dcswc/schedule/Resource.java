package com.dcs.dcswc.schedule;

public class Resource {
	private String resourceID;
	private String resourceName;
	private String detail;
	private boolean dropAble;
	private boolean share;
	
	public Resource(String resourceID, String resourceName, String detail) {
		this.resourceID = resourceID;
		this.resourceName = resourceName;
		this.detail = detail;
	}
	
	public Resource() {		
	}
	
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean getDropAble() {
		return dropAble;
	}

	public void setDropAble(boolean dropAble) {
		this.dropAble = dropAble;
	}

	public boolean getShare() {
		return share;
	}

	public void setShare(boolean share) {
		this.share = share;
	}

}
