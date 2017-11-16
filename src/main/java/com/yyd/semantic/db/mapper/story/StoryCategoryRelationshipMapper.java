package com.yyd.semantic.db.mapper.story;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.story.StoryCategoryRelationship;

@Mapper
public interface StoryCategoryRelationshipMapper {
	@Select("SELECT * FROM story.story_category_relationship WHERE parent_id = #{parentId}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "subId", column = "sub_id"),
			@Result(property = "parentId", column = "parent_id"), })

	public List<StoryCategoryRelationship> getByParentId(int parentId);

}
