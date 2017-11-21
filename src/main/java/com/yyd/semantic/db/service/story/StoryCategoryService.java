package com.yyd.semantic.db.service.story;

import java.util.List;

import com.yyd.semantic.db.bean.story.StoryCategory;

public interface StoryCategoryService {

	public StoryCategory getById(int id);

	public StoryCategory getByName(String name);
	
	public List<String> getAllNames();
}
