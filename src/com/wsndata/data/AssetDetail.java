package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class AssetDetail implements Serializable {
	
	
	private static final long serialVersionUID = -1008729639849941118L;
	private long plantId;
	private long assetId;
	private Date assetDate;
	private double amount;
	private Asset asset;
	private Plant plant;
	private long seq;


	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public long getPlantId() {
		if (plant != null) {
			return plant.getPlantId();
		} else {
			return plantId;
		}
	}
	public void setPlantId(long plantId) {
		if (plant != null) {
			plant.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	public long getAssetId() {
		if(asset !=null){
			return asset.getAssetId();
		}else{
			return assetId;
		}
	}
	public void setAssetId(long assetId) {
		if(asset !=null){
			asset.setAssetId(assetId);
		}else{
			this.assetId = assetId;
		}
	}
	public Date getAssetDate() {
		return assetDate;
	}
	public void setAssetDate(Date assetDate) {
		this.assetDate = assetDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	public Plant getPlant() {
		return plant;
	}

	
	
	
}
