package com.yyd.semantic.db.service.impl.region;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.District;
import com.yyd.semantic.db.mapper.region.DistrictMapper;
import com.yyd.semantic.db.service.region.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService{
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getAll(){
		return districtMapper.getAll();
	}
	
	@Override
	public District getById(Integer id) {
		return districtMapper.getById(id);
	}
	
	@Override
	public List<District> getByAreaId(Integer areaId){
		return districtMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<District> getByName(String name){
		return districtMapper.getByName(name);
	}
	
	@Override
	public List<District> getByUpperAndLevel(Integer upper,Integer upperLevel){
		return districtMapper.getByUpperAndLevel(upper, upperLevel);
	}
	
	@Override
	public List<String> getAllShortName(){
		return districtMapper.getAllShortName();
	}
	
	@Override
	public List<String> getAllFullName(){
		List<String> fullNameList = new ArrayList<String>();
		List<District> list = getAll();
		
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
	public List<District> getByShortName(String shortName){
		return districtMapper.getByName(shortName);
	}
	
	@Override
	public List<District> getByFullName(String fullName){
		List<District> result = new ArrayList<District>();
		List<District> list = getAll();
		
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
}
