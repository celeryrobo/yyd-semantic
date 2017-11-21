package com.yyd.semantic.db.service.impl.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.story.StoryResource;
import com.yyd.semantic.db.mapper.story.StoryResourceMapper;
import com.yyd.semantic.db.service.story.StoryResourceService;

@Service
public class StoryResourceServiceImpl implements StoryResourceService {
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

	@Override
	public List<Integer> getIdList() {
		return srm.getIdList();
	}

	@Override
	public List<String> getAllNames() {
		return srm.getAllNames();
	}

}
