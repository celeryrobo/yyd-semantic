package com.yyd.semantic.services.impl.geling;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class GeLingBean extends AbstractSemanticResult{
	private String text;
	private String module;
	private String param;

	public GeLingBean(Integer errCode, String text) {
		setErrCode(errCode);
		setText(text);		
	}
	
	public GeLingBean(String module, String param) {
		this.module = module;
		this.param = param;
		setOperation(Operation.APP);
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
