package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.PlantMethod;

public class PlantMethodForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3969223530849130647L;
	private long plantMethodId;
	private String plantMethodName;
	private long breedTypeId;
	private long breedGroupId;
	
	private String cmd;
	private String msg;
	
	public long getPlantMethodId() {
		return plantMethodId;
	}
	public void setPlantMethodId(long plantMethodId) {
		this.plantMethodId = plantMethodId;
	}
	public String getPlantMethodName() {
		return plantMethodName;
	}
	public void setPlantMethodName(String plantMethodName) {
		this.plantMethodName = plantMethodName;
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
	
	public void loadToBean(Object o) {
		PlantMethod pmObj = (PlantMethod)o;
		pmObj.setPlantMethodName(this.plantMethodName);
		pmObj.setBreedTypeId(this.breedTypeId);
		pmObj.setBreedGroupId(this.breedGroupId);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			PlantMethod pmObj = (PlantMethod)o;
			this.plantMethodId = pmObj.getPlantMethodId();
			this.plantMethodName = pmObj.getPlantMethodName();
			this.breedTypeId = pmObj.getBreedTypeId();
			this.breedGroupId = pmObj.getBreedGroupId();
		}
	}
}
