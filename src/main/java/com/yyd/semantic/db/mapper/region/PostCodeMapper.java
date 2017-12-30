package com.yyd.semantic.db.mapper.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.region.PostCode;

@Mapper
public interface PostCodeMapper {
	@Select("SELECT distinct post_code FROM yyd_resources.tb_region_post_code")
	public List<String> getAllCode();
	
	@Select("SELECT id,name,area_id,post_code,level,upper,upper_level FROM yyd_resources.tb_region_post_code WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "post_code"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<PostCode> getByName(String name);
	
	@Select("SELECT id,name,area_id,post_code,level,upper,upper_level FROM yyd_resources.tb_region_post_code WHERE post_code = #{code}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "post_code"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<PostCode> getByCode(String code);	
	
	@Select("SELECT id,name,area_id,post_code,level,upper,upper_level FROM yyd_resources.tb_region_post_code WHERE area_id = #{areaId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "post_code"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<PostCode> getByAreaId(int areaId);	
	
	@Select("SELECT id,name,area_id,post_code,level,upper,upper_level FROM yyd_resources.tb_region_post_code WHERE area_id = #{0} AND level = #{1}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "areaId", column = "area_id"),
		@Result(property = "code", column = "post_code"),
		@Result(property = "level", column = "level"),
		@Result(property = "upper", column = "upper"),
		@Result(property = "upperLevel", column = "upper_level"),
	})
	public List<PostCode> getByAreaIdAndLevel(int areaId,int level);	
}
