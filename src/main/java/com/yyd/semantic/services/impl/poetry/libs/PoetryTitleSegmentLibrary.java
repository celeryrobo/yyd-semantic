package com.yyd.semantic.services.impl.poetry.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.poetry.PoetryService;

@Component
public class PoetryTitleSegmentLibrary implements SegmentLibrary {
	@Autowired
	private PoetryService poetryService;

	@Override
	public List<Value> load() {
		List<String> titles = poetryService.getAllTitles();
		List<Value> values = new LinkedList<>();
		for (String title : titles) {
			values.add(new Value(title, "title", "1"));
		}
		return values;
	}

}
