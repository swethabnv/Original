package com.wsndata.data;

import java.io.Serializable;

public class Coordinates implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6116396703511297004L;
	private long coordinatesId;
	private String latitude;
	private String longitude;
	private int seq;
	private int plantYear;
	private int plantNo;
	private String idCard;
	private long typeId;
	private long breedTypeId;
	private String docNo;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public long getCoordinatesId() {
		return coordinatesId;
	}
	public void setCoordinatesId(long coordinatesId) {
		this.coordinatesId = coordinatesId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(int plantYear) {
		this.plantYear = plantYear;
	}
	public int getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(int plantNo) {
		this.plantNo = plantNo;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	
}
