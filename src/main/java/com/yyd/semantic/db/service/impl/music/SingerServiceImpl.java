package com.yyd.semantic.db.service.impl.music;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.music.Singer;
import com.yyd.semantic.db.mapper.music.SingerMapper;
import com.yyd.semantic.db.service.music.SingerService;

@Service
public class SingerServiceImpl implements SingerService {
	@Autowired
	private SingerMapper singerMapper;

	@Override
	public Singer getById(Integer id) {
		return singerMapper.getById(id);
	}

	@Override
	public List<Integer> getIdsByName(String name) {
		return singerMapper.getIdsByName(name);
	}
	
	@Override
	public List<String> getAllNames() {
		return singerMapper.getAllNames();
	}

}
