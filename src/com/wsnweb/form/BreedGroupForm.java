package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.BreedGroup;

public class BreedGroupForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long breedTypeId;
	private long breedGroupId;
	private String breedGroupName;
	private int period;
	private String plantPeriodFrom;
	private String plantPeriodTo;
	private String forcastPeriodFrom;
	private String forcastPeriodTo;
	private String cmd; 
	private String msg;
	private String breedCategory;
	
    
	public String getBreedCategory() {
		return breedCategory;
	}

	public void setBreedCategory(String breedCategory) {
		this.breedCategory = breedCategory;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}

	public long getBreedGroupId() {
		return breedGroupId;
	}

	public void setBreedGroupName(String breedGroupName) {
		this.breedGroupName = breedGroupName;
	}

	public String getBreedGroupName() {
		return breedGroupName;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getPlantPeriodFrom() {
		return plantPeriodFrom;
	}

	public void setPlantPeriodFrom(String plantPeriodFrom) {
		this.plantPeriodFrom = plantPeriodFrom;
	}

	public String getPlantPeriodTo() {
		return plantPeriodTo;
	}

	public void setPlantPeriodTo(String plantPeriodTo) {
		this.plantPeriodTo = plantPeriodTo;
	}

	public String getForcastPeriodFrom() {
		return forcastPeriodFrom;
	}

	public void setForcastPeriodFrom(String forcastPeriodFrom) {
		this.forcastPeriodFrom = forcastPeriodFrom;
	}

	public String getForcastPeriodTo() {
		return forcastPeriodTo;
	}

	public void setForcastPeriodTo(String forcastPeriodTo) {
		this.forcastPeriodTo = forcastPeriodTo;
	}
	
	public void loadToBean(Object o) {
		BreedGroup bgObj = (BreedGroup)o;
		bgObj.setBreedGroupId(this.breedGroupId);
		bgObj.setBreedGroupName(this.breedGroupName);
		bgObj.setBreedTypeId(this.breedTypeId);
		bgObj.setPeriod(this.period);
		bgObj.setPlantPeriodFrom(this.plantPeriodFrom);
		bgObj.setPlantPeriodTo(this.plantPeriodTo);
		bgObj.setForcastPeriodFrom(this.forcastPeriodFrom);
		bgObj.setForcastPeriodTo(this.forcastPeriodTo);
		bgObj.setBreedCategory(this.breedCategory);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			BreedGroup bObj = (BreedGroup)o;
			this.breedGroupId = bObj.getBreedGroupId();
			this.breedGroupName = bObj.getBreedGroupName();
			this.breedTypeId = bObj.getBreedTypeId();
			this.period = bObj.getPeriod();
			this.plantPeriodFrom = bObj.getPlantPeriodFrom();
			this.plantPeriodTo = bObj.getPlantPeriodTo();
			this.forcastPeriodFrom = bObj.getForcastPeriodFrom();
			this.forcastPeriodTo = bObj.getForcastPeriodTo();
			this.breedCategory = bObj.getBreedCategory();
		}
	}
	
    
}
