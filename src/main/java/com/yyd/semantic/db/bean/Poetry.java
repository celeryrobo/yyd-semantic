package com.yyd.semantic.db.bean;

public class Poetry {
	private Integer id;
	private String title;
	private String content;
	private String sourceUrl;
	private String authorName;
	private Integer authorId;
	
	public Poetry() {
		// TODO Auto-generated constructor stub
	}

	public Poetry(Integer id, String title, String content, String sourceUrl, String authorName,Integer authorId) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.sourceUrl = sourceUrl;
		this.authorName = authorName;
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Poetry [id=" + id + ", title=" + title + ", content=" + content + ", sourceUrl=" + sourceUrl
				+ ",authorName="+authorName+", authorId=" + authorId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
}
