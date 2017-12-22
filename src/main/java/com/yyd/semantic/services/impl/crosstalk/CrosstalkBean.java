package com.yyd.semantic.services.impl.crosstalk;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CrosstalkBean extends AbstractSemanticResult{
	private String text;

	public CrosstalkBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
