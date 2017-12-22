package com.yyd.semantic.services.impl.joke;

import java.util.Map;

public class JokeSlot {
	public static final String JOKE_CATEGORY = "category";
	
	public static final String JOKE_ID = "jokeId";
	
	private Map<Object, Object> params;

	public JokeSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getId() {
		String id = (String) params.get(JOKE_ID);
		if (id == null) {
			return null;
		}
		return Integer.valueOf(id);
	}

	public void setId(Integer id) {
		params.put(JOKE_ID, id.toString());
	}
}
