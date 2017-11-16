package com.yyd.semantic.db.service.impl.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.story.StoryCategoryResource;
import com.yyd.semantic.db.mapper.story.StoryCategoryResourceMapper;
import com.yyd.semantic.db.service.story.StoryCategoryResourceService;

@Service
public class StoryCategoryResourceServiceImpl implements StoryCategoryResourceService {
	@Autowired
	private StoryCategoryResourceMapper scrm;

	@Override
	public List<StoryCategoryResource> getByCategoryId(int categoryId) {
		return scrm.getByCategoryId(categoryId);
	}

}
