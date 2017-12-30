package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.CarNumberService;

@Component
public class RegionCarNumberSegmentLibrary implements SegmentLibrary{
	@Autowired
	private CarNumberService carNumberService;

	@Override
	public List<Value> load() {
		List<String> names = carNumberService.getAllNumber();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "carNumber", "1"));					
		}
		return values;
	}
}
