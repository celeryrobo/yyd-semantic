package com.yyd.semantic.db.mapper.joke;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.joke.JokeCategory;

@Mapper
public interface JokeCategoryMapper {
	@Select("SELECT name FROM yyd_resources.tb_joke_category")
	public List<String> getAllNames();
	
	@Select("SELECT id, name FROM yyd_resources.tb_joke_category WHERE id=#{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),		
	})
	public JokeCategory getById(Integer id);

	@Select("SELECT id, name FROM yyd_resources.tb_joke_category WHERE name=#{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),		
	})
	public List<JokeCategory> findByName(String name);
}
