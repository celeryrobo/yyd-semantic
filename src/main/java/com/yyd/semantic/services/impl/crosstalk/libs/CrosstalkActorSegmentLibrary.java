package com.yyd.semantic.services.impl.crosstalk.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.crosstalk.ActorService;

@Component
public class CrosstalkActorSegmentLibrary implements SegmentLibrary{
	@Autowired
	private ActorService actorService;

	@Override
	public List<Value> load() {
		List<String> names = actorService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "crosstalkActor", "1"));
		}
		return values;
	}
}
