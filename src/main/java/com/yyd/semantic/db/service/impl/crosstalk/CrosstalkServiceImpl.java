package com.yyd.semantic.db.service.impl.crosstalk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.crosstalk.Crosstalk;
import com.yyd.semantic.db.bean.crosstalk.CrosstalkTagType;
import com.yyd.semantic.db.mapper.crosstalk.ActorMapper;
import com.yyd.semantic.db.mapper.crosstalk.CrosstalkMapper;
import com.yyd.semantic.db.mapper.crosstalk.CrosstalkTagMapper;
import com.yyd.semantic.db.service.crosstalk.CrosstalkService;

@Service
public class CrosstalkServiceImpl implements CrosstalkService{
	@Autowired
	private CrosstalkMapper crosstalkMapper;
	@Autowired
	private ActorMapper actorMapper;
	@Autowired
	private CrosstalkTagMapper crosstalkTagMapper;
	
	
	@Override
	public List<Integer> getIdList(){
		return crosstalkMapper.getIdList();
	}
	
	@Override
	public List<String> getAllNames(){
		return crosstalkMapper.getAllName();
	}

	@Override
	public Crosstalk getById(Integer id) {
		return crosstalkMapper.getById(id);
	}

	@Override
	public List<Crosstalk> getByName(String name){
		return crosstalkMapper.getByName(name);
	}

	@Override
	public List<Crosstalk> findByActorId(Integer actorId){
		List<Integer> crosstalkIds = crosstalkTagMapper.getResourceIdsByTag(actorId, CrosstalkTagType.TAG_CROSSTALK_ACTOR);
		List<Crosstalk> crosstalks = new ArrayList<>();
		for (Integer crosstalkId : crosstalkIds) {
			Crosstalk crosstalk = getById(crosstalkId);
			if(crosstalk!=null) {
				crosstalks.add(crosstalk);
			}
		}
		return crosstalks;
	}
	
	@Override
	public List<Crosstalk> findByActorName(String actorName){
		List<Crosstalk> crosstalks = new ArrayList<>();
		List<Integer> actors = actorMapper.getIdsByName(actorName);
		if(actors.size() <= 0) {
			return crosstalks;
		}
		int categoryId = actors.get(0);
		
		crosstalks = findByActorId(categoryId);		
		
		return crosstalks;
	}
}
