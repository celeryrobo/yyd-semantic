package com.yyd.semantic.services.impl.story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ybnf.compiler.beans.AbstractSemanticResult;

public class StoryBean extends AbstractSemanticResult {
	@JsonIgnore
	private String text;
	private String url;

	public StoryBean(String text, String url, Object resource) {
		this.text = text;
		this.url = url;
		setResource(resource);
		setOperation(Operation.PLAY);
		setParamType(ParamType.U);
	}

	public StoryBean(String text, int errCode, String errMsg) {
		this.text = text;
		setErrCode(errCode);
		setErrMsg(errMsg);
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
