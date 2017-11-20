package com.yyd.semantic.common;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.nlp.NLPFactory;

@Component
public class DbSegLoader {
	@Autowired
	private SpringContext springContext;
	private Properties properties;

	public DbSegLoader() throws Exception {
		this.properties = FileUtils.buildProperties("semantics/forests.properties");
	}

	public void loads() throws Exception {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			Class<?> clazz = Class.forName((String) entry.getValue());
			SegmentLibrary sl = (SegmentLibrary) springContext.getBean(clazz);
			List<Value> values = sl.load();
			Forest forest = Library.makeForest(values);
			NLPFactory.forests.put(entry.getKey(), forest);
		}
	}

}
