package com.yyd.semantic.db.service.story;

import com.yyd.semantic.db.bean.story.StoryResource;

public interface StoryResourceService {
	
	public StoryResource getById(int id);
	
	public StoryResource getByName(String name);
}
