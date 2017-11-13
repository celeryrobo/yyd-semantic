package com.yyd.semantic.services.impl.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component
public class TestCallable implements SemanticCallable {

	@Override
	public String call(String text, Object callName, Object... args) {
		List<WordTerm> list = new ArrayList<WordTerm>();
		for (WordTerm wordTerm : NLPFactory.segment(text, callName + "_" + args[0])) {
			if (wordTerm.getNature().equals(args[0])) {
				list.add(wordTerm);
			}
		}
		int size = list.size();
		String result = "null";
		if (size > 0) {
			WordTerm term = list.get(0);
			StringBuilder sb = new StringBuilder(term.getRealWord());
			for (int i = 1; i < size; i++) {
				sb.append("|").append(list.get(i).getRealWord());
			}
			result = sb.toString();
		}
		return result;
	}

}
