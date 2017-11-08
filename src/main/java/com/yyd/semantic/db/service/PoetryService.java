package com.yyd.semantic.db.service;

import java.util.List;

import com.yyd.semantic.db.bean.Poetry;

public interface PoetryService {
	public List<Integer> getIdList();
	
	public Poetry getById(int id);
	
	public List<Poetry> getByTitle(String title);
	
	public List<Poetry> getByAuthor(String author);
	
	public List<Poetry> getByAuthorId(int authorId);
	
}
