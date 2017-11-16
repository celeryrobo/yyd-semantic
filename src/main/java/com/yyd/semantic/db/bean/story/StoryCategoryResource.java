package com.yyd.semantic.db.bean.story;

public class StoryCategoryResource {
	private Integer id;
	private Integer categoryId;
	private Integer resourceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	@Override
	public String toString() {
		return "StoryCategoryResource [id=" + id + ", categoryId=" + categoryId + ", resourceId=" + resourceId + "]";
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
}
