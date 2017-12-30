package com.yyd.semantic.services.impl.areacode;

import java.util.Map;

public class AreaCodeSlot {
	public static final String REGION_PROV_SHORT = "provShort";
	public static final String REGION_PROV_FULL = "provFull";
	public static final String REGION_CITY_SHORT = "cityShort";
	public static final String REGION_CITY_FULL = "cityFull";
	public static final String REGION_DISTRICT_SHORT = "districtShort";
	public static final String REGION_DISTRICT_FULL = "districtFull";
	public static final String REGION_AREACODE = "areaCode";
	
	public static final String AREACODE_ID = "areaCodeId";
	
	private Map<Object, Object> params;

	public AreaCodeSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(AREACODE_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(AREACODE_ID, id.toString());
	}
}
