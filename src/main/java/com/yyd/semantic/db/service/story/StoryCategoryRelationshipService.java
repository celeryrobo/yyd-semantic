package com.yyd.semantic.db.service.story;

import java.util.List;

import com.yyd.semantic.db.bean.story.StoryCategoryRelationship;

public interface StoryCategoryRelationshipService {

	public List<StoryCategoryRelationship> getByParentId(int parentId);
}
