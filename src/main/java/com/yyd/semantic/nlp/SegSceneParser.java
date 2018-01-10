package com.yyd.semantic.nlp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegSceneParser {
	/**
	 * 通过实体词识别场景
	 * 
	 * @param text
	 * @return :场景数组或null(此时无实体词)
	 */
	public static String[] parse(String text) {
		List<WordTerm> terms = NLPFactory.segment(text);
		Map<String, Integer> services = new HashMap<String, Integer>();
		for (WordTerm term : terms) {
			List<String> natureServices = NatureDict.getServices(term.getNature());
			if (null == natureServices) {
				continue;
			}
			for (String serv : natureServices) {
				Integer weight = services.get(serv);
				if (null == weight) {
					services.put(serv, 1);
				} else {
					services.put(serv, weight + 1);
				}
			}
		}
		if (services.isEmpty()) {
			return null;
		}
		Object[] sceneServices = services.entrySet().toArray();
		Arrays.sort(sceneServices, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Object o1, Object o2) {
				Map.Entry<String, Integer> e1 = (Map.Entry<String, Integer>) o1;
				Map.Entry<String, Integer> e2 = (Map.Entry<String, Integer>) o2;
				return e1.getValue() - e2.getValue();
			}
		});
		String[] serviceArr = new String[sceneServices.length];
		for (int i = 0; i < sceneServices.length; i++) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> me = (Map.Entry<String, Integer>) sceneServices[i];
			serviceArr[i] = me.getKey();
		}
		return serviceArr;
	}
}
