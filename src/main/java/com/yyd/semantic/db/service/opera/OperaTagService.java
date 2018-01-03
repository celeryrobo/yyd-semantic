package com.yyd.semantic.db.service.opera;

import java.util.List;

import com.yyd.semantic.db.bean.opera.OperaTag;

public interface OperaTagService {	
	public List<Integer> getResourceIdsByTag(Integer tagId, Integer tagTypeId);
	
	public List<OperaTag> getByResourceId(int resourceId);
}
