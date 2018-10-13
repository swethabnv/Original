package com.wsndata.data;

import java.io.Serializable;

public class CheckingDisease implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6553836036212753510L;
	private long checkingDiseaseId;
	private String description;
	
	public long getCheckingDiseaseId() {
		return checkingDiseaseId;
	}
	public void setCheckingDiseaseId(long checkingDiseaseId) {
		this.checkingDiseaseId = checkingDiseaseId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
