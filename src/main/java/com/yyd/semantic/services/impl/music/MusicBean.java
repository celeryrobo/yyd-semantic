package com.yyd.semantic.services.impl.music;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class MusicBean extends AbstractSemanticResult {
	private String text;

	public MusicBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
