package com.yyd.semantic.services.impl.poetry;

public class Poem {
	private Integer id;
	private String name;
	private String author;
	private String dynasty;
	private String sentence;
	public Poem() {
		// TODO Auto-generated constructor stub
	}
	public Poem(String name, String author, String dynasty, String sentence) {
		super();
		this.name = name;
		this.author = author;
		this.dynasty = dynasty;
		this.sentence = sentence;
	}
	@Override
	public String toString() {
		return "Poem [name=" + name + ", author=" + author + ", dynasty=" + dynasty + ", sentence=" + sentence + "]";
	}
	
	 public Integer getId() {
	        return id;
	 }

	 public void setId(Integer id) {
	        this.id = id;
	 }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDynasty() {
		return dynasty;
	}
	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
}
