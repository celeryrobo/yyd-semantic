package com.yyd.semantic.db.service.impl.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.story.StoryCategoryRelationship;
import com.yyd.semantic.db.mapper.story.StoryCategoryRelationshipMapper;
import com.yyd.semantic.db.service.story.StoryCategoryRelationshipService;

@Service
public class StoryCategoryRelationshipServiceImpl implements StoryCategoryRelationshipService {

	@Autowired
	private StoryCategoryRelationshipMapper scrm;

	@Override
	public List<StoryCategoryRelationship> getByParentId(int parentId) {
		return scrm.getByParentId(parentId);
	}

}
