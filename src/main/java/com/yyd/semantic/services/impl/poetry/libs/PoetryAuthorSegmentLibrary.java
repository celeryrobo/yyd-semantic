package com.yyd.semantic.services.impl.poetry.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.poetry.AuthorService;

@Component
public class PoetryAuthorSegmentLibrary implements SegmentLibrary {
	@Autowired
	private AuthorService authorService;

	@Override
	public List<Value> load() {
		List<String> names = authorService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "author", "1"));
		}
		return values;
	}

}
