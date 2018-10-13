package com.wsndata.data;

import java.io.Serializable;

public class CheckingDiseaseChild implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6553836036212753510L;
	private long checkingDiseaseId;
	private long diseaseChildId;
	private String description;
	
	public long getCheckingDiseaseId() {
		return checkingDiseaseId;
	}
	public void setCheckingDiseaseId(long checkingDiseaseId) {
		this.checkingDiseaseId = checkingDiseaseId;
	}
	public long getDiseaseChildId() {
		return diseaseChildId;
	}
	public void setDiseaseChildId(long diseaseChildId) {
		this.diseaseChildId = diseaseChildId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
