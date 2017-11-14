package com.yyd.semantic.db.service.impl.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.story.StoryResource;
import com.yyd.semantic.db.mapper.story.StoryResourceMapper;

@Service
public class StoryResourceServiceImpl implements StoryResourceMapper{
	@Autowired
	private StoryResourceMapper srm;
	@Override
	public StoryResource getById(int id) {
		return srm.getById(id);
	}

	@Override
	public StoryResource getByName(String name) {
		return srm.getByName(name);
	}

}
