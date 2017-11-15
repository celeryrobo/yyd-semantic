package com.yyd.semantic.db.service.impl.poetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.poetry.Author;
import com.yyd.semantic.db.mapper.poetry.AuthorMapper;
import com.yyd.semantic.db.service.poetry.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService{
	@Autowired
	private AuthorMapper authorMapper;
	
	@Override
	public Author getAuthorById(Integer id) {
		return authorMapper.getAuthorById(id);
	}
	
	@Override
	public List<Author> getByName(String name){
		return authorMapper.findByName(name);
	}
}
