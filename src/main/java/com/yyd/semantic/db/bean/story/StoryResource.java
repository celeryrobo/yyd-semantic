package com.yyd.semantic.db.bean.story;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StoryResource {
	private Integer id;
	private String name;
	@JsonIgnore
	private String playUrl;
	@JsonIgnore
	private Integer score;

	public Integer getId() {
		return id;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "StoryResource [id=" + id + ", name=" + name + ", playUrl=" + playUrl + ", score=" + score + "]";
	}

}
