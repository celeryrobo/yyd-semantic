package com.yyd.semantic.services.impl.story;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class StoryBean extends AbstractSemanticResult{
	private String url;
	private String name;
	
	public StoryBean(String url,String name) {
		super();
		this.url = url;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
