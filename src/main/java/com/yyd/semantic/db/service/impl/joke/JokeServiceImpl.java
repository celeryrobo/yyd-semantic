package com.yyd.semantic.db.service.impl.joke;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.joke.Joke;
import com.yyd.semantic.db.bean.joke.JokeCategory;
import com.yyd.semantic.db.bean.joke.JokeTagType;
import com.yyd.semantic.db.mapper.joke.JokeCategoryMapper;
import com.yyd.semantic.db.mapper.joke.JokeMapper;
import com.yyd.semantic.db.mapper.joke.JokeTagMapper;
import com.yyd.semantic.db.service.joke.JokeService;


@Service
public class JokeServiceImpl implements JokeService{
	@Autowired
	private JokeMapper jokeMapper;
	@Autowired
	private JokeCategoryMapper categoryMapper;
	@Autowired
	private JokeTagMapper jokeTagMapper;
	
	@Override
	public List<Integer> getIdList(){
		return jokeMapper.getIdList();
	}
	
	@Override
	public List<String> getAllNames(){
		return jokeMapper.getAllName();
	}

	public Joke getById(Integer id) {
		return jokeMapper.getById(id);
	}

	@Override
	public List<Joke> getByName(String name){
		return jokeMapper.getByName(name);
	}
		
	@Override
	public List<Joke> findByCategoryId(Integer categoryId){
		List<Integer> jokeIds = jokeTagMapper.getResourceIdsByTag(categoryId, JokeTagType.TAG_JOKE_CATEGORY);
		List<Joke> jokes = new ArrayList<>();
		for (Integer jokeId : jokeIds) {
			Joke joke = getById(jokeId);
			if(joke!=null) {
				jokes.add(joke);
			}
		}
		return jokes;
	}
	
	@Override
	public List<Joke> findByCategoryName(String categoryName){
		List<Joke> jokes = new ArrayList<>();
		List<JokeCategory> categorys = categoryMapper.findByName(categoryName);
		if(categorys.size() <= 0) {
			return jokes;
		}
		int categoryId = categorys.get(0).getId();
		
		jokes = findByCategoryId(categoryId);		
		
		return jokes;
	}
}
