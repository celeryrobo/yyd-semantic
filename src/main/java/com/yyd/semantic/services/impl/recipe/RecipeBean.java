package com.yyd.semantic.services.impl.recipe;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class RecipeBean extends AbstractSemanticResult {
	private String text;

	public RecipeBean(Integer errCode, String text) {
		this(errCode, text, null);
	}

	public RecipeBean(String text, Object resource) {
		this(0, text, resource);
	}

	public RecipeBean(Integer errCode, String text, Object resource) {
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
