package com.yyd.semantic.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;

/**
 * nlp处理工具类,无状态，可以多线程使用
 * 
 * @author pc
 *
 */
public class NLPFactory {
	public static Map<Object, Forest> forests = new HashMap<>();

	/**
	 * 分词
	 * 
	 * @param text
	 * @return
	 */
	public static List<WordTerm> segment(String text, String... forestNames) {
		ArrayList<Forest> forestList = new ArrayList<>();
		if (forestNames.length > 0) {
			for (int idx = 0; idx < forestNames.length; idx++) {
				String key = forestNames[idx];
				if (forests.containsKey(key)) {
					forestList.add(forests.get(key));
				}
			}
		} else {
			forestList.addAll(forests.values());
		}
		Forest[] forestArr = new Forest[forestList.size()];
		forestList.toArray(forestArr);
		Result hanlpTermResult = DicAnalysis.parse(text, forestArr);
		List<WordTerm> result = Convert(hanlpTermResult);
		return result;
	}

	private static List<WordTerm> Convert(Result termResult) {
		List<WordTerm> results = new ArrayList<WordTerm>();
		int id = 0;

		for (Term term : termResult) {
			id += 1;
			WordTerm word = new WordTerm(id, term.getName(), term.getNatureStr());
			results.add(word);
		}

		return results;
	}

}
