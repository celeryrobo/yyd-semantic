package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.AreaCode;

public interface AreaCodeService {
	public List<String> getAllCode();
	
	public List<AreaCode> getByName(String name);
	
	public List<AreaCode> getByCode(String code);
	
	public List<AreaCode> getByAreaId(Integer areaId);
	
	public List<AreaCode> getByAreaIdAndLevel(Integer areaId,Integer level);
}
