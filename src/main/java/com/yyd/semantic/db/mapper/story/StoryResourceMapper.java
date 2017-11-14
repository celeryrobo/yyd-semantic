package com.yyd.semantic.db.mapper.story;


import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.story.StoryResource;

public interface StoryResourceMapper {
	@Select("SELECT * FROM story_resource WHERE id = #{id}")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "score", column = "score"),
        @Result(property = "contentUrl", column = "content_url"),
    })
	public StoryResource getById(int id);
	
	@Select("SELECT * FROM story_resource WHERE name = #{name}")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "score", column = "score"),
        @Result(property = "contentUrl", column = "content_url"),
    })
	public StoryResource getByName(String name);
}
