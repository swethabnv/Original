package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class LandRightForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private String docNo;
    private long docRai;
    private long docNgan;
    private long docWah;
    private String irrigationIn;
    private int seq;
    private long typeId;
    private long regionNo;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
    private String province;
    private String landRight;
	
	private String cmd; 
	private String msg;
	
	public long getPlantId() {
		return plantId;
	}
	public void setPlantId(long plantId) {
		this.plantId = plantId;
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
//	public long getDocNo() {
//		return docNo;
//	}
//	public void setDocNo(long docNo) {
//		this.docNo = docNo;
//	}
	
	
	public long getDocRai() {
		return docRai;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public void setDocRai(long docRai) {
		this.docRai = docRai;
	}
	public long getDocNgan() {
		return docNgan;
	}
	public void setDocNgan(long docNgan) {
		this.docNgan = docNgan;
	}
	public long getDocWah() {
		return docWah;
	}
	public void setDocWah(long docWah) {
		this.docWah = docWah;
	}
	
	
	public String getIrrigationIn() {
		return irrigationIn;
	}
	public void setIrrigationIn(String irrigationIn) {
		this.irrigationIn = irrigationIn;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public long getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(long districtNo) {
		this.districtNo = districtNo;
	}
	public long getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(long subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
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
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvince() {
		return province;
	}
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}
	public String getLandRight() {
		return landRight;
	}

}
