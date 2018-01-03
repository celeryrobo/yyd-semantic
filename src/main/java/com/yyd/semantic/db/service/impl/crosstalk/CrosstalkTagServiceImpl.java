package com.yyd.semantic.db.service.impl.crosstalk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.crosstalk.CrosstalkTag;
import com.yyd.semantic.db.mapper.crosstalk.CrosstalkTagMapper;
import com.yyd.semantic.db.service.crosstalk.CrosstalkTagService;

@Service
public class CrosstalkTagServiceImpl implements CrosstalkTagService{
	@Autowired
	private CrosstalkTagMapper crosstalkTagMapper;
	
	@Override
	public List<Integer> getResourceIdsByTag(Integer tagId,Integer tagTypeId){
		return crosstalkTagMapper.getResourceIdsByTag(tagId, tagTypeId);
	}
	
	@Override
	public List<CrosstalkTag> getByResourceId(int resourceId){
		return crosstalkTagMapper.getByResourceId(resourceId);
	}
}
