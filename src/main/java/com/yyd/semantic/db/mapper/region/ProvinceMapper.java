package com.yyd.semantic.db.mapper.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.region.Province;

@Mapper
public interface ProvinceMapper {
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_province")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<Province> getAll();	
	
	@Select("SELECT distinct name  FROM yyd_resources.tb_region_province")
	public List<String> getAllShortName();	
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_province WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public Province getById(Integer id);	
	
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_province WHERE area_id = #{areaId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<Province> getByAreaId(Integer areaId);	
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_province WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<Province> getByName(String name);	
	
}
