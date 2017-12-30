package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.AreaCodeService;

@Component
public class RegionAreaCodeSegmentLibrary implements SegmentLibrary{
	@Autowired
	private AreaCodeService areaCodeService;

	@Override
	public List<Value> load() {
		List<String> names = areaCodeService.getAllCode();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "areaCode", "1"));
		}
		return values;
	}
}
