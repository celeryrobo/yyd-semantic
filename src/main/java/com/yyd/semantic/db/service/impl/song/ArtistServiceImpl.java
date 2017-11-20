package com.yyd.semantic.db.service.impl.song;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.song.Artist;
import com.yyd.semantic.db.mapper.song.ArtistMapper;
import com.yyd.semantic.db.service.song.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService {
	@Autowired
	private ArtistMapper artistMapper;

	@Override
	public Artist getById(Integer id) {
		return artistMapper.getById(id);
	}

	@Override
	public List<Integer> getIdsByName(String name) {
		return artistMapper.getIdsByName(name);
	}
	
	@Override
	public List<String> getAllNames() {
		return artistMapper.getAllNames();
	}

}
