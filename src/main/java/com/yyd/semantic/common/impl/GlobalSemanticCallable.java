package com.yyd.semantic.common.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SpringContext;

@Component("GlobalSemanticCallable")
public class GlobalSemanticCallable implements SemanticCallable {
	@Autowired
	private SpringContext springContext;
	@Resource(name = "CommonSemanticCallable")
	private SemanticCallable semanticCallable;
	private Properties properties;

	public GlobalSemanticCallable() throws Exception {
		properties = FileUtils.buildProperties("semantics/callable.properties");
	}

	@Override
	public String call(String text, Object callName, Object... args) {
		String result = "null";
		try {
			SemanticCallable callable = semanticCallable;
			String className = properties.getProperty((String) callName);
			if (null != className) {
				Class<?> clazz = Class.forName(className);
				callable = (SemanticCallable) springContext.getBean(clazz);
			}
			String rs = callable.call(text, callName, args);
			if (rs != null) {
				result = rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
