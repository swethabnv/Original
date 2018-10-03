package com.wsnweb.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.Buyer;

public class BuyerForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long buyerId;
	private String buyerName;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private String addressNo;
    private int moo;
    private String soi;
    private String road;
	private long postCode;
    private String mobile;
    private String tel;
    private String fax;
    private String placeName;
    private String placeAddressNo;
    private int placeMoo;
    private String placeSoi;
    private String placeRoad;
    private long placePostCode;
    private long placeProvinceNo;
    private long placeDistrictNo;
    private long placeSubDistrictNo;
    private long breedTypeId;
    private long breedGroupId;
    private String qualification;
    private double quantity;
    private String paymentCondition;
	
	private String cmd; 
	private String msg;
	
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
    public long getPostCode() {
		return postCode;
	}
	public void setPostCode(long postCode) {
		this.postCode = postCode;
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
	public long getPlacePostCode() {
		return placePostCode;
	}
	public void setPlacePostCode(long placePostCode) {
		this.placePostCode = placePostCode;
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
		Buyer Obj = (Buyer)o;
		Obj.setBuyerName(this.buyerName);
		Obj.setProvinceNo(this.provinceNo);
		Obj.setDistrictNo(this.districtNo);
		Obj.setSubDistrictNo(this.subDistrictNo);
		Obj.setAddressNo(this.addressNo);
		Obj.setMoo(this.moo);
		Obj.setSoi(this.soi);
		Obj.setRoad(this.road);
		Obj.setMobile(this.mobile);
		Obj.setTel(this.tel);
		Obj.setFax(this.fax);
		Obj.setPlaceName(this.placeName);
		Obj.setPlaceAddressNo(this.placeAddressNo);
		Obj.setPlaceMoo(this.placeMoo);
		Obj.setPlaceSoi(this.placeSoi);
		Obj.setPlaceRoad(this.placeRoad);
		Obj.setPlaceProvinceNo(this.placeProvinceNo);
		Obj.setPlaceDistrictNo(this.placeDistrictNo);
		Obj.setPlaceSubDistrictNo(this.placeSubDistrictNo);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			Buyer Obj = (Buyer)o;
			this.buyerId = Obj.getBuyerId();
			this.buyerName = Obj.getBuyerName();
			this.provinceNo = Obj.getProvinceNo();
			this.districtNo = Obj.getDistrictNo();
			this.subDistrictNo = Obj.getSubDistrictNo();
			this.addressNo = Obj.getAddressNo();
			this.moo = Obj.getMoo();
			this.soi = Obj.getSoi();
			this.road = Obj.getRoad();
			this.mobile = Obj.getMobile();
			this.tel = Obj.getTel();
			this.fax = Obj.getFax();
			this.placeName = Obj.getPlaceName();
			this.placeAddressNo = Obj.getPlaceAddressNo();
			this.placeMoo = Obj.getPlaceMoo();
			this.placeSoi = Obj.getPlaceSoi();
			this.placeRoad = Obj.getPlaceRoad();
			this.placeProvinceNo = Obj.getPlaceProvinceNo();
			this.placeDistrictNo = Obj.getPlaceDistrictNo();
			this.placeSubDistrictNo = Obj.getPlaceSubDistrictNo();
			this.breedTypeId = Obj.getBreedTypeId();
			this.breedGroupId = Obj.getBreedGroupId();
			this.qualification = Obj.getQualification();
			this.quantity = Obj.getQuantity();
			this.paymentCondition = Obj.getPaymentCondition();
		}
	}
}
