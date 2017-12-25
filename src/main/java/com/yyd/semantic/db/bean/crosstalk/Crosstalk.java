package com.yyd.semantic.db.bean.crosstalk;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Crosstalk {
	private Integer id;
	private String name;
	@JsonIgnore
	private String resourceUrl;
	
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
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}	
}
