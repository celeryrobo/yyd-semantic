package com.yyd.semantic.db.service.impl.song;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.song.Song;
import com.yyd.semantic.db.mapper.song.CategorySongMapper;
import com.yyd.semantic.db.mapper.song.SongMapper;
import com.yyd.semantic.db.service.song.SongService;

@Service
public class SongServiceImpl implements SongService {
	@Autowired
	private SongMapper songMapper;
	@Autowired
	private CategorySongMapper categorySongMapper;

	@Override
	public List<Integer> getIdList() {
		return songMapper.getIdList();
	}

	@Override
	public Song getById(Integer id) {
		return songMapper.getById(id);
	}

	@Override
	public List<Song> getByName(String name) {
		return songMapper.getByName(name);
	}

	@Override
	public List<Song> getByArtistId(Integer artistId) {
		return songMapper.getByArtistId(artistId);
	}

	@Override
	public List<String> getAllNames() {
		return songMapper.getAllName();
	}

	@Override
	public List<Song> findByCategoryId(Integer categoryId) {
		List<Integer> songIds = categorySongMapper.getSongIdsByCategoryId(categoryId);
		List<Song> songs = new ArrayList<>();
		for (Integer songId : songIds) {
			Song song = getById(songId);
			if (song != null) {
				songs.add(song);
			}
		}
		return songs;
	}

}
