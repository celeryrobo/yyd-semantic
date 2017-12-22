package com.yyd.semantic.services.impl.music.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.music.SingerService;

@Component
public class MusicSingerSegmentLibrary implements SegmentLibrary {
	@Autowired
	private SingerService singerService;

	@Override
	public List<Value> load() {
		List<String> names = singerService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "singer", "1"));
		}
		return values;
	}

}
