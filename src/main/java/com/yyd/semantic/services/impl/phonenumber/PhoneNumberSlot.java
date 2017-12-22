package com.yyd.semantic.services.impl.phonenumber;

import java.util.Map;

public class PhoneNumberSlot {
	public static final String PHONENUMBER_NAME = "name";
	public static final String PHONENUMBER_NUMBER = "number";
	
	public static final String PHONENUMBER_ID = "phoneNumberId";
	
	private Map<Object, Object> params;

	public PhoneNumberSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(PHONENUMBER_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(PHONENUMBER_ID, id.toString());
	}
}
