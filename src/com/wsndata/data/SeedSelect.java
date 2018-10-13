package com.wsndata.data;

import java.io.Serializable;

public class SeedSelect implements Serializable {

	
	private static final long serialVersionUID = 6812156586812901134L;
	private long seedSelectId;
	private String seedSelectName;
	private long breedTypeId;
	private long breedGroupId;
	private BreedGroup breedGroup;	
	
	
	
	public BreedGroup getBreedGroup() {
		return breedGroup;
	}
	public void setBreedGroup(BreedGroup breedGroup) {
		this.breedGroup = breedGroup;
	}
	public long getSeedSelectId() {
		return seedSelectId;
	}
	public void setSeedSelectId(long seedSelectId) {
		this.seedSelectId = seedSelectId;
	}
	public String getSeedSelectName() {
		return seedSelectName;
	}
	public void setSeedSelectName(String seedSelectName) {
		this.seedSelectName = seedSelectName;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public long getBreedGroupId() {
		return breedGroupId;
	}
	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}
}
