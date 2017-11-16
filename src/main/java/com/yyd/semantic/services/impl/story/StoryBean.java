package com.yyd.semantic.services.impl.story;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class StoryBean extends AbstractSemanticResult{
	private String url;

	
	public StoryBean(String url) {
		super();
		this.url = url;
	}

	@Override
	public String toString() {
		return "StoryBean [url=" + url + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
