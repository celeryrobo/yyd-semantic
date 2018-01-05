package com.yyd.semantic.services.impl.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ybnf.compiler.beans.AbstractSemanticResult;


public class MusicBean extends AbstractSemanticResult {
	@JsonIgnore
	public static final int PLAY_NO = 0;             //无须播放资源，以text内容作为回复内容
	@JsonIgnore
	public static final int PLAY_NORMAL = 1;         //常规播放
	@JsonIgnore
	public static final int PLAY_REPEAT_SINGLE= 2;      //单曲循环
	
	private String text;
	private String url;
	private int    mode; 
	
	//执行正确时使用
	public MusicBean(String text, String url, int mode,Object resource,Operation operation,ParamType paramType) {
		this.text = text;
		this.url = url;
		this.mode = mode;
		setResource(resource);	
		setOperation(operation);
		setParamType(paramType);
	}

	//执行错误时使用
	public MusicBean(Integer errorCode,String text) {
		this.mode = PLAY_NO;
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
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
