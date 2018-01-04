package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.CarNumber;

public interface CarNumberService {
	public List<String> getAllNumber();
	
	public List<CarNumber> getByName(String name);
	
	public List<CarNumber> getByNumber(String number);
	
	public List<CarNumber> getByAreaId(Integer areaId);
	
	public List<CarNumber> getByAreaIdAndLevel(Integer areaId,Integer level);
}
