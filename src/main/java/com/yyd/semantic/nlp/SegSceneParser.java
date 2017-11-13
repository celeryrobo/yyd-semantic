package com.yyd.semantic.nlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegSceneParser {
	 /**
	  * 通过实体词识别场景
	  * @param text
	  * @return :场景或null(此时无实体词)
	  */
	 public static  String parse(String text, String... forestNames) {
		 String service = null;
		 List<WordTerm> terms = NLPFactory.segment(text, forestNames);
		 
		 Map<String,Integer> services = new HashMap<String,Integer>();
		 for(WordTerm term:terms) {		
			 List<String> natureServices = NatureDict.getServices(term.getNature());
			 if(null == natureServices) {
				 continue;
			 }
			 
			 for(String serv:natureServices) {
				 Integer count = services.get(serv);
				 if(null == count) {
					 services.put(serv, 1);
				 }
				 else
				 {
					 services.put(serv, count+1);
				 }
			 }			 
		 }
		 
		 //统计最大
		 int max = 0;
		 for(Map.Entry<String, Integer> entry : services.entrySet()) {
			 int cur = entry.getValue();
			 if(cur > max) {
				 max = cur;
				 service = entry.getKey();
			 }
		 }
		 
		 return service;
	 }
}
