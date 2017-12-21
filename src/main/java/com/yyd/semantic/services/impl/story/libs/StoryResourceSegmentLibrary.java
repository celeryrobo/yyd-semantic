package com.yyd.semantic.services.impl.story.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.story.StoryResourceService;

@Component
public class StoryResourceSegmentLibrary implements SegmentLibrary {
	@Autowired
	private StoryResourceService srs;

	@Override
	public List<Value> load() {
		List<String> names = srs.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "StoryName", "1"));
		}
		return values;
	}
}
