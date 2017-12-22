package com.yyd.semantic.services.impl.date;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class DateBean extends AbstractSemanticResult{
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;

	}
	public DateBean(String text) {
		this.text=text;
		setOperation(Operation.SPEAK);
		setParamType(ParamType.T);
	}

}
