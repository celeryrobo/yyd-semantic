package com.yyd.semantic.services.impl.festival;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class FestivalBean extends AbstractSemanticResult{
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "FestivalBean [text=" + text + "]";
	}

	public FestivalBean() {
	}

	public FestivalBean(String text) {
		super();
		this.text = text;
	}

}
