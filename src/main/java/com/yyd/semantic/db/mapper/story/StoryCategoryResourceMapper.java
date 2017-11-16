package com.yyd.semantic.db.mapper.story;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.story.StoryCategoryResource;

@Mapper
public interface StoryCategoryResourceMapper {

	@Select("SELECT * FROM story.story_category_resource WHERE category_id = #{categoryId}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "categoryId", column = "category_id"),
			@Result(property = "resourceId", column = "resource_id"), })
	public List<StoryCategoryResource> getByCategoryId(int categoryId);
}