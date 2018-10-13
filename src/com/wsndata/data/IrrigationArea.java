package com.wsndata.data;

import java.io.Serializable;
public class IrrigationArea implements Serializable{

	
	private static final long serialVersionUID = 6812156586812901134L;
	private long irrigationAreaId;
	private String irrigationAreaName;
	
	public long getIrrigationAreaId() {
		return irrigationAreaId;
	}
	public void setIrrigationAreaId(long irrigationAreaId) {
		this.irrigationAreaId = irrigationAreaId;
	}
	public String getIrrigationAreaName() {
		return irrigationAreaName;
	}
	public void setIrrigationAreaName(String irrigationAreaName) {
		this.irrigationAreaName = irrigationAreaName;
	}

}
