package com.yyd.semantic.db.bean.region;

public class District {
	private int id;
	private String name;
	private String unit;
	private int upper;	
	private int upperLevel; //上级级别
	private int areaId;	
	
	
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}	
	public int getUpperLevel() {
		return upperLevel;
	}
	public void setUpperLevel(int upperLevel) {
		this.upperLevel = upperLevel;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getUpper() {
		return upper;
	}
	public void setUpper(int upper) {
		this.upper = upper;
	}

}
