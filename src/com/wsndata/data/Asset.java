package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;


public class Asset implements Serializable, ICheckOnGrid{

	
	private static final long serialVersionUID = 1832393670181605076L;
	private long assetId;
	private String assetName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String isTotal;
	
	private List<AssetDetail> assetDetail;
	
	private boolean checkBox;// added for assetList
	

	
	public String getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}
	public List<AssetDetail> getAssetDetail() {
		return assetDetail;
	}
	public void setAssetDetail(List<AssetDetail> assetDetail) {
		this.assetDetail = assetDetail;
	}
	public long getAssetId() {
		return assetId;
	}
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
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
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	
}
