package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.City;

public interface CityService {
	public List<City> getAll();
	
	public City getById(int id);
	
	public List<City> getByAreaId(int areaId);
	
	public List<String> getAllShortName();
	
	public List<String> getAllFullName();
	
	public List<City> getByShortName(String shortName);
	
	public List<City> getByFullName(String fullName);
	
	public List<City> getByUpper(int upper);
}
