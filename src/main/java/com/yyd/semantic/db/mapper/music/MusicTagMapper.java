package com.yyd.semantic.db.mapper.music;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MusicTagMapper {
	@Select("SELECT resource_id FROM yyd_resources.tb_music_resource_tag WHERE tag_id = #{tagId}  AND tag_type_id = #{tagTypeId} ORDER BY RAND() limit 10")
	public List<Integer> getResourceIdsByTag(@Param("tagId") Integer tagId,@Param("tagTypeId") Integer tagTypeId);
}
