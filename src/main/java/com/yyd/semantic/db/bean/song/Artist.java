package com.yyd.semantic.db.bean.song;

public class Artist {
	private Integer id;
	private String name;
	private String imageUrl;
	private Integer sourceId;
	private String sourceImageUrl;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceImageUrl() {
		return sourceImageUrl;
	}

	public void setSourceImageUrl(String sourceImageUrl) {
		this.sourceImageUrl = sourceImageUrl;
	}
}
