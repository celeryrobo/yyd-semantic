package com.yyd.semantic.db.mapper.joke;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.joke.Joke;


@Mapper
public interface JokeMapper {
	@Select("SELECT id FROM yyd_resources.tb_joke_resource ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT name FROM yyd_resources.tb_joke_resource")
	public List<String> getAllName();
	
	@Select("SELECT content FROM yyd_resources.tb_joke_resource LIMIT 10")
	public List<String> getContentList();
	
	@Select("SELECT id, name, content, content_url FROM yyd_resources.tb_joke_resource WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "content", column = "content"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public Joke getById(Integer id);
	
	@Select("SELECT id, name, content, content_url FROM yyd_resources.tb_joke_resource WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "content", column = "content"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public List<Joke> getByName(String name);	
}
