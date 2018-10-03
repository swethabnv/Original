package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class AssetListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "assetName";
	private String[] delAsset;
	private long assetId;
	private String assetName;
	
	public String[] getDelAsset() {
		return delAsset;
	}

	public void setDelAsset(String[] delAsset) {
		this.delAsset = delAsset;
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
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
