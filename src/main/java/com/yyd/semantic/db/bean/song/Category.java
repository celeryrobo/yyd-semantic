package com.yyd.semantic.db.bean.song;

public class Category {
	private Integer id;
	private String name;
	private Integer srcId;

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

	public Integer getSrcId() {
		return srcId;
	}

	public void setSrcId(Integer srcId) {
		this.srcId = srcId;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", srcId=" + srcId + "]";
	}

}
