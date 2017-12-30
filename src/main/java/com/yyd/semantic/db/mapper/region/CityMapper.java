package com.yyd.semantic.db.mapper.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.region.City;

@Mapper
public interface CityMapper {
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_city")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<City> getAll();
	
	@Select("SELECT distinct name FROM yyd_resources.tb_region_city")
	public List<String> getAllShortName();
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_city WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public City getById(int id);
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_city WHERE area_id = #{areaId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<City> getByAreaId(int areaId);
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_city WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<City> getByName(String name);
	
	
	@Select("SELECT id,name,unit,area_id,upper FROM yyd_resources.tb_region_city WHERE upper = #{upper}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "upper", column = "upper"),		
	})
	public List<City> getByUpper(int upper);
}
