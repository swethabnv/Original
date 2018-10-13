package com.wsndata.data;

import java.io.Serializable;

public class PrepareManure implements Serializable {

	
	private static final long serialVersionUID = 6812156586812901134L;
	private long prepareManureId;
	private String prepareManureName;
	private String prepareManureType;
	
	public long getPrepareManureId() {
		return prepareManureId;
	}
	public void setPrepareManureId(long prepareManureId) {
		this.prepareManureId = prepareManureId;
	}
	public String getPrepareManureName() {
		return prepareManureName;
	}
	public void setPrepareManureName(String prepareManureName) {
		this.prepareManureName = prepareManureName;
	}
	public String getPrepareManureType() {
		return prepareManureType;
	}
	public void setPrepareManureType(String prepareManureType) {
		this.prepareManureType = prepareManureType;
	}
	
	
	
    
    
}
