package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Branch implements Serializable, ICheckOnGrid {

	
	private static final long serialVersionUID = 3850582118574572200L;
    private long branchCode;
    private long pbranchCode;
    private String branchName;
    private String address;
    private String tel;
    private String fax;
    private long seq;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private long regionNo;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
    // 
    private String manager;
    private int moo;
    private String soi;
    private String street;
    private int postCode;
    private String branchType; // only 1 character
    
    
    private List<Branch> childBranch;
    private Branch parentBranch;
    
    private boolean checkBox;// added for branchList
	private String pbranchName;// added for branchList
	private String provinceName;// added for branchList
	private String districtName;// added for branchList
	private String subDistrictName;// added for branchList
    
	
	
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
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public String getPbranchName() {
		return pbranchName;
	}
	public void setPbranchName(String pbranchName) {
		this.pbranchName = pbranchName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSubDistrictName() {
		return subDistrictName;
	}
	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}
	public long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public long getPbranchCode() {
		return pbranchCode;
	}
	public void setPbranchCode(long pbranchCode) {
		this.pbranchCode = pbranchCode;
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
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
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
	public List<Branch> getChildBranch() {
		return childBranch;
	}
	public void setChildBranch(List<Branch> childBranch) {
		this.childBranch = childBranch;
	}
	public Branch getParentBranch() {
		return parentBranch;
	}
	public void setParentBranch(Branch parentBranch) {
		this.parentBranch = parentBranch;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
    
    
    
}
