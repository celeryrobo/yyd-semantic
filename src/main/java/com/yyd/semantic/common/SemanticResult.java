package com.yyd.semantic.common;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SemanticResult {
	private Integer errCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errMsg;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String service;
	private String text;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, Object> slots;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;
	
	public SemanticResult(Integer errCode, String errMsg, String service, String text) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.service = service;
		this.text = text;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getSlots() {
		return slots;
	}

	public void setSlots(Map<String, Object> slots) {
		this.slots = slots;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
