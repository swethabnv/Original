package com.wsndata.data;

import java.io.Serializable;
import java.util.Set;

public class MixedBreedType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2964163487889001487L;
	private long mixedBreedTypeId;
	private String description;
	private Set<MixedBreedTypeChild> mixedBreedTypeChild;
	
	public long getMixedBreedTypeId() {
		return mixedBreedTypeId;
	}
	public void setMixedBreedTypeId(long mixedBreedTypeId) {
		this.mixedBreedTypeId = mixedBreedTypeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<MixedBreedTypeChild> getMixedBreedTypeChild() {
		return mixedBreedTypeChild;
	}
	public void setMixedBreedTypeChild(Set<MixedBreedTypeChild> mixedBreedTypeChild) {
		this.mixedBreedTypeChild = mixedBreedTypeChild;
	}

}
