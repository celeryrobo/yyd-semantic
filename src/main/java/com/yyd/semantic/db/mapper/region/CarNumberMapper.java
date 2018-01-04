package com.yyd.semantic.db.mapper.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.region.CarNumber;

@Mapper
public interface CarNumberMapper {
	@Select("SELECT distinct car_number FROM yyd_resources.tb_region_car_number")
	public List<String> getAllNumber();
	
	@Select("SELECT id,name,area_id,car_number,level,upper,upper_level FROM yyd_resources.tb_region_car_number WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "car_number"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<CarNumber> getByName(String name);
	
	@Select("SELECT id,name,area_id,car_number,level,upper,upper_level FROM yyd_resources.tb_region_car_number WHERE car_number = #{number}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "car_number"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<CarNumber> getByNumber(String number);
	
	
	@Select("SELECT id,name,area_id,car_number,level,upper,upper_level FROM yyd_resources.tb_region_car_number WHERE area_id = #{areaId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "car_number"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<CarNumber> getByAreaId(Integer areaId);
	
	@Select("SELECT id,name,area_id,car_number,level,upper,upper_level FROM yyd_resources.tb_region_car_number WHERE area_id = #{0} AND level = #{1}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "car_number"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<CarNumber> getByAreaIdAndLevel(Integer areaId,Integer level);
}
