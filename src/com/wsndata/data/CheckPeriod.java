package com.wsndata.data;

import java.io.Serializable;
import java.util.Set;

public class CheckPeriod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -534107159826840118L;
	private long checkPeriodId;
	private String description;
	private Set<MixedBreedType> mixedBreedType;
	
	public long getCheckPeriodId() {
		return checkPeriodId;
	}
	public void setCheckPeriodId(long checkPeriodId) {
		this.checkPeriodId = checkPeriodId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<MixedBreedType> getMixedBreedType() {
		return mixedBreedType;
	}
	public void setMixedBreedType(Set<MixedBreedType> mixedBreedType) {
		this.mixedBreedType = mixedBreedType;
	}
	
}
