package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.Branch;

public class BranchForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3765418999993465615L;
    
    private long branchCode;
    private String branchName;
    private String address;
    private int moo;
    private String soi;
    private String street;
    private String tel;
    private String fax;
    private String manager;
    
    private long pbranchCode;
    private long regionNo;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
    private int postCode;
    
    private String cmd;
    private String msg;
    
	public long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getMoo() {
		return moo;
	}
	public void setMoo(int moo) {
		this.moo = moo;
	}
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public long getPbranchCode() {
		return pbranchCode;
	}
	public void setPbranchCode(long pbranchCode) {
		this.pbranchCode = pbranchCode;
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
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
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
		Branch bObj = (Branch)o;
		bObj.setBranchName(this.branchName.trim());
		bObj.setAddress(this.address.trim());
		bObj.setMoo(this.moo);
		bObj.setSoi(this.soi.trim());
		bObj.setStreet(this.street.trim());
		bObj.setTel(this.tel.trim());
		bObj.setFax(this.fax.trim());
		bObj.setProvinceNo(this.provinceNo);
		bObj.setDistrictNo(this.districtNo);
		bObj.setSubDistrictNo(this.subDistrictNo);
		bObj.setPostCode(this.postCode);
		bObj.setManager(this.manager.trim());
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			Branch bObj = (Branch)o;
			this.address = bObj.getAddress();
			this.branchCode = bObj.getBranchCode();
			this.branchName = bObj.getBranchName();
			this.districtNo = bObj.getDistrictNo();
			this.fax = bObj.getFax();
			this.manager = bObj.getManager();
			this.moo = bObj.getMoo();
			this.pbranchCode = bObj.getPbranchCode();
			this.provinceNo = bObj.getProvinceNo();
			this.postCode = bObj.getPostCode();
			this.regionNo = bObj.getRegionNo();
			this.soi = bObj.getSoi();
			this.street = bObj.getStreet();
			this.subDistrictNo = bObj.getSubDistrictNo();
			this.tel = bObj.getTel();
		}
	}
}
