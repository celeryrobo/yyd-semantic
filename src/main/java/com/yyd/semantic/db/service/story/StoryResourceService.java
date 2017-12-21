package com.yyd.semantic.db.service.story;

import java.util.List;

import com.yyd.semantic.db.bean.story.StoryResource;

public interface StoryResourceService {

	public List<Integer> getIdList();

	public StoryResource getById(int id);

	public StoryResource getByName(String name);

	public List<String> getAllNames();
}
