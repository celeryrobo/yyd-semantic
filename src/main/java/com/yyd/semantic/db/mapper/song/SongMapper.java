package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.song.Song;

@Mapper
public interface SongMapper {
	@Select("SELECT id FROM music.song ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT id, name, artistId, sourceUrl FROM music.song WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artistId"),
		@Result(property = "sourceUrl", column = "sourceUrl")
	})
	public Song getById(Integer id);
	
	@Select("SELECT id, name, artistId, sourceUrl FROM music.song WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artistId"),
		@Result(property = "sourceUrl", column = "sourceUrl")
	})
	public List<Song> getByName(String name);
	
	@Select("SELECT id, name, artistId, sourceUrl FROM music.song WHERE artistId = #{artistId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artistId"),
		@Result(property = "sourceUrl", column = "sourceUrl")
	})
	public List<Song> getByArtistId(Integer artistId);
}
