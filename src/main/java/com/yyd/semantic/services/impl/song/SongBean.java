package com.yyd.semantic.services.impl.song;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybnf.compiler.beans.AbstractSemanticResult;

public class SongBean extends AbstractSemanticResult {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String audioUrl;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String text;

	public SongBean(String name, String audioUrl) {
		this(name, audioUrl, null);
	}

	public SongBean(String text) {
		this(null, null, text);
	}

	public SongBean(String name, String audioUrl, String text) {
		super();
		this.setName(name);
		this.setAudioUrl(audioUrl);
		this.setText(text);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
