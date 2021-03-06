package com.yyd.semantic.services.impl.crosstalk;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CrosstalkBean extends AbstractSemanticResult{
	private String text;
	private String url;
	
	public CrosstalkBean(String text, String url, Object resource) {
		this.text = text;
		this.url = url;
		setResource(resource);		
		setOperation(Operation.PLAY);
		setParamType(ParamType.U);
	}

	public CrosstalkBean(Integer errorCode,String text) {
		this.text = text;
		setErrCode(errorCode);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

}
