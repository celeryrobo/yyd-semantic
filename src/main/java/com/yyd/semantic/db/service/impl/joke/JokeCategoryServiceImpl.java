package com.yyd.semantic.db.service.impl.joke;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.joke.JokeCategory;
import com.yyd.semantic.db.mapper.joke.JokeCategoryMapper;
import com.yyd.semantic.db.service.joke.JokeCategoryService;


@Service
public class JokeCategoryServiceImpl implements JokeCategoryService{
	@Autowired
	private JokeCategoryMapper jokeMapper;
	
	@Override
	public List<String> getAllNames(){
		return jokeMapper.getAllNames(); 
	}

	@Override
	public List<JokeCategory> getByName(String name){
		return jokeMapper.findByName(name);
	}
}
