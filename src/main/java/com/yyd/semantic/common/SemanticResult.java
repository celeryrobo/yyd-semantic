package com.yyd.semantic.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybnf.compiler.beans.YbnfCompileResult;

class KV {
	private String key;
	private String value;

	public KV(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

public class SemanticResult {
	private Integer errCode;
	private String errMsg;
	private String service;
	private String text;
	private Long time;
	private Map<String, Object> semantic;
	private Object resource;

	public SemanticResult(Integer errCode, String errMsg, YbnfCompileResult result) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		if (result != null) {
			this.service = result.getService();
			this.text = result.getText();
			this.semantic = new HashMap<String, Object>();
			this.semantic.putAll(result.getSlots());
			Map<String, String> objects = result.getObjects();
			List<KV> slots = new LinkedList<>();
			if (objects != null) {
				for (Map.Entry<String, String> entry : objects.entrySet()) {
					slots.add(new KV(entry.getKey(), entry.getValue()));
				}
			}
			this.semantic.put("slots", slots);
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
	public Map<String, Object> getSemantic() {
		return semantic;
	}

	public void setSemantic(Map<String, Object> semantic) {
		this.semantic = semantic;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
