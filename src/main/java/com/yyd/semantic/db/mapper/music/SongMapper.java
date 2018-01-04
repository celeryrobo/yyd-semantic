package com.yyd.semantic.db.mapper.music;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.music.Song;

@Mapper
public interface SongMapper {
	@Select("SELECT id FROM yyd_resources.tb_music_resource ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT id FROM yyd_resources.tb_music_resource WHERE id != #{id} ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdListExcept(int id);
	
	@Select("SELECT name FROM yyd_resources.tb_music_resource")
	public List<String> getAllName();
	
	@Select("SELECT id, name, singer_id, src_url FROM yyd_resources.tb_music_resource WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "singerId", column = "singer_id"),
		@Result(property = "songUrl", column = "src_url")
	})
	public Song getById(Integer id);
	
	@Select("SELECT id, name, singer_id, src_url FROM yyd_resources.tb_music_resource WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "singerId", column = "singer_id"),
		@Result(property = "songUrl", column = "src_url")
	})
	public List<Song> getByName(String name);
	
	@Select("SELECT id, name, singer_id, src_url FROM yyd_resources.tb_music_resource WHERE singer_id = #{singerId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "singerId", column = "singer_id"),
		@Result(property = "songUrl", column = "src_url")
	})
	public List<Song> getBySingerId(Integer singerId);
}
