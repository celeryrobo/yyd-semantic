package com.yyd.semantic.services.impl.region.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.region.PostCodeService;


@Component
public class RegionPostCodeSegmentLibrary implements SegmentLibrary{
	@Autowired
	private PostCodeService postCodeService;

	@Override
	public List<Value> load() {
		List<String> names = postCodeService.getAllCode();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "postCode", "1"));
		}
		return values;
	}
}
