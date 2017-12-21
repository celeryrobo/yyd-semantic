package com.yyd.semantic.db.bean.festival;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Festival {
	private int id;
	private String name;
	@JsonIgnore
	private String enDate;
	@JsonIgnore
	private String month;
	@JsonIgnore
	private String day;
	@JsonIgnore
	private int dateCode;
	@JsonIgnore
	private String enName;
	@JsonIgnore
	private String des;
	

	@Override
	public String toString() {
		return "Festival [id=" + id + ", name=" + name + ", enDate=" + enDate + ", month=" + month + ", day=" + day
				+ ", dateCode=" + dateCode + ", enName=" + enName + ", des=" + des + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnDate() {
		return enDate;
	}

	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public int getDateCode() {
		return dateCode;
	}

	public void setDateCode(int dateCode) {
		this.dateCode = dateCode;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getId() {
		return id;
	}
}
