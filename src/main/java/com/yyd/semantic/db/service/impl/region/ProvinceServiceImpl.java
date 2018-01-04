package com.yyd.semantic.db.service.impl.region;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.region.Province;
import com.yyd.semantic.db.mapper.region.ProvinceMapper;
import com.yyd.semantic.db.service.region.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{
	@Autowired
	private ProvinceMapper provinceMapper;
	
	@Override
	public List<Province> getAll(){
		return provinceMapper.getAll();
	}
	
	@Override
	public Province getById(Integer id) {
		return provinceMapper.getById(id);
	}
	
	@Override
	public List<Province> getByAreaId(Integer areaId){
		return provinceMapper.getByAreaId(areaId);
	}
	
	@Override
	public List<String> getAllShortName(){
		return provinceMapper.getAllShortName();
	}
	
	@Override
	public List<String> getAllFullName(){
		List<String> fullNameList = new ArrayList<String>();
		List<Province> list = getAll();
		
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
	public List<Province> getByShortName(String shortName){
		return provinceMapper.getByName(shortName);
	}
	
	@Override
	public List<Province> getByFullName(String fullName){
		List<Province> list = getAll();
		List<Province> resultt = new ArrayList<Province>();
		
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
				resultt.add(list.get(i));
			}
		}		
		
		return resultt;
		
	}	
	
}
