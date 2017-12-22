package com.yyd.semantic.services.impl.opera;

import java.util.Map;

public class OperaSlot {
	public static final String OPERA_ACTOR = "actor";
	public static final String OPERA_NAME = "name";
	public static final String OPERA_CATEGORY = "category";
	
	public static final String OPERA_ID = "operaId";
	
	private Map<Object, Object> params;

	public OperaSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(OPERA_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(OPERA_ID, id.toString());
	}
}
