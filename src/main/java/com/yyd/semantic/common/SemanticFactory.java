package com.yyd.semantic.common;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.Semantic;

@Component
public class SemanticFactory {
	private Properties properties;
	@Autowired
	private SpringContext springContext;

	public SemanticFactory() throws Exception {
		String semanticPropertiesPath = this.getClass().getResource("/semantics/semantic.properties").getPath();
		properties = new Properties();
		FileInputStream fis = new FileInputStream(semanticPropertiesPath);
		properties.load(fis);
		fis.close();
	}

	public Semantic<?> build(String serviceName) throws Exception {
		Semantic<?> semantic = null;
		if (semantic == null) {
			String className = properties.getProperty(serviceName);
			Class<?> clazz = Class.forName(className);
			semantic = (Semantic<?>) springContext.getBean(clazz);
		}
		return semantic;
	}
}
