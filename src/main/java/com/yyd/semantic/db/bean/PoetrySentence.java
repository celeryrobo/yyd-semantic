package com.yyd.semantic.db.bean;

public class PoetrySentence {
	private Integer id;
	private String sentence;
	private Integer poetryId;
	
	public PoetrySentence() {
		// TODO Auto-generated constructor stub
	}

	public PoetrySentence(Integer id, String sentence, Integer poetryId) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.poetryId = poetryId;
	}

	@Override
	public String toString() {
		return "PotrySentence [id=" + id + ", sentence=" + sentence + ", poetryId=" + poetryId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Integer getPoetryId() {
		return poetryId;
	}

	public void setPoetryId(Integer poetryId) {
		this.poetryId = poetryId;
	}
}
