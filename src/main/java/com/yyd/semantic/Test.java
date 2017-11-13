package com.yyd.semantic;

import com.yyd.semantic.nlp.SegSceneParser;

public class Test {

	public static void main(String[] args) {
		String service = SegSceneParser.parse("我想听李白的静夜思");
		System.out.println(service);
	}

}
