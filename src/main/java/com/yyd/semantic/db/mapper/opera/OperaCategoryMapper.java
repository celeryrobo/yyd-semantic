package com.yyd.semantic.db.mapper.opera;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.opera.OperaCategory;


@Mapper
public interface OperaCategoryMapper {
	@Select("SELECT name FROM yyd_resources.tb_opera_category")
	public List<String> getAllNames();
	
	@Select("SELECT id, name  FROM yyd_resources.tb_opera_category WHERE id=#{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),		
	})
	public OperaCategory getById(Integer id);

	@Select("SELECT id, name  FROM yyd_resources.tb_opera_category WHERE name=#{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),	
	})
	public List<OperaCategory> findByName(String name);
}
