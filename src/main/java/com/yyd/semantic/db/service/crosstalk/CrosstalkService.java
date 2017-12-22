package com.yyd.semantic.db.service.crosstalk;

import java.util.List;

import com.yyd.semantic.db.bean.crosstalk.Crosstalk;

public interface CrosstalkService {
	public List<Integer> getIdList();
	
	public List<String> getAllNames();

	public Crosstalk getById(Integer id);

	public List<Crosstalk> getByName(String name);

	public List<Crosstalk> findByActorId(Integer actorId);	
	
	public List<Crosstalk> findByActorName(String actorName);
}
