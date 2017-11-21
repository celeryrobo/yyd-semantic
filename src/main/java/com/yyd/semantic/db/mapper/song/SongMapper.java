package com.yyd.semantic.db.mapper.song;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.song.Song;

@Mapper
public interface SongMapper {
	@Select("SELECT id FROM music.music_resource ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT name FROM music.music_resource")
	public List<String> getAllName();
	
	@Select("SELECT id, name, artist_id, content_url FROM music.music_resource WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artist_id"),
		@Result(property = "songUrl", column = "content_url")
	})
	public Song getById(Integer id);
	
	@Select("SELECT id, name, artist_id, content_url FROM music.music_resource WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artist_id"),
		@Result(property = "songUrl", column = "content_url")
	})
	public List<Song> getByName(String name);
	
	@Select("SELECT id, name, artist_id, content_url FROM music.music_resource WHERE artist_id = #{artistId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artist_id"),
		@Result(property = "songUrl", column = "content_url")
	})
	public List<Song> getByArtistId(Integer artistId);
	
	@Select("SELECT id, name, artist_id, content_url FROM music.music_resource WHERE id IN (#{ids})")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "artistId", column = "artist_id"),
		@Result(property = "songUrl", column = "content_url")
	})
	public List<Song> findByIds(String ids);
}
