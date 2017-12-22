package com.yyd.semantic.db.bean.idiom;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Idiom {
	private Integer id;
	private String content;
	private String pyFirst;
	private String pyLast;
	@JsonIgnore
	private Integer refcount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPyFirst() {
		return pyFirst;
	}

	public void setPyFirst(String pyFirst) {
		this.pyFirst = pyFirst;
	}

	public String getPyLast() {
		return pyLast;
	}

	public void setPyLast(String pyLast) {
		this.pyLast = pyLast;
	}

	public Integer getRefcount() {
		return refcount;
	}

	public void setRefcount(Integer refcount) {
		this.refcount = refcount;
	}

}
