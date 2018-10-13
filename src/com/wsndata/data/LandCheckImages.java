package com.wsndata.data;

import java.io.Serializable;
import java.sql.Blob;

public class LandCheckImages implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 677923660164539079L;
	private long imageId;
	private String imageName;
	private Blob imageBlob;
	private String contentType;
	private long landCheckId;
	
	private long seq;
	private long seqIndex;

	private String cmd;
	private String msg;
	
	private LandCheck landCheck;
	
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
		if(this.imageId >0 && (this.imageName == null || "".equals(this.imageName) || "0".equals(this.imageName) )){
			this.imageName = imageId+"";
		}
		
	}

	public Blob getImageBlob() {
		return imageBlob;
	}
	public void setImageBlob(Blob imageBlob) {
		this.imageBlob = imageBlob;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getLandCheckId() {
		if(landCheck !=null){
			return landCheck.getLandCheckId();
		}else{
			return landCheckId;
		}
	}
	public void setLandCheckId(long landCheckId) {
		if(landCheck !=null){
			landCheck.setLandCheckId(landCheckId);
		}else{
			this.landCheckId = landCheckId;
		}
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getSeqIndex() {
		return seqIndex;
	}
	public void setSeqIndex(long seqIndex) {
		this.seqIndex = seqIndex;
	}
	public LandCheck getLandCheck() {
		return landCheck;
	}
	public void setLandCheck(LandCheck landCheck) {
		this.landCheck = landCheck;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
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
	
}
