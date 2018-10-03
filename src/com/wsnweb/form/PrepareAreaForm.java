package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.PrepareArea;

public class PrepareAreaForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3969223530849130647L;
	private long prepareAreaId;
	private long pprepareAreaId;
	private String prepareAreaName;
	private long breedTypeId;
	private long breedGroupId;
	
	private String cmd;
	private String msg;
	
	public long getPrepareAreaId() {
		return prepareAreaId;
	}
	public void setPrepareAreaId(long prepareAreaId) {
		this.prepareAreaId = prepareAreaId;
	}
	public long getPprepareAreaId() {
		return pprepareAreaId;
	}
	public void setPprepareAreaId(long pprepareAreaId) {
		this.pprepareAreaId = pprepareAreaId;
	}
	public String getPrepareAreaName() {
		return prepareAreaName;
	}
	public void setPrepareAreaName(String prepareAreaName) {
		this.prepareAreaName = prepareAreaName;
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
		PrepareArea pObj = (PrepareArea)o;
		pObj.setPrepareAreaId(this.prepareAreaId);
		pObj.setPprepareAreaId(this.pprepareAreaId);
		pObj.setPrepareAreaName(this.prepareAreaName);
		pObj.setBreedTypeId(this.breedTypeId);
		pObj.setBreedGroupId(this.breedGroupId);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			PrepareArea pObj = (PrepareArea)o;
			this.prepareAreaId = pObj.getPrepareAreaId();
			this.prepareAreaName = pObj.getPrepareAreaName();
			this.pprepareAreaId = pObj.getPprepareAreaId();
			this.breedTypeId = pObj.getBreedTypeId();
			this.breedGroupId = pObj.getBreedGroupId();
		}
	}
}
