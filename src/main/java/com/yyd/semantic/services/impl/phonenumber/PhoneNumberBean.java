package com.yyd.semantic.services.impl.phonenumber;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PhoneNumberBean extends AbstractSemanticResult{
	private String text;

	public PhoneNumberBean(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
