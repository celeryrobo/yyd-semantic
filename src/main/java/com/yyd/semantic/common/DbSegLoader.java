package com.yyd.semantic.common;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.impl.SemanticIntention;
import com.yyd.semantic.common.impl.SemanticScene;
import com.yyd.semantic.nlp.NLPFactory;

@Component
public class DbSegLoader {
	@Autowired
	private SpringContext springContext;
	private Properties properties;
	private static Map<String, Item> index = new HashMap<>();

	public DbSegLoader() throws Exception {
		this.properties = FileUtils.buildProperties("semantics/forests.properties");
		initIndex(); // 初始化词库
		SemanticScene.init(); // 初始化判断场景语义模板
		SemanticIntention.init(); // 初始化场景语义模板
	}

	public void loads() throws Exception {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			Class<?> clazz = Class.forName((String) entry.getValue());
			SegmentLibrary sl = (SegmentLibrary) springContext.getBean(clazz);
			List<Value> values = sl.load();
			Forest forest = new Forest();
			for (Value value : values) {
				Library.insertWord(forest, value);
				Item item = index.get(value.getKeyword());
				if (item != null) {
					for (String w : item.getItems()) {
						if (!w.equals(value.getKeyword())) {
							Library.insertWord(forest, new Value(w, value.getParamers()));
						}
					}
				}
			}
			NLPFactory.forests.put(entry.getKey(), forest);
		}
	}

	public void initIndex() throws Exception {
		String filepath = FileUtils.getResourcePath() + "semantics/synonyms.cfg";
		try (BufferedReader reader = FileUtils.fileReader(filepath)) {
			String synonym = null;
			while ((synonym = reader.readLine()) != null) {
				if (synonym.length() > 1) {
					new Item(synonym.split("\t"));
				}
			}
		}
	}

	public Item getItem(String syn) {
		return index.get(syn);
	}

	public static class Item {
		private Set<String> items;

		public Item(String... syns) {
			items = new TreeSet<>();
			for (String syn : syns) {
				items.add(syn);
				index.put(syn, this);
			}
		}

		public Set<String> getItems() {
			return items;
		}

		@Override
		public String toString() {
			return StringUtil.joiner(items, "|");
		}
	}
}
