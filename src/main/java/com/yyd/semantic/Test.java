package com.yyd.semantic;

public class Test {

	public static void main(String[] args) throws Exception {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		System.out.println(loader);
		System.out.println(loader.getParent());
		System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));
	}
}
