package com.yyd.semantic.db.service.poetry;

import java.util.List;

import com.yyd.semantic.db.bean.poetry.Author;

public interface AuthorService {
	public Author getAuthorById(Integer id);

	public List<Author> getByName(String name);
	
	public List<String> getAllNames();
}
