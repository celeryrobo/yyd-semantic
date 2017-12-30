package com.yyd.semantic.db.bean.region;

public class CarNumber {
	private int id;
	private String name; //区域名
	private String code; //车牌号
	private int level;   //行政级别
	private int upper;   //上级区域id	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getUpper() {
		return upper;
	}
	public void setUpper(int upper) {
		this.upper = upper;
	}
}
