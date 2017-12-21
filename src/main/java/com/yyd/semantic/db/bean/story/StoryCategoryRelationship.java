package com.yyd.semantic.db.bean.story;

public class StoryCategoryRelationship {
	private Integer id;
	private Integer subId;
	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	@Override
	public String toString() {
		return "StoryCategoryRelationship [id=" + id + ", subId=" + subId + ", parentId=" + parentId + "]";
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
