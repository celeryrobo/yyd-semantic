package com.yyd.semantic.db.bean.poetry;

public class Author {
	private Integer id;
	private String name;
	private String description;
	private String sourceUrl;
	private String chaodai;

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", text=" + description + ", sourceUrl=" + sourceUrl
				+ ", chaodai=" + chaodai + "]";
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getChaodai() {
		return chaodai;
	}

	public void setChaodai(String chaodai) {
		this.chaodai = chaodai;
	}
}
