package com.yyd.semantic.services.impl.carnumber;

import java.util.Map;

public class CarNumberSlot {
	public static final String REGION_PROV_SHORT = "provShort";
	public static final String REGION_PROV_FULL = "provFull";
	public static final String REGION_CITY_SHORT = "cityShort";
	public static final String REGION_CITY_FULL = "cityFull";
	public static final String REGION_DISTRICT_SHORT = "districtShort";
	public static final String REGION_DISTRICT_FULL = "districtFull";
	public static final String REGION_CARNUMBER = "carNumber";
	
	public static final String CARNUMBER_ID = "carNumberId";
	
	private Map<Object, Object> params;

	public CarNumberSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(CARNUMBER_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(CARNUMBER_ID, id.toString());
	}
}
