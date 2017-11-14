package com.yyd.semantic.db.service.story;

import com.yyd.semantic.db.bean.story.StoryCategory;

public interface StoryCategoryService {

	public StoryCategory getById(int id);
	
	public StoryCategory getByName(String name);
}
