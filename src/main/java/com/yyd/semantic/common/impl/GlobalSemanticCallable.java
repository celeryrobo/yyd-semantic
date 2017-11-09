package com.yyd.semantic.common.impl;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.SpringContext;

@Component("GlobalSemanticCallable")
public class GlobalSemanticCallable implements SemanticCallable {
	@Autowired
	private SpringContext springContext;
	private Properties properties;

	public GlobalSemanticCallable() throws Exception {
		String callablePropertiesPath = this.getClass().getResource("/semantics/callable.properties").getPath();
		properties = new Properties();
		FileInputStream fis = new FileInputStream(callablePropertiesPath);
		properties.load(fis);
		fis.close();
	}

	@Override
	public String call(String text, Object callName, Object... args) {
		String result = "null";
		try {
			String className = properties.getProperty((String) callName);
			Class<?> clazz = Class.forName(className);
			SemanticCallable semanticCallable = (SemanticCallable) springContext.getBean(clazz);
			String rs = semanticCallable.call(text, callName, args);
			if(rs != null) {
				result = rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
