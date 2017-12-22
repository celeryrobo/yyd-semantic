package com.yyd.semantic.services.impl.idiom;

import java.util.Map;

public class IdiomSlot {
	public final static String IDIOM_CONTENT = "idiomContent";

	private final static String IDIOM_ID = "idiomId";

	private Map<Object, Object> params;

	public IdiomSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getIdiomId() {
		String idiomId = (String) params.get(IDIOM_ID);
		if (idiomId == null) {
			return null;
		}
		return Integer.valueOf(idiomId);
	}

	public void setIdiomId(Integer idiomId) {
		params.put(IDIOM_ID, idiomId.toString());
	}
}
