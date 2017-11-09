package com.yyd.semantic;



import com.yyd.semantic.nlp.SegSceneParser;


public class Test {

	public static void main(String[] args) {
		String service = SegSceneParser.parse("床前明月光的下一句");
		System.out.println(service);
	}

}
