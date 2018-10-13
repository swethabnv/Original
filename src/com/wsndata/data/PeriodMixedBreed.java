package com.wsndata.data;

import java.io.Serializable;

public class PeriodMixedBreed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 66400599758434114L;
	private long checkPeriodId;
	private long mixedBreedTypeId;
	
	public long getCheckPeriodId() {
		return checkPeriodId;
	}
	public void setCheckPeriodId(long checkPeriodId) {
		this.checkPeriodId = checkPeriodId;
	}
	public long getMixedBreedTypeId() {
		return mixedBreedTypeId;
	}
	public void setMixedBreedTypeId(long mixedBreedTypeId) {
		this.mixedBreedTypeId = mixedBreedTypeId;
	}

}
