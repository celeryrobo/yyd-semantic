package com.yyd.semantic;

import org.nlpcn.commons.lang.tire.domain.Value;

public class Test {

	public static void main(String[] args) throws Exception {
		Value v = new Value("中国", "test", "1");
		System.out.println(v.getParamers()[0]);
	}
}
