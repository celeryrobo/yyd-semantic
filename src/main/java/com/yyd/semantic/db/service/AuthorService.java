package com.yyd.semantic.db.service;

import java.util.List;

import com.yyd.semantic.db.bean.Author;

public interface AuthorService {

	public List<Author> getAuthorList();
	
	public Author getAuthorById(Integer id);
	
	public List<Author> getByName(String name);
	
	public List<Author> getByDynasty(String dynasty);
}
