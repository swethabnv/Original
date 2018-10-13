package com.wsndata.data;

import java.io.Serializable;

public class DiseaseType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6553836036212753510L;
	private long diseaseTypeId;
	private long diseaseChildId;
	private String description;
	
	public long getDiseaseTypeId() {
		return diseaseTypeId;
	}
	public void setDiseaseTypeId(long diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
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
