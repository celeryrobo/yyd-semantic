package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.Province;

public interface ProvinceService {
	public List<Province> getAll();
	
	public Province getById(int id);	
	
	public List<Province> getByAreaId(int areaId);	
	
	public List<String> getAllShortName();
	
	public List<String> getAllFullName();
	
	public List<Province> getByShortName(String shortName);
	
	public List<Province> getByFullName(String fullName);	
}
