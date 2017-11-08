package com.yyd.semantic.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.Author;
import com.yyd.semantic.db.mapper.AuthorMapper;
import com.yyd.semantic.db.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService{
	@Autowired
	private AuthorMapper authorMapper;
	
	@Override
	public List<Author> getAuthorList(){
		return authorMapper.getAuthorList();
	}
	
	@Override
	public Author getAuthorById(Integer id) {
		return authorMapper.getAuthorById(id);
	}
	
	@Override
	public List<Author> getByName(String name){
		return authorMapper.findByName(name);
	}
		
	@Override
	public List<Author> getByDynasty(String dynasty){
		return authorMapper.findByDynasty(dynasty);
	}
}
