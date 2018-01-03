package com.yyd.semantic.db.mapper.crosstalk;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.crosstalk.CrosstalkTag;

@Mapper
public interface CrosstalkTagMapper {
	@Select("SELECT resource_id FROM yyd_resources.tb_crosstalk_resource_tag WHERE tag_id = #{tagId}  AND tag_type_id = #{tagTypeId} ORDER BY RAND() limit 10")
	public List<Integer> getResourceIdsByTag(@Param("tagId") Integer tagId,@Param("tagTypeId") Integer tagTypeId);
	
	@Select("SELECT id,resource_id,tag_id,tag_type_id FROM yyd_resources.tb_crosstalk_resource_tag WHERE resource_id = #{resourceId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "resourceId", column = "resource_id"),
		@Result(property = "tagId", column = "tag_id"),
		@Result(property = "tagTypeId", column = "tag_type_id"),
	})
	public List<CrosstalkTag> getByResourceId(int resourceId);
}
