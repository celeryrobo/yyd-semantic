package com.yyd.semantic.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private Integer ret;
	private String msg;
	private String service;
	private String text;
	private Long time;
	private Map<String, Object> semantic;
	private Object data;
	
	private static Map<String, Object> emptyObject = new HashMap<>();

	public SemanticResult(Integer ret, String msg, YbnfCompileResult result) {
		this.ret = ret;
		this.msg = msg;
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

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getMsg() {
		if (msg == null) {
			return "";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getService() {
		if (service == null) {
			return "";
		}
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

	public Map<String, Object> getSemantic() {
		if (semantic == null) {
			return emptyObject;
		}
		return semantic;
	}

	public void setSemantic(Map<String, Object> semantic) {
		this.semantic = semantic;
	}

	public Object getData() {
		if (data == null) {
			return emptyObject;
		}
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
