package com.yyd.semantic.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

/**
 * nlp处理工具类,无状态，可以多线程使用
 * @author pc
 *
 */
public class NLPFactory {
	 /**
	  * 分词
	  * @param text
	  * @return
	  */
	 public static List<WordTerm> segment(String text){
		 List<Term> hanlpTermResult = HanLP.segment(text);
		 List<WordTerm> result = Convert(hanlpTermResult);
		 return result;
	 }
	 
	 private static List<WordTerm>Convert(List<Term> termList){
		List<WordTerm> results = new ArrayList<WordTerm>();
		int id = 0;
		
		for(Term term:termList){
			id += 1;
			WordTerm word = new WordTerm(id,term.word,term.nature.name());				
			results.add(word);
		}
		
		return results;
	}
	 
}
