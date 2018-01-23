package com.yyd.semantic.services.impl.calc;

public class IndexRange {
	public int letf;
	public int right;
	public IndexRange(int letf,int right){
		this.letf = letf;
		this.right = right;
	}
	@Override
	public String toString() {
		return "[letf=" + letf + ", right=" + right + "]";
	}
}
