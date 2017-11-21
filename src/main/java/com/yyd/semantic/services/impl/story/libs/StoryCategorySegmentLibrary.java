package com.yyd.semantic.services.impl.story.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.story.StoryCategoryService;

@Component
public class StoryCategorySegmentLibrary implements SegmentLibrary {
	@Autowired
	private StoryCategoryService scs;
	@Override
	public List<Value> load() {
		List<String> names = scs.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "StoryCategory", "1"));
		}
		return values;
	}

}
