package com.yyd.semantic.services.impl.festival;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class FestivalBean extends AbstractSemanticResult {
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

	public FestivalBean(String text, Object resource) {
		this.text = text;
		setResource(resource);
		setOperation(Operation.SPEAK);
		setParamType(ParamType.T);
	}
	public FestivalBean(String text,Integer errCode) {
		this.text = text;
		setOperation(Operation.SPEAK);
		setParamType(ParamType.T);
		setErrCode(errCode);
	}

}
