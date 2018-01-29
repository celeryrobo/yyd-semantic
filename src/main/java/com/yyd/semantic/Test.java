package com.yyd.semantic;

import java.io.BufferedReader;

import com.yyd.semantic.common.FileUtils;

class Parser {
	private int pos;
	private String target;

	public Parser(String target) {
		this.pos = 0;
		this.target = target;
		expr();
	}

	private void expr() {
		term();
		while (true) {
			if(pos >= target.length()) {
				break;
			}
			char ch = target.charAt(pos);
			if ('+' == ch) {
				match(ch);
				term();
				System.out.print(ch);
			} else if ('-' == ch) {
				match(ch);
				term();
				System.out.print(ch);
			} else {
				term();
			}
		}
	}

	private void term() {
		char ch = target.charAt(pos);
		if (Character.isDigit(ch)) {
			System.out.print(ch);
			match(ch);
		} else {
			throw new Error("syntax error");
		}
	}

	private void match(char ch) {
		if (ch == target.charAt(pos)) {
			pos++;
		} else {
			throw new Error("syntax error");
		}
	}
}

public class Test {

	public static void main(String[] args) throws Exception {
		new Parser("1+2-1");
		String text = "我想听，(杜甫),的（sss）诗";
		text = text.replaceAll("[,\\.;:'\"!，。；：‘’“”！\\pS]", "");
		System.out.println(text);
		
		String filename = FileUtils.getResourcePath() + "semantics/mitie.properties";
		BufferedReader br = FileUtils.fileReader(filename);
		String line = null;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
	}
}
