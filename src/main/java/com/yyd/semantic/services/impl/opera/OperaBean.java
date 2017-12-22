package com.yyd.semantic.services.impl.opera;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class OperaBean extends AbstractSemanticResult{
	private String text;

	public OperaBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
