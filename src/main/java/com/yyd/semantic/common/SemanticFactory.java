package com.yyd.semantic.common;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.Semantic;

@Component
public class SemanticFactory {
	private static Map<String, Semantic<?>> semanticMap; // Semantic对象缓存
	private Properties properties;
	@Autowired
	private SpringContext springContext;

	public SemanticFactory() throws Exception {
		String classRootPath = this.getClass().getResource("/semantics/").getPath();
		ArrayList<String> propFilenames = FileUtils.listFilenames(classRootPath, ".properties");
		String[] propFilenameArr = new String[propFilenames.size()];
		propFilenames.toArray(propFilenameArr);
		semanticMap = new HashMap<String, Semantic<?>>();
		properties = new Properties();
		for (String filename : propFilenames) {
			FileInputStream fis = new FileInputStream(filename);
			properties.load(fis);
			fis.close();
		}
	}

	public Semantic<?> build(String serviceName) throws Exception {
		Semantic<?> semantic = semanticMap.get(serviceName);
		if (semantic == null) {
			String className = properties.getProperty(serviceName);
			Class<?> clazz = Class.forName(className);
			System.out.println(clazz.getName());
			semantic = (Semantic<?>) springContext.getBean(clazz);
			semanticMap.put(serviceName, semantic);
		}
		return semantic;
	}
}
