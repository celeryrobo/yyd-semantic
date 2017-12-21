package com.yyd.semantic.services.impl.festival;

public class DateEntity {
	private String cnDate; // xxxx年xx月xx日
	private String enDate; // yyyy-mm-dd 2017-12-18
	private String lunarDate; // 丁酉年十一月初一
	private String weekDate; // 星期几

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
