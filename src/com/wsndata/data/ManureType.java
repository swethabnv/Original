package com.wsndata.data;

import java.io.Serializable;

public class ManureType implements Serializable{

	
	private static final long serialVersionUID = 4873864794688803541L;
	
	private long typeId;
	private String typeName;
	
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
