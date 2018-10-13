package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Buyer implements Serializable, ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2560256160600774884L;
	
	private long buyerId;
	private String buyerName;
	
    private long regionNo;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private String buyerAddress;
    //
    private String addressNo;
    private int moo;
    private String soi; // datatype is char(5) in DB
    private String road; // datatype is char(5) in DB
    private String mobile;
    private String tel;
    private String fax;
    private String placeName;
    private String placeAddressNo; // datatype is char(5) in DB
    private int placeMoo;
    private String placeSoi;
    private String placeRoad;
    private long placeRegionNo;
    private long placeProvinceNo;
    private long placeDistrictNo;
    private long placeSubDistrictNo;
    private long breedTypeId;
    private long breedGroupId;
    private String qualification;
    private double quantity;
    private String paymentCondition;
    
    private boolean checkBox;// added for buyerList
	private String linkImageEdit;// added for buyerList
    private String provinceName;// added for buyerList
    private String districtName;// added for buyerList
    private String subDistrictName;// added for buyerList
    
    
    
	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
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

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
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

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceAddressNo() {
		return placeAddressNo;
	}

	public void setPlaceAddressNo(String placeAddressNo) {
		this.placeAddressNo = placeAddressNo;
	}

	public int getPlaceMoo() {
		return placeMoo;
	}

	public void setPlaceMoo(int placeMoo) {
		this.placeMoo = placeMoo;
	}

	public String getPlaceSoi() {
		return placeSoi;
	}

	public void setPlaceSoi(String placeSoi) {
		this.placeSoi = placeSoi;
	}

	public String getPlaceRoad() {
		return placeRoad;
	}

	public void setPlaceRoad(String placeRoad) {
		this.placeRoad = placeRoad;
	}

	public long getPlaceRegionNo() {
		return placeRegionNo;
	}

	public void setPlaceRegionNo(long placeRegionNo) {
		this.placeRegionNo = placeRegionNo;
	}

	public long getPlaceProvinceNo() {
		return placeProvinceNo;
	}

	public void setPlaceProvinceNo(long placeProvinceNo) {
		this.placeProvinceNo = placeProvinceNo;
	}

	public long getPlaceDistrictNo() {
		return placeDistrictNo;
	}

	public void setPlaceDistrictNo(long placeDistrictNo) {
		this.placeDistrictNo = placeDistrictNo;
	}

	public long getPlaceSubDistrictNo() {
		return placeSubDistrictNo;
	}

	public void setPlaceSubDistrictNo(long placeSubDistrictNo) {
		this.placeSubDistrictNo = placeSubDistrictNo;
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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getPaymentCondition() {
		return paymentCondition;
	}

	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public void setLinkImageEdit(String linkImageEdit) {
		this.linkImageEdit = linkImageEdit;
	}
	
	
    
}
