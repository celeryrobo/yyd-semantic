package com.yyd.semantic.services.impl.idiom.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.idiom.IdiomService;

@Component
public class IdiomSegmentLibrary implements SegmentLibrary {
	@Autowired
	private IdiomService idiomService;

	@Override
	public List<Value> load() {
		List<String> contents = idiomService.findAll();
		List<Value> values = new LinkedList<>();
		for (String content : contents) {
			values.add(new Value(content, "idiomContent", "1"));
		}
		return values;
	}

}
