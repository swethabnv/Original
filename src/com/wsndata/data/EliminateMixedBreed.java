package com.wsndata.data;

import java.io.Serializable;

public class EliminateMixedBreed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 213638059861143139L;
	private long eliminateMixedBreedId;
	private String description;
	
	public long getEliminateMixedBreedId() {
		return eliminateMixedBreedId;
	}
	public void setEliminateMixedBreedId(long eliminateMixedBreedId) {
		this.eliminateMixedBreedId = eliminateMixedBreedId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
