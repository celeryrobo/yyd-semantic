package com.yyd.semantic.db.service.impl.crosstalk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.crosstalk.CrosstalkActor;
import com.yyd.semantic.db.mapper.crosstalk.ActorMapper;
import com.yyd.semantic.db.service.crosstalk.ActorService;

@Service
public class ActorServiceImpl implements ActorService{
	@Autowired
	private ActorMapper actorMapper;
	
	@Override
	public CrosstalkActor getById(Integer id) {
		return actorMapper.getById(id);
	}
	
	@Override
	public List<CrosstalkActor> getByName(String name){
		return actorMapper.getByName(name);
	}

	@Override
	public List<Integer> getIdsByName(String name){
		return actorMapper.getIdsByName(name);
	}

	@Override
	public List<String> getAllNames(){
		return actorMapper.getAllNames();
	}
}
