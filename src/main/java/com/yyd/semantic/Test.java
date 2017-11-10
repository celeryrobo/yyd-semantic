package com.yyd.semantic;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class Test {

	public static void main(String[] args) {
		List<Term> terms = HanLP.segment("姑苏城外寒山寺");
		System.out.println(terms);
	}

}
