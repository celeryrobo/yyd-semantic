package com.yyd.semantic.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.Poetry;
import com.yyd.semantic.db.mapper.PoetryMapper;
import com.yyd.semantic.db.service.PoetryService;

@Service
public class PoetryServiceImpl implements PoetryService{
	@Autowired
	private PoetryMapper poetryMapper;
	
	@Override
	public List<Integer> getIdList(){
		return poetryMapper.getIdList();
	}
	
	@Override
	public Poetry getById(int id) {
		return poetryMapper.getById(id);
	}
	
	@Override
	public List<Poetry> getByTitle(String title){
		return poetryMapper.getByTitle(title);
	}
	
	@Override
	public List<Poetry> getByAuthor(String author){
		return poetryMapper.getByAuthor(author);
	}
	
	@Override
	public List<Poetry> getByAuthorId(int authorId){
		return poetryMapper.getByAuthorId(authorId);
	}
}
