package com.yyd.semantic;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws Exception {
		Map<String, String> m = new HashMap<>();
		Object[] o = m.entrySet().toArray();
		System.out.println(o.length);
	}
}
