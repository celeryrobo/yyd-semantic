package com.yyd.semantic.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.semantic.Semantic;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticFactory;
import com.yyd.semantic.services.SemanticService;

@Service
public class SemanticServiceImpl implements SemanticService {
	private String semanticBaseDirname;
	private String semanticLang;
	@Autowired
	private SemanticFactory semanticFactory;
	
	public SemanticServiceImpl() throws Exception {
		semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
		loadSemanticLang();
	}

	public void loadSemanticLang() throws Exception {
		semanticLang = FileUtils.readFile(semanticBaseDirname + "main.ybnf");
	}

	@Override
	public String getSemanticLang() throws Exception {
		if (semanticLang == null) {
			loadSemanticLang();
		}
		return semanticLang;
	}

	@Override
	public Semantic<?> handleSemantic(String service) throws Exception {
		return semanticFactory.build(service);
	}

}
