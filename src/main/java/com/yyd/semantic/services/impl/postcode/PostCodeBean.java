package com.yyd.semantic.services.impl.postcode;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PostCodeBean extends AbstractSemanticResult{
	private String text;

	public PostCodeBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
