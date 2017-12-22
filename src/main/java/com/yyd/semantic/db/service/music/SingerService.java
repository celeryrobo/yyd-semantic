package com.yyd.semantic.db.service.music;

import java.util.List;

import com.yyd.semantic.db.bean.music.Singer;

public interface SingerService {
	public Singer getById(Integer id);

	public List<Integer> getIdsByName(String name);

	public List<String> getAllNames();
}
