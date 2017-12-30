package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.ProvinceService;

@Component
public class RegionProvinceFullSegmentLibrary implements SegmentLibrary{
	@Autowired
	private ProvinceService provinceService;

	@Override
	public List<Value> load() {
		List<String> names = provinceService.getAllFullName();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "provFull", "1"));
		}
		return values;
	}
}
