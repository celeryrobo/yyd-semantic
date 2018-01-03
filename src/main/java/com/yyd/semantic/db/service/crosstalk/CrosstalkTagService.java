package com.yyd.semantic.db.service.crosstalk;

import java.util.List;

import com.yyd.semantic.db.bean.crosstalk.CrosstalkTag;


public interface CrosstalkTagService {
	public List<Integer> getResourceIdsByTag(Integer tagId,Integer tagTypeId);
	
	public List<CrosstalkTag> getByResourceId(int resourceId);
}
