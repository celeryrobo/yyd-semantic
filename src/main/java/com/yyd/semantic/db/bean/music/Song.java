package com.yyd.semantic.db.bean.music;

public class Song {
	private Integer id;
	private String name;
	private Integer singerId;
	private String songUrl;	

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

	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	public Integer getSingerId() {
		return singerId;
	}

	public void setSingerId(Integer singerId) {
		this.singerId = singerId;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", songUrl=" + songUrl + ", artistId=" + singerId + "]";
	}
}
