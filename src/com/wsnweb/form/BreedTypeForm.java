package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.BreedType;

public class BreedTypeForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long breedTypeId;
	private String breedTypeName;
	private String cmd; 
	private String msg;
	private int maxPerYear;    // จำนวนที่มากที่สุดต่อปี
	
    


	public int getMaxPerYear() {
		return maxPerYear;
	}

	public void setMaxPerYear(int maxPerYear) {
		this.maxPerYear = maxPerYear;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getBreedTypeName() {
		return breedTypeName;
	}

	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
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
		BreedType btObj = (BreedType)o;
		btObj.setBreedTypeName(this.breedTypeName);
		btObj.setMaxPerYear(this.maxPerYear);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			BreedType btObj = (BreedType)o;
			this.breedTypeId = btObj.getBreedTypeId();
			this.breedTypeName = btObj.getBreedTypeName();
			this.maxPerYear = btObj.getMaxPerYear();
		}
	}
    
}
