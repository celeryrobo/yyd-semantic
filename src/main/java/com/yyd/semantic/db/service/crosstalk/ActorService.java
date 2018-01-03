package com.yyd.semantic.db.service.crosstalk;

import java.util.List;

import com.yyd.semantic.db.bean.crosstalk.CrosstalkActor;


public interface ActorService {
	public CrosstalkActor getById(Integer id);

	public List<CrosstalkActor> getByName(String name);
	
	public List<Integer> getIdsByName(String name);

	public List<String> getAllNames();
}
