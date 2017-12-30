package com.yyd.semantic.services.impl.areacode;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class AreaCodeBean extends AbstractSemanticResult{
	private String text;

	public AreaCodeBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
