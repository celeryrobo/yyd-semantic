package com.yyd.semantic.services.impl.story;

import java.util.Map;

public class StorySlot {
	public static final String STORY_RESOURCE = "storyResource";
	public static final String STORY_CATEGORY = "storyCategory";
	private Map<Object, Object> params;

	public StorySlot(Map<Object, Object> params) {
		this.params = params;
	}
	
}
