package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.DistrictService;

@Component
public class RegionDistrictFullSegmentLibrary implements SegmentLibrary{
	@Autowired
	private DistrictService districtService;

	@Override
	public List<Value> load() {
		List<String> names = districtService.getAllFullName();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "districtFull", "1"));
		}
		return values;
	}
}
