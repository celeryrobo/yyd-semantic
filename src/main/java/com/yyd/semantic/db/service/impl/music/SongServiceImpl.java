package com.yyd.semantic.db.service.impl.music;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.music.Category;
import com.yyd.semantic.db.bean.music.MusicTagType;
import com.yyd.semantic.db.bean.music.Song;
import com.yyd.semantic.db.mapper.music.CategoryMapper;
import com.yyd.semantic.db.mapper.music.MusicTagMapper;
import com.yyd.semantic.db.mapper.music.SongMapper;
import com.yyd.semantic.db.service.music.SongService;

@Service
public class SongServiceImpl implements SongService {
	@Autowired
	private SongMapper songMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private MusicTagMapper musicTagMapper;
	

	@Override
	public List<Integer> getIdList() {
		return songMapper.getIdList();
	}
	
	@Override
	public List<Integer> getIdListExcept(int id){
		return songMapper.getIdListExcept(id);
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
	public List<Song> getBySingerId(Integer singerId) {
		return songMapper.getBySingerId(singerId);
	}

	@Override
	public List<String> getAllNames() {
		return songMapper.getAllName();
	}

	@Override
	public List<Song> findByCategoryId(Integer categoryId) {
		List<Integer> songIds = musicTagMapper.getResourceIdsByTag(categoryId, MusicTagType.TAG_MUSIC_CATEGORY);
		List<Song> songs = new ArrayList<>();
		for (Integer songId : songIds) {
			Song song = getById(songId);
			if(song!=null) {
				songs.add(song);
			}
		}
		return songs;
	}
	
	@Override
	public List<Song> findByCategoryName(String categoryName){
		List<Song> songs = new ArrayList<>();
		List<Category> categorys = categoryMapper.findByName(categoryName);
		if(categorys.size() <= 0) {
			return songs;
		}
		int categoryId = categorys.get(0).getId();
		
		songs = findByCategoryId(categoryId);		
		
		return songs;
	}

}
