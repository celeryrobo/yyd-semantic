package com.yyd.semantic.db.bean.festival;

public class Festival {
	private int id;
	private String name;
	private String enDate;
	private String cnDate;
	private String enName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Festival [id=" + id + ", name=" + name + ", enDate=" + enDate + ", enName=" + enName + "]";
	}

	public String getCnDate() {
		return cnDate;
	}

	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}

	public String getEnDate() {
		return enDate;
	}

	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
}
