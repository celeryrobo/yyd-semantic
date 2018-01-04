package com.yyd.semantic.db.service.impl.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.AreaCode;
import com.yyd.semantic.db.mapper.region.AreaCodeMapper;
import com.yyd.semantic.db.service.region.AreaCodeService;


@Service
public class AreaCodeServiceImpl implements AreaCodeService{	
	@Autowired
	private AreaCodeMapper areaCodeMapper;
	
	@Override
	public List<String> getAllCode(){
		return areaCodeMapper.getAllCode();
	}
	
	@Override
	public List<AreaCode> getByName(String name){
		return areaCodeMapper.getByName(name);
	}
	
	@Override
	public List<AreaCode> getByCode(String code){
		return areaCodeMapper.getByCode(code);
	}
	
	@Override
	public List<AreaCode> getByAreaId(Integer areaId){
		return areaCodeMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<AreaCode> getByAreaIdAndLevel(Integer areaId,Integer level){
		return areaCodeMapper.getByAreaIdAndLevel(areaId, level);
	}
}
