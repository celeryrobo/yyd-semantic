package com.yyd.semantic.services.impl.phonenumber.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.phonenumber.PhoneNumberService;


@Component
public class PhoneNumberNameSegmentLibrary implements SegmentLibrary{
	@Autowired
	private PhoneNumberService phoneNumberService;

	@Override
	public List<Value> load() {
		List<String> words = phoneNumberService.getAllName();
		List<Value> values = new LinkedList<>();
		for (String name : words) {
			values.add(new Value(name, "name", "1"));
		}
		
		return values;
	}
}
