package com.yyd.semantic.db.service.impl.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.CarNumber;
import com.yyd.semantic.db.mapper.region.CarNumberMapper;
import com.yyd.semantic.db.service.region.CarNumberService;

@Service
public class CarNumberServiceImpl implements CarNumberService{
	@Autowired
	private CarNumberMapper carNumberMapper;
	
	@Override
	public List<String> getAllNumber(){
		return carNumberMapper.getAllNumber();
	}
	
	@Override
	public List<CarNumber> getByName(String name){
		return carNumberMapper.getByName(name);
	}
	
	@Override
	public List<CarNumber> getByNumber(String number){
		return carNumberMapper.getByNumber(number);
	}
	
	@Override
	public List<CarNumber> getByAreaId(int areaId){
		return carNumberMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<CarNumber> getByAreaIdAndLevel(int areaId,int level){
		return carNumberMapper.getByAreaIdAndLevel(areaId, level);
	}
}
