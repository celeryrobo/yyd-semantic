package com.yyd.semantic.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybnf.compiler.beans.YbnfCompileResult;

public class SemanticResult {
	private Integer errCode;
	private String errMsg;
	private String service;
	private String text;
	private Map<String, Object> slots;
	private Object data;

	public SemanticResult(Integer errCode, String errMsg, YbnfCompileResult result) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		if (result != null) {
			this.service = result.getService();
			this.text = result.getText();
			this.slots = new HashMap<String, Object>();
			this.slots.putAll(result.getSlots());
			this.slots.put("objects", result.getObjects());
		}
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
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

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Map<String, Object> getSlots() {
		return slots;
	}

	public void setSlots(Map<String, Object> slots) {
		this.slots = slots;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
