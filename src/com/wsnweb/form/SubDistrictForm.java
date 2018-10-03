package com.wsnweb.form;
import org.apache.struts.action.ActionForm;
import com.wsndata.data.SubDistrict;

public class SubDistrictForm extends ActionForm 
{

	private static final long serialVersionUID = -5973830026499316962L;
	private String regionNo;   	// dropdown - regionNo
	private String provinceNo; 	// dropdown - provinceNo
	private String districtNo; 	// dropdown - districtName
	private String subDistrictNo; // text - districtName
	private String thaiName;
	private String engName;
	private String postCode;
	private String cmd;
	private String msg;
	private String prevRegionNo;
	private String prevProvinceNo;
	private String prevDistrictNo;
	
	public String getPrevRegionNo() {
		return prevRegionNo;
	}
	public void setPrevRegionNo(String prevRegionNo) {
		this.prevRegionNo = prevRegionNo;
	}
	public String getPrevProvinceNo() {
		return prevProvinceNo;
	}
	public void setPrevProvinceNo(String prevProvinceNo) {
		this.prevProvinceNo = prevProvinceNo;
	}
	public String getPrevDistrictNo() {
		return prevDistrictNo;
	}
	public void setPrevDistrictNo(String prevDistrictNo) {
		this.prevDistrictNo = prevDistrictNo;
	}
	
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}
	public String getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(String subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
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
	public void loadFromBean(Object o) {
		SubDistrict sub = (SubDistrict)o;
		this.regionNo = String.valueOf(sub.getRegionNo());
		this.provinceNo = String.valueOf(sub.getProvinceNo());
		this.districtNo = String.valueOf(sub.getDistrictNo());
		this.subDistrictNo = String.valueOf(sub.getSubDistrictNo());
		this.postCode = String.valueOf(sub.getPostCode());
		this.engName = sub.getEngName();
		this.thaiName = sub.getThaiName();
	}
	
	
}
