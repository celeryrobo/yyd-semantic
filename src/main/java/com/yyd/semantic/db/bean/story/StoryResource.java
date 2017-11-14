package com.yyd.semantic.db.bean.story;

public class StoryResource {
	private Integer id;
	private String name;
	private String contentUrl;
	private Integer score;
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
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public StoryResource(Integer id, String name, String contentUrl, Integer score) {
		super();
		this.id = id;
		this.name = name;
		this.contentUrl = contentUrl;
		this.score = score;
	}
	@Override
	public String toString() {
		return "StoryResource [id=" + id + ", name=" + name + ", contentUrl=" + contentUrl + ", score=" + score + "]";
	}
	
}
