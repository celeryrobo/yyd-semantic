package com.yyd.semantic.services.impl.poetry;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PoetryBean extends AbstractSemanticResult {
	private String text;

	public PoetryBean(String text, Object resource) {
		this(0, text, resource);
	}

	public PoetryBean(Integer errCode, String text) {
		this(errCode, text, null);
	}

	public PoetryBean(Integer errCode, String text, Object resource) {
		setErrCode(errCode);
		setText(text);
		setResource(resource);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
