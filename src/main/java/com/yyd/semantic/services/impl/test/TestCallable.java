package com.yyd.semantic.services.impl.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component
public class TestCallable implements SemanticCallable {
	private Map<Object, Object> map;
	
	public TestCallable() {
		map = new HashMap<Object, Object>();
		map.put("title", "npoem");
		map.put("sentence", "npoemse");
		map.put("author", "npoet");
	}

	@Override
	public String call(String text, Object callName, Object... args) {
		List<WordTerm> list = new ArrayList<WordTerm>();
		for (WordTerm wordTerm : NLPFactory.segment(text)) {
			if(wordTerm.getNature().equals(map.get(args[0]))) {
				list.add(wordTerm);
			}
		}
		int size = list.size();
		String result = "null";
		if(size > 0) {
			WordTerm term = list.get(0);
			StringBuilder sb = new StringBuilder(term.getRealWord());
			for(int i = 1; i< size; i++) {
				sb.append("|").append(list.get(i).getRealWord());
			}
			result = sb.toString();
		}
		return result;
	}

}
