package com.yyd.semantic.services.impl.recipe;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class RecipeBean extends AbstractSemanticResult {
	private String text;

	public RecipeBean(String text) {
		setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
