package com.yyd.semantic.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component("CommonSemanticCallable")
public class CommonSemanticCallable implements SemanticCallable {

	@Override
	public String call(String text, Object callName, Object... args) {
		List<String> list = new ArrayList<String>();
		for (WordTerm wordTerm : NLPFactory.segment(text, callName + "_" + args[0])) {
			if (wordTerm.getNature().equals(args[0])) {
				list.add(wordTerm.getRealWord());
			}
		}
		return list.isEmpty() ? "null" : StringUtil.joiner(list, "|");
	}

}
