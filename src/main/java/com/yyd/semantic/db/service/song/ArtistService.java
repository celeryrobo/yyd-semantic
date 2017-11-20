package com.yyd.semantic.db.service.song;

import java.util.List;

import com.yyd.semantic.db.bean.song.Artist;

public interface ArtistService {
	public Artist getById(Integer id);

	public List<Integer> getIdsByName(String name);

	List<String> getAllNames();
}
