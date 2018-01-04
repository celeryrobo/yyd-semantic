package com.yyd.semantic.db.service.region;

import java.util.List;

import com.yyd.semantic.db.bean.region.PostCode;

public interface PostCodeService {
	public List<String> getAllCode();
	
	public List<PostCode> getByName(String name);
	
	public List<PostCode> getByCode(String code);	
	
	public List<PostCode> getByAreaId(Integer areaId);	
	
	public List<PostCode> getByAreaIdAndLevel(Integer areaId,Integer level);	
}
