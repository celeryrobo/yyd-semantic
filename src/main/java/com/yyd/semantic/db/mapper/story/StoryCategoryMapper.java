package com.yyd.semantic.db.mapper.story;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.story.StoryCategory;

@Mapper
public interface StoryCategoryMapper {

	@Select("SELECT * FROM story.story_category WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"), })
	public StoryCategory getById(int id);

	@Select("SELECT * FROM story.story_category WHERE name = #{name}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"), })
	public StoryCategory getByName(String name);

	@Select("SELECT name FROM story.story_category")
	public List<String> getAllNames();
}
