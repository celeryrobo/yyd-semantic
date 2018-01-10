package com.yyd.semantic.services.impl.idiom;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class IdiomBean extends AbstractSemanticResult {
	private String text;

	public IdiomBean(Integer errCode, String text) {
		this(errCode, text, null);
	}

	public IdiomBean(String text, Object resource) {
		this(0, text, resource);
	}

	public IdiomBean(Integer errCode, String text, Object resource) {
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
