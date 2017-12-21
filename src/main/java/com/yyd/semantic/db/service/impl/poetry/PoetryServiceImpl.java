package com.yyd.semantic.db.service.impl.poetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.poetry.Poetry;
import com.yyd.semantic.db.mapper.poetry.PoetryMapper;
import com.yyd.semantic.db.service.poetry.PoetryService;

@Service
public class PoetryServiceImpl implements PoetryService {
	@Autowired
	private PoetryMapper poetryMapper;

	@Override
	public Poetry getById(int id) {
		return poetryMapper.getById(id);
	}

	@Override
	public List<Poetry> getByTitle(String title) {
		return poetryMapper.getByTitle(title);
	}

	@Override
	public List<Poetry> getByAuthor(String author) {
		return poetryMapper.getByAuthor(author);
	}

	@Override
	public List<Poetry> getByAuthorId(int authorId) {
		return poetryMapper.getByAuthorId(authorId);
	}

	@Override
	public List<Integer> getIdList() {
		return poetryMapper.getIdList();
	}

	@Override
	public List<String> getAllTitles() {
		return poetryMapper.getAllTitles();
	}
}
