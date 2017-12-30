package com.yyd.semantic.services.impl.carnumber;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CarNumberBean extends AbstractSemanticResult{
	private String text;

	public CarNumberBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
