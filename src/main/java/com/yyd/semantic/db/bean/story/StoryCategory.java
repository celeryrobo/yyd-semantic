package com.yyd.semantic.db.bean.story;

public class StoryCategory {
	private Integer id;
	private String name;
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

	public String toString() {
		return "StoryCategory [id=" + id + ", name=" + name + "]";
	}
}
