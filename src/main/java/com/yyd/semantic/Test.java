package com.yyd.semantic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class TestInput extends InputStream {
	private byte[] content;
	private Integer index;

	public TestInput(String content) {
		this.content = content.getBytes();
		this.index = 0;
	}

	@Override
	public int read() throws IOException {
		if (content.length <= index) {
			return -1;
		}
		return content[index++];
	}

}

public class Test {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new TestInput("中国\n人民\n共和国")));
		String buf = null;
		while ((buf = br.readLine()) != null) {
			System.out.println(buf);
		}
		br.close();
	}

}
