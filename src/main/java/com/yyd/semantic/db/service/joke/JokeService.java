package com.yyd.semantic.db.service.joke;

import java.util.List;

import com.yyd.semantic.db.bean.joke.Joke;


public interface JokeService {
	public List<Integer> getIdList();
	
	public List<String> getAllNames();

	public Joke getById(Integer id);

	public List<Joke> getByName(String name);

	public List<Joke> findByCategoryId(Integer categoryId);
	
	public List<Joke> findByCategoryName(String categoryName);
}
