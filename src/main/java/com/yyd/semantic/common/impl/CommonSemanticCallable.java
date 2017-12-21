package com.yyd.semantic.common.impl;

import java.util.Set;
import java.util.TreeSet;

import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.DbSegLoader;
import com.yyd.semantic.common.DbSegLoader.Item;
import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.WordTerm;

@Component("CommonSemanticCallable")
public class CommonSemanticCallable implements SemanticCallable {
	@Autowired
	private DbSegLoader dbSegLoader;

	@Override
	public String call(String text, Object callName, Object... args) {
		Set<String> strs = new TreeSet<>();
		for (WordTerm wordTerm : NLPFactory.segment(text, callName + "_" + args[0])) {
			if (wordTerm.getNature().equals(args[0])) {
				String realWord = wordTerm.getRealWord();
				strs.add(realWord);
				Item item = dbSegLoader.getItem(realWord);
				if (item != null) {
					strs.addAll(item.getItems());
				}
			}
		}
		return strs.isEmpty() ? "null" : StringUtil.joiner(strs, "|");
	}

}
