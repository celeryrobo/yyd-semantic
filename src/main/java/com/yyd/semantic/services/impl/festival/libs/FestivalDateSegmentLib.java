package com.yyd.semantic.services.impl.festival.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.festival.FestivalService;

@Component
public class FestivalDateSegmentLib implements SegmentLibrary {

	@Autowired
	private FestivalService fs;
	@Override
	public List<Value> load() {
		List<String> names = fs.getAllDates();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "FestivalDate", "1"));
		}
		return values;
	}
}
