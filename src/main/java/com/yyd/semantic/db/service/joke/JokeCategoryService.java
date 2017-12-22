package com.yyd.semantic.db.service.joke;

import java.util.List;

import com.yyd.semantic.db.bean.joke.JokeCategory;

public interface JokeCategoryService {
	public List<String> getAllNames();

	public List<JokeCategory> getByName(String name);
}
