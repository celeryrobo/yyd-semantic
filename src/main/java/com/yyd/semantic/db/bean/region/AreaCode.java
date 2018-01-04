package com.yyd.semantic.db.bean.region;

public class AreaCode {
	private Integer id;	
	private String name; //区域名
	private String code; //区号
	private Integer level;   //行政级别
	private Integer upper;   //上级区域id
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getUpper() {
		return upper;
	}
	public void setUpper(Integer upper) {
		this.upper = upper;
	}
}
