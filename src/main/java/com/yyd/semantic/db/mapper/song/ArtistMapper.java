package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.song.Artist;

@Mapper
public interface ArtistMapper {
	@Select("SELECT id, name FROM music.artist WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
	})
	public Artist getById(Integer id);
	
	@Select("SELECT id FROM music.artist WHERE name = #{name}")
	public List<Integer> getIdsByName(String name);
	
	@Select("SELECT name FROM music.artist")
	public List<String> getAllNames();
}
