package com.yyd.semantic.services.impl.poetry;

import java.util.Map;

public class PoetrySlot {
	public static final String POEM_SENTENCE = "sentence";
	public static final String POEM_TITLE = "title";
	public static final String POEM_AUTHOR = "author";
	public static final String POEM_NUMBER = "number";

	private static final String POEM_ID = "poemId"; // 诗id
	private static final String POEM_CUR_SENTENCE_INDEX = "poemSentenceIndex";

	private Map<Object, Object> params;

	public PoetrySlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getPoemCurSentenceIndex() {
		String sentenceIndex = (String) params.get(POEM_CUR_SENTENCE_INDEX);
		if (sentenceIndex == null) {
			return null;
		}
		return Integer.valueOf(sentenceIndex);
	}

	public void setPoemCurSentenceIndex(Integer poemCurSentenceIndex) {
		params.put(POEM_CUR_SENTENCE_INDEX, poemCurSentenceIndex.toString());
	}

	public Integer getPoemId() {
		String poemId = (String) params.get(POEM_ID);
		if (poemId == null) {
			return null;
		}
		return Integer.valueOf(poemId);
	}

	public void setPoemId(Integer poemId) {
		params.put(POEM_ID, poemId.toString());
	}

	public void clear() {
		if (!params.isEmpty()) {
			params.clear();
		}
	}
}
