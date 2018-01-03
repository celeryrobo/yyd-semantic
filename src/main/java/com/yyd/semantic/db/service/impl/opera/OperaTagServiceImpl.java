package com.yyd.semantic.db.service.impl.opera;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.opera.OperaTag;
import com.yyd.semantic.db.mapper.opera.OperaTagMapper;
import com.yyd.semantic.db.service.opera.OperaTagService;

@Service
public class OperaTagServiceImpl implements OperaTagService{
	@Autowired
	private OperaTagMapper operaTagMapper;
	
	@Override
	public List<Integer> getResourceIdsByTag(Integer tagId, Integer tagTypeId){
		return operaTagMapper.getResourceIdsByTag(tagId, tagTypeId);
	}
	
	@Override
	public List<OperaTag> getByResourceId(int resourceId){
		return operaTagMapper.getByResourceId(resourceId);
	}
}
