package com.wsndata.data;
import java.io.Serializable;
public class LandType implements Serializable {


	private static final long serialVersionUID = 5225187088704562015L;
	private long landTypeId;
	private String landTypeName;
	
	
	public long getLandTypeId() {
		return landTypeId;
	}
	public void setLandTypeId(long landTypeId) {
		this.landTypeId = landTypeId;
	}
	public String getLandTypeName() {
		return landTypeName;
	}
	public void setLandTypeName(String landTypeName) {
		this.landTypeName = landTypeName;
	}
	
    
    
}
