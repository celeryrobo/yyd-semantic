package com.yyd.semantic.db.service.crosstalk;

import java.util.List;

import com.yyd.semantic.db.bean.crosstalk.Actor;


public interface ActorService {
	public Actor getById(Integer id);

	public List<Integer> getIdsByName(String name);

	public List<String> getAllNames();
}
