package com.yyd.semantic.services;

import com.ybnf.semantic.Semantic;

public interface SemanticService {
	
	String getSemanticLang() throws Exception;
	
	Semantic<?> handleSemantic(String service) throws Exception;
	
}
