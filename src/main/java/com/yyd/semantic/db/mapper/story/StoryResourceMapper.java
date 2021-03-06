package com.yyd.semantic.db.mapper.story;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.story.StoryResource;

@Mapper
public interface StoryResourceMapper {
	@Select("SELECT id FROM yyd_resources.tb_story_resource")
	public List<Integer> getIdList();

	@Select("SELECT * FROM yyd_resources.tb_story_resource WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "score", column = "score"), @Result(property = "playUrl", column = "content_url"), })
	public StoryResource getById(int id);

	@Select("SELECT * FROM yyd_resources.tb_story_resource WHERE name = #{name}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "score", column = "score"), @Result(property = "playUrl", column = "content_url"), })
	public StoryResource getByName(String name);

	@Select("SELECT name FROM yyd_resources.tb_story_resource")
	public List<String> getAllNames();
}
