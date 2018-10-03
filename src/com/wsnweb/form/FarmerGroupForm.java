package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.FarmerGroup;

public class FarmerGroupForm extends ActionForm {
	private static final long serialVersionUID = 8169053540850803596L;

	private long farmerGroupId;
	private String farmerGroupName;
	private String farmerGroupType;
	private long branchCode;
	private double target;
	private String addressNo;
	private int moo;
	private String village;
	private String soi;
	private String road;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
	private String postCode;
	private String mobile;
	private String tel;
	private String fax;
	private String objective;
	private long childFarmerGroupId;

	private String[] delTeamId;
	private String[] farmerGroupTeamId;
	private String[] farmerGroupTeamName;
	private String[] farmerGroupTeamPosition;

	private String cmd; 
	private String msg;
	
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String getFarmerGroupName() {
		return farmerGroupName;
	}
	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}
	public String getFarmerGroupType() {
		return farmerGroupType;
	}
	public void setFarmerGroupType(String farmerGroupType) {
		this.farmerGroupType = farmerGroupType;
	}
	public long getChildFarmerGroupId() {
		return childFarmerGroupId;
	}
	public void setChildFarmerGroupId(long childFarmerGroupId) {
		this.childFarmerGroupId = childFarmerGroupId;
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

	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public long getBranchCode() {
		return branchCode;
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
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
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
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
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
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String[] getDelTeamId() {
		return delTeamId;
	}
	public void setDelTeamId(String[] delTeamId) {
		this.delTeamId = delTeamId;
	}
	public String[] getFarmerGroupTeamId() {
		return farmerGroupTeamId;
	}
	public void setFarmerGroupTeamId(String[] farmerGroupTeamId) {
		this.farmerGroupTeamId = farmerGroupTeamId;
	}
	public String[] getFarmerGroupTeamName() {
		return farmerGroupTeamName;
	}
	public void setFarmerGroupTeamName(String[] farmerGroupTeamName) {
		this.farmerGroupTeamName = farmerGroupTeamName;
	}
	public String[] getFarmerGroupTeamPosition() {
		return farmerGroupTeamPosition;
	}
	public void setFarmerGroupTeamPosition(String[] farmerGroupTeamPosition) {
		this.farmerGroupTeamPosition = farmerGroupTeamPosition;
	}
	
	public void loadToBean(Object o) {
		FarmerGroup fgObj = (FarmerGroup)o;
		fgObj.setFarmerGroupName(this.farmerGroupName);
		fgObj.setTarget(this.target);
		fgObj.setAddressNo(this.addressNo);
		fgObj.setMoo(this.moo);
		fgObj.setVillage(this.village);
		fgObj.setSoi(this.soi);
		fgObj.setRoad(this.road);
		fgObj.setTel(this.tel);
		fgObj.setFax(this.fax);
		fgObj.setObjective(this.objective);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			FarmerGroup fgObj = (FarmerGroup)o;
			this.farmerGroupId = fgObj.getFarmerGroupId();
			this.farmerGroupName = fgObj.getFarmerGroupName();
			this.target = fgObj.getTarget();
			this.addressNo = fgObj.getAddressNo();
			this.moo = fgObj.getMoo();
			this.village = fgObj.getVillage();
			this.soi = fgObj.getSoi();
			this.road = fgObj.getRoad();
			this.tel = fgObj.getTel();
			this.fax = fgObj.getFax();
			this.farmerGroupType = fgObj.getFarmerGroupType();
			this.objective = fgObj.getObjective();
		}
	}
	
}
