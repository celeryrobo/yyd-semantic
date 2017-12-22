package com.yyd.semantic.db.service.music;

import java.util.List;

import com.yyd.semantic.db.bean.music.Song;

public interface SongService {
	public List<Integer> getIdList();
	
	public List<String> getAllNames();

	public Song getById(Integer id);

	public List<Song> getByName(String name);

	public List<Song> getBySingerId(Integer artistId);
	
	public List<Song> findByCategoryId(Integer categoryId);
	
	public List<Song> findByCategoryName(String categoryName);
}
