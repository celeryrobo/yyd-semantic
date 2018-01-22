package com.yyd.semantic.services.impl.calc;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CalcBean extends AbstractSemanticResult{
	private String text;

	public CalcBean(String text) {
		this.text = text;
	}
	
	public CalcBean(Integer errorCode,String text) {
		this.text = text;
		setErrCode(errorCode);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
