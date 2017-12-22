package com.yyd.semantic.services.impl.crosstalk;

import java.util.Map;

public class CrosstalkSlot {
	public static final String CROSSTALK_ACTPR = "actor";
	public static final String CROSSTALK_NAME = "name";
	public static final String CROSSTALK_CATEGORY = "category";
	
	public static final String CROSSTALK_ID = "crosstalkId";
	
	private Map<Object, Object> params;

	public CrosstalkSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(CROSSTALK_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(CROSSTALK_ID, id.toString());
	}
}
