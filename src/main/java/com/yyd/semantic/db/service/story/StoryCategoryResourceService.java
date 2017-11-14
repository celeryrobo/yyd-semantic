package com.yyd.semantic.db.service.story;

import java.util.List;

import com.yyd.semantic.db.bean.story.StoryCategoryResource;

public interface StoryCategoryResourceService {
	public List<StoryCategoryResource> getByCategoryId(int categoryId);

}
