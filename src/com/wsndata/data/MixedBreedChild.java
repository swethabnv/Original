package com.wsndata.data;

import java.io.Serializable;

public class MixedBreedChild implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8771190876348766885L;
	private long mixedBreedTypeId;
	private long childId;
	
	public long getMixedBreedTypeId() {
		return mixedBreedTypeId;
	}
	public void setMixedBreedTypeId(long mixedBreedTypeId) {
		this.mixedBreedTypeId = mixedBreedTypeId;
	}
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}

}
