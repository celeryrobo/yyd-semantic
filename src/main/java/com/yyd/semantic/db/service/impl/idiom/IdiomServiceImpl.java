package com.yyd.semantic.db.service.impl.idiom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.idiom.Idiom;
import com.yyd.semantic.db.mapper.idiom.IdiomMapper;
import com.yyd.semantic.db.service.idiom.IdiomService;

@Service
public class IdiomServiceImpl implements IdiomService {
	@Autowired
	private IdiomMapper idiomMapper;

	@Override
	public Idiom getById(Integer id) {
		return idiomMapper.getById(id);
	}

	@Override
	public Idiom getByContent(String content) {
		return idiomMapper.getByContent(content);
	}

	@Override
	public List<Idiom> findByPyFirst(String pyFirst) {
		return idiomMapper.findByPyFirst(pyFirst);
	}

	@Override
	public List<Idiom> findByPyLast(String pyLast) {
		return idiomMapper.findByPyLast(pyLast);
	}

	@Override
	public List<String> findAll() {
		return idiomMapper.findAll();
	}

	@Override
	public void update(Idiom idiom) {
		idiomMapper.updateRefcount(idiom);
	}

}
