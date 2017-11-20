package com.yyd.semantic.db.service.song;

import java.util.List;

import com.yyd.semantic.db.bean.song.Song;

public interface SongService {
	public List<Integer> getIdList();
	
	public List<String> getAllNames();

	public Song getById(Integer id);

	public List<Song> getByName(String name);

	public List<Song> getByArtistId(Integer artistId);
}
