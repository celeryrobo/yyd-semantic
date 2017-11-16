package com.yyd.semantic;

import java.util.List;

import com.yyd.semantic.nlp.NLPFactory;
import com.yyd.semantic.nlp.SegSceneParser;
import com.yyd.semantic.nlp.WordTerm;

public class Test {

	public static void main(String[] args) {
		String service = SegSceneParser.parse("说一个万圣节历险故事");
		System.out.println(service);
		List<WordTerm> result = NLPFactory.segment("说一个万圣节历险故事");
		System.out.println(result);
		OUT:
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(i + ":" + j);
				if(i == 1) {
					break OUT;
				}
			}
		}
		System.out.println("hello");
	}

}
