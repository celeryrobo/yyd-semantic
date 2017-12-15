package com.yyd.semantic.services.impl.poetry;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PoetryBean extends AbstractSemanticResult {
	private String text;

	public PoetryBean(String text, Object resource) {
		this.text = text;
		setResource(resource);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
