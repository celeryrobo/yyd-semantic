package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.ProvinceService;

@Component
public class RegionProvinceShortSegmentLibrary implements SegmentLibrary{
	@Autowired
	private ProvinceService provinceService;

	@Override
	public List<Value> load() {
		List<String> names = provinceService.getAllShortName();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "provShort", "1"));
		}
		return values;
	}
}
