package com.yyd.semantic.db.service.music;

import java.util.List;


import com.yyd.semantic.db.bean.music.MusicTag;

public interface MusicTagService {
	public List<Integer> getResourceIdsByTag(Integer tagId,Integer tagTypeId);
	
	public List<MusicTag> getByResourceId(int resourceId);
}
