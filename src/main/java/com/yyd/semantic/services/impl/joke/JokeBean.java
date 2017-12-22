package com.yyd.semantic.services.impl.joke;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class JokeBean extends AbstractSemanticResult{
	private String text;

	public JokeBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
