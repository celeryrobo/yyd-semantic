package com.yyd.semantic.services.impl.opera;

import com.ybnf.compiler.beans.AbstractSemanticResult;


public class OperaBean extends AbstractSemanticResult{
	private String text;
	private String url;
	
	public OperaBean(String text, String url, Object resource) {
		this.text = text;
		this.url = url;
		setResource(resource);		
	}

	public OperaBean(String text) {
		this.text = text;
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
