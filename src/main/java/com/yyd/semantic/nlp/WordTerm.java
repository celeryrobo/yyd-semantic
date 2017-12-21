package com.yyd.semantic.nlp;

public class WordTerm {
	/**
	 * 词id，词在句子中的位置，按从左到右的顺序，从1开始
	 */
	private int id = -1; // id为0表示为虚拟的根节点,-1表示无id
	/**
	 * 句子中实际的词
	 */
	private String realWord = null;
	/**
	 * 词性
	 */
	private String nature = null;

	public WordTerm() {

	}

	public WordTerm(int id, String realWord, String nature) {
		this.id = id;
		this.realWord = realWord;
		this.nature = nature;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealWord() {
		return realWord;
	}

	public void setRealWord(String realWord) {
		this.realWord = realWord;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Override
	public String toString() {
		if (this.nature != null)
			return this.realWord + "/" + nature + "/ " + this.id;
		return this.realWord;
	}
}
