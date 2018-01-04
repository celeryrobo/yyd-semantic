package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.District;

public interface DistrictService {
	public List<District> getAll();
	
	public District getById(Integer id);
	
	public List<District> getByAreaId(Integer areaId);
	
	public List<District> getByName(String name);
	
	public List<District> getByUpperAndLevel(Integer upper,Integer upperLevel);
	
	public List<String> getAllShortName();
	
	public List<String> getAllFullName();
	
	public List<District> getByShortName(String shortName);
	
	public List<District> getByFullName(String fullName);
}
