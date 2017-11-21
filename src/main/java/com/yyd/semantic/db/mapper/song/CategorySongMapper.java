package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategorySongMapper {
	@Select("SELECT resource_id FROM music.music_category_resource WHERE category_id=#{categoryId} ORDER BY RAND() LIMIT 10")
	public List<Integer> getSongIdsByCategoryId(Integer categoryId);
}
