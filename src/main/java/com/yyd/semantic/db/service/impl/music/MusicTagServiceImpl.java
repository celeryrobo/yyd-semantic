package com.yyd.semantic.db.service.impl.music;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.music.MusicTag;
import com.yyd.semantic.db.mapper.music.MusicTagMapper;
import com.yyd.semantic.db.service.music.MusicTagService;

@Service
public class MusicTagServiceImpl implements MusicTagService{
	@Autowired
	private MusicTagMapper musicTagMapper;
	
	@Override
	public List<Integer> getResourceIdsByTag(Integer tagId,Integer tagTypeId){
		return musicTagMapper.getResourceIdsByTag(tagId, tagTypeId);
	}
	
	@Override
	public List<MusicTag> getByResourceId(int resourceId){
		return musicTagMapper.getByResourceId(resourceId);
	}
}
