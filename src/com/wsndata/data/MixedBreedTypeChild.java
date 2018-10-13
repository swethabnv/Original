package com.wsndata.data;

import java.io.Serializable;

public class MixedBreedTypeChild implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7642970012101129396L;
	private long childId;
	private String description;
	
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
