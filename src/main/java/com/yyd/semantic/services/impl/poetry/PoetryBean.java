package com.yyd.semantic.services.impl.poetry;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PoetryBean extends AbstractSemanticResult{
	private Integer id;
	private String text;

	public PoetryBean() {
		
	}
	
	public PoetryBean(Integer id, String text) {
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
