package com.yyd.semantic.services.impl.postcode;

import java.util.Map;

public class PostCodeSlot {
	public static final String REGION_PROV_SHORT = "provShort";
	public static final String REGION_PROV_FULL = "provFull";
	public static final String REGION_CITY_SHORT = "cityShort";
	public static final String REGION_CITY_FULL = "cityFull";
	public static final String REGION_DISTRICT_SHORT = "districtShort";
	public static final String REGION_DISTRICT_FULL = "districtFull";
	public static final String REGION_POSTCODE = "postCode";
	
	public static final String POSTCODE_ID = "postCodeId";
	
	private Map<Object, Object> params;

	public PostCodeSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(POSTCODE_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(POSTCODE_ID, id.toString());
	}
}
