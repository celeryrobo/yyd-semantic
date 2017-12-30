package com.yyd.semantic.db.service.impl.region;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.City;
import com.yyd.semantic.db.mapper.region.CityMapper;
import com.yyd.semantic.db.service.region.CityService;

@Service
public class CityServiceImpl implements CityService{
	@Autowired
	private CityMapper citytMapper;
	
	@Override
	public List<City> getAll(){
		return citytMapper.getAll();
	}
	
	@Override
	public City getById(int id) {
		return citytMapper.getById(id);
	}
	
	@Override
	public List<City> getByAreaId(int areaId){
		return citytMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<String> getAllShortName(){
		return citytMapper.getAllShortName();
	}
	
	@Override
	public List<String> getAllFullName(){
		List<String> fullNameList = new ArrayList<String>();
		List<City> list = getAll();
		
		for(int i =0; i<list.size();i++) {
			String shortName = list.get(i).getName();
			String unit = list.get(i).getUnit();
			String fullName = null;
			if(null != unit) {
				fullName = shortName + unit;
			}
			else
			{
				fullName = shortName;
			}
			
			if(null != fullName) {
				fullNameList.add(fullName);
			}
		}
		
		
		return fullNameList;
	}
	
	@Override
	public List<City> getByShortName(String shortName){
		return citytMapper.getByName(shortName);
	}
	
	@Override
	public List<City> getByFullName(String fullName){
		List<City> result = new ArrayList<City>();
		List<City> list = getAll();
		
		for(int i =0; i<list.size();i++) {
			String shortName = list.get(i).getName();
			String unit = list.get(i).getUnit();
			String full = null;
			if(null != unit) {
				full = shortName + unit;
			}
			else
			{
				full = shortName;
			}
			
			if(fullName.equalsIgnoreCase(full)) {
				result.add(list.get(i));
			}
		}
		
		
		return result;
	}
	
	@Override
	public List<City> getByUpper(int upper){
		return citytMapper.getByUpper(upper);
	}
}
