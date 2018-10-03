package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class BranchListForm extends ListForm {
	private static final long serialVersionUID = 3765418999993465615L;
	private String sortColumn = "branchName";
	private String[] delBranchCode;
    private String branchName;
	//private String cmd;
	
	private long pbranchCode;
	private String districtName;
	private String subDistrictName;
	private String provinceName;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
	
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
/*	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
*/
	public void setPbranchCode(long pbranchCode) {
		this.pbranchCode = pbranchCode;
	}

	public long getPbranchCode() {
		return pbranchCode;
	}

	public void setDelBranchCode(String[] delBranchCode) {
		this.delBranchCode = delBranchCode;
	}

	public String[] getDelBranchCode() {
		return delBranchCode;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}

	public String getSubDistrictName() {
		return subDistrictName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return provinceName;
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
	
}
