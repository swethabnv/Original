package com.dcs.dcswc.schedule;

public class Schedule {
	public static final String dateFormat = "dd/MM/yyyy";
	private String scheduleID;
	private String scheduleName;
	private String startDate;
	private String endDate;
	private int duration;
	private int time;   //1:Morning, 2:Evening, 3:Fullday
	private String resourceID;
	private String color;
	private String[] dateList;
	private boolean moveAble = true;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

	public boolean getMoveAble() {
		return moveAble;
	}
	public boolean isMoveAble() {
		return moveAble;
	}
	public void setMoveAble(boolean moveAble) {
		this.moveAble = moveAble;
	}
	public String[] getDateList() {
		return dateList;
	}
	public void setDateList(String[] dateList) {
		this.dateList = dateList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
	}
}
