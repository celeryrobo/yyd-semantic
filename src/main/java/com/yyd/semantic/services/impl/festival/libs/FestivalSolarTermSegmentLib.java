package com.yyd.semantic.services.impl.festival.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.services.impl.festival.IntentData;

@Component
public class FestivalSolarTermSegmentLib implements SegmentLibrary {

	@Override
	public List<Value> load() {
		List<Value> values = new LinkedList<>();
		for (String name : IntentData.SOLAR_TERM) {
			values.add(new Value(name, "SolarTerm", "1"));
		}
		return values;
	}

}
