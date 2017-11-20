package com.yyd.semantic.services.impl.poetry.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;

@Component
public class PoetrySentenceSegmentLibrary implements SegmentLibrary {
	@Autowired
	private PoetrySentenceService poetrySentenceService;

	@Override
	public List<Value> load() {
		List<String> sentences = poetrySentenceService.getAllSentences();
		List<Value> values = new LinkedList<>();
		for (String sentence : sentences) {
			values.add(new Value(sentence, "sentence", "1"));
		}
		return values;
	}

}
