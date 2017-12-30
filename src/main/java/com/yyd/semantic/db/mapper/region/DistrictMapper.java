package com.yyd.semantic.db.mapper.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.region.District;

@Mapper
public interface DistrictMapper {
	@Select("SELECT id,name,unit,area_id,upper,upper_level FROM yyd_resources.tb_region_district")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),	
		@Result(property = "upperLevel", column = "upper_level"),	
	})
	public List<District> getAll();
	
	@Select("SELECT distinct name FROM yyd_resources.tb_region_district")
	public List<String> getAllShortName();
	
	
	@Select("SELECT id,name,unit,area_id,upper,upper_level FROM yyd_resources.tb_region_district WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),	
		@Result(property = "upperLevel", column = "upper_level"),	
	})
	public District getById(int id);
		
	@Select("SELECT id,name,unit,area_id,upper,upper_level FROM yyd_resources.tb_region_district WHERE area_id = #{areaId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),	
		@Result(property = "upperLevel", column = "upper_level"),	
	})
	public List<District> getByAreaId(int areaId);
	
	@Select("SELECT id,name,unit,area_id,upper,upper_level FROM yyd_resources.tb_region_district WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),	
		@Result(property = "upperLevel", column = "upper_level"),	
	})
	public List<District> getByName(String name);
	
	
	@Select("SELECT id,name,unit,area_id,upper,upper_level FROM yyd_resources.tb_region_district WHERE upper = #{0} AND upper_level = #{1}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),	
		@Result(property = "upperLevel", column = "upper_level"),	
	})
	public List<District> getByUpperAndLevel(int upper,int upperLevel);
}
