package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.song.Category;

@Mapper
public interface CategoryMapper {
	@Select("SELECT name FROM music.music_category")
	public List<String> getAllNames();
	
	@Select("SELECT id, name, src_id FROM music.music_category WHERE id=#{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "srcId", column = "src_id")
	})
	public Category getById(Integer id);

	@Select("SELECT id, name, src_id FROM music.music_category WHERE name=#{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "srcId", column = "src_id")
	})
	public List<Category> findByName(String name);
}
