package com.yyd.semantic.services.impl.festival;

public class DateEntity {
	private String cnDate;
	private String enDate;
	private String lunarDate;
	private String weekDate;
	public String getCnDate() {
		return cnDate;
	}
	public String getWeekDate() {
		return weekDate;
	}
	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}
	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}
	public String getEnDate() {
		return enDate;
	}
	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}
	public String getLunarDate() {
		return lunarDate;
	}
	public void setLunarDate(String lunarDate) {
		this.lunarDate = lunarDate;
	}
	@Override
	public String toString() {
		return "DateEntity [cnDate=" + cnDate + ", enDate=" + enDate + ", lunarDate=" + lunarDate + ", weekDate="
				+ weekDate + "]";
	}
	
	
}
