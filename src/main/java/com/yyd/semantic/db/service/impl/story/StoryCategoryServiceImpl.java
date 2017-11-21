package com.yyd.semantic.db.service.impl.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.story.StoryCategory;
import com.yyd.semantic.db.mapper.story.StoryCategoryMapper;
import com.yyd.semantic.db.service.story.StoryCategoryService;

@Service
public class StoryCategoryServiceImpl implements StoryCategoryService {
	@Autowired
	private StoryCategoryMapper scm;

	@Override
	public StoryCategory getById(int id) {
		return scm.getById(id);
	}

	@Override
	public StoryCategory getByName(String name) {
		return scm.getByName(name);
	}

	@Override
	public List<String> getAllNames() {
		return scm.getAllNames();
	}

}
