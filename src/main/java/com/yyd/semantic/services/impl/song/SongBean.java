package com.yyd.semantic.services.impl.song;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class SongBean extends AbstractSemanticResult {
	private String text;

	public SongBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
