package com.yyd.semantic;

import java.util.List;

import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.SegSceneParser;
import com.yyd.semantic.nlp.WordTerm;

public class Test {

	public static void main(String[] args) {
		String service = SegSceneParser.parse("我想听刘德华的歌");
		System.out.println(service);
		List<WordTerm> result = NLPFactory.segment("我想听张学友的饿狼传说");
		System.out.println(result);
	}

}
