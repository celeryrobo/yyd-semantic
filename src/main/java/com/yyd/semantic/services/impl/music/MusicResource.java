package com.yyd.semantic.services.impl.music;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MusicResource {
	private Integer id;
	private String name;
	private String singer;
	@JsonIgnore
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
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSongUrl() {
		return songUrl;
	}
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

}
