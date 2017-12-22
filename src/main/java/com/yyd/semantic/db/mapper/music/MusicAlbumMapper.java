package com.yyd.semantic.db.mapper.music;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.music.MusicAlbum;


@Mapper
public interface MusicAlbumMapper {
	@Select("SELECT id, name FROM yyd_resources.tb_music_album WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
	})
	public MusicAlbum getById(Integer id);
	
	@Select("SELECT id FROM yyd_resources.tb_music_album WHERE name = #{name}")
	public List<Integer> getIdsByName(String name);
	
	@Select("SELECT name FROM yyd_resources.tb_music_album")
	public List<String> getAllNames();
}
