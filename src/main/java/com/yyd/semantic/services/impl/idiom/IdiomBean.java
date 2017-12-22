package com.yyd.semantic.services.impl.idiom;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class IdiomBean extends AbstractSemanticResult {
	private String text;

	public IdiomBean(String text, Object resource) {
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
