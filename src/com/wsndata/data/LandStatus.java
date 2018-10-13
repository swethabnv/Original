package com.wsndata.data;
import java.io.Serializable;

public class LandStatus implements Serializable {

	
	private static final long serialVersionUID = 2853632277443856466L;
	private long landStatusId;
	private String landStatusName;
	
	public long getLandStatusId() {
		return landStatusId;
	}
	public void setLandStatusId(long landStatusId) {
		this.landStatusId = landStatusId;
	}
	public String getLandStatusName() {
		return landStatusName;
	}
	public void setLandStatusName(String landStatusName) {
		this.landStatusName = landStatusName;
	}
	
    
    
}
