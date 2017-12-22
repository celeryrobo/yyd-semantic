package com.yyd.semantic.services.impl.joke.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.joke.JokeCategoryService;

@Component
public class JokeCategorySegmentLibrary implements SegmentLibrary{
	@Autowired
	private JokeCategoryService categoryService;

	@Override
	public List<Value> load() {
		List<String> names = categoryService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "category", "1"));
		}
		return values;
	}
}
