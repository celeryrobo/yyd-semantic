package com.yyd.semantic.services.impl.opera.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.opera.OperaService;

@Component
public class OperaNameSegmentLibrary implements SegmentLibrary {
	@Autowired
	private OperaService operaService;

	@Override
	public List<Value> load() {
		List<String> names = operaService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "name", "1"));
		}
		return values;
	}
}
