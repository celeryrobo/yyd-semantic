package com.yyd.semantic.db.service.impl.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.PostCode;
import com.yyd.semantic.db.mapper.region.PostCodeMapper;
import com.yyd.semantic.db.service.region.PostCodeService;

@Service
public class PostCodeServiceImpl implements PostCodeService{
	@Autowired
	private PostCodeMapper postCodeMapper;
	
	@Override
	public List<String> getAllCode(){
		return postCodeMapper.getAllCode();
	}
	
	@Override
	public List<PostCode> getByName(String name){
		return postCodeMapper.getByName(name);
	}
	
	@Override
	public List<PostCode> getByCode(String code){
		return postCodeMapper.getByCode(code);
	}
	
	@Override
	public List<PostCode> getByAreaId(Integer areaId){
		return postCodeMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<PostCode> getByAreaIdAndLevel(Integer areaId,Integer level){
		return postCodeMapper.getByAreaIdAndLevel(areaId, level);
	}
}
