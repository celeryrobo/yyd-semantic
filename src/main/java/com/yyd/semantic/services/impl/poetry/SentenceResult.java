package com.yyd.semantic.services.impl.poetry;

public class SentenceResult {
	private boolean isSentence = false;
	private String Sentence  = null;
	
	public SentenceResult(String sentence,boolean isSentence) {
		this.Sentence = sentence;
		this.isSentence = isSentence;
	}
	
	public String get() {
		return this.Sentence;
	}
	
	public boolean isSentence() {
		return this.isSentence;
	}

}
