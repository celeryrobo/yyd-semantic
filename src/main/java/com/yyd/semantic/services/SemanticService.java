package com.yyd.semantic.services;

import com.yyd.semantic.common.SemanticResult;

public interface SemanticService {
	
	String getSemanticLang();
	
	String getSemanticLang(String langFilename) throws Exception;
	
	SemanticResult handleSemantic(String service) throws Exception;
	
}
