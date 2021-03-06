package com.yyd.semantic.db.bean.region;

public class District {
	private Integer id;
	private String name;
	private String unit;
	private Integer upper;	
	private Integer upperLevel; //上级级别
	private Integer areaId;	
	
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}	
	public Integer getUpperLevel() {
		return upperLevel;
	}
	public void setUpperLevel(Integer upperLevel) {
		this.upperLevel = upperLevel;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getUpper() {
		return upper;
	}
	public void setUpper(Integer upper) {
		this.upper = upper;
	}

}
