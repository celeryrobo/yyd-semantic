package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryRelationshipMapper {
	@Select("SELECT sub_id FROM music.music_category_relationship WHERE parent_id=#{parentId}")
	public List<Integer> getSubIdsByParentId(Integer parentId);
}
