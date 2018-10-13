package com.wsndata.data;
import java.io.Serializable;

public class ProvinceDistrict implements Serializable{

	
	private static final long serialVersionUID = 4424743919856279207L;
	private long regionNo;
	private long provinceNo;
    private long districtNo;
	private long subDistrictNo;
    private String regionName;
    private String provinceThai;
    private String provinceEng;
    private String districtThai;
    private String districtEng;
    private String subDistrictThai;
    private String subDistrictEng;
    private int postCode;
    
   

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

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getProvinceThai() {
		return provinceThai;
	}

	public void setProvinceThai(String provinceThai) {
		this.provinceThai = provinceThai;
	}

	public String getProvinceEng() {
		return provinceEng;
	}

	public void setProvinceEng(String provinceEng) {
		this.provinceEng = provinceEng;
	}

	public String getDistrictThai() {
		return districtThai;
	}

	public void setDistrictThai(String districtThai) {
		this.districtThai = districtThai;
	}

	public String getDistrictEng() {
		return districtEng;
	}

	public void setDistrictEng(String districtEng) {
		this.districtEng = districtEng;
	}

	public String getSubDistrictThai() {
		return subDistrictThai;
	}

	public void setSubDistrictThai(String subDistrictThai) {
		this.subDistrictThai = subDistrictThai;
	}

	public String getSubDistrictEng() {
		return subDistrictEng;
	}

	public void setSubDistrictEng(String subDistrictEng) {
		this.subDistrictEng = subDistrictEng;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	
	
    
}
