package com.yyd.semantic.services.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticFactory;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.services.SemanticService;

@Service
public class SemanticServiceImpl implements SemanticService {
	private String semanticBaseDirname;
	private String semanticLang;
	private String[] propFilenameArr;
	private SemanticFactory semanticFactory;
	
	public SemanticServiceImpl() throws Exception {
		semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
		loadSemanticLang();
		loadSemanticProps();
		semanticFactory = new SemanticFactory(propFilenameArr);
	}

	public void loadSemanticLang() throws Exception {
		semanticLang = FileUtils.readFile(semanticBaseDirname + "main.ybnf");
	}

	public void loadSemanticProps() throws Exception {
		ArrayList<String> propFilenames = FileUtils.listFilenames(semanticBaseDirname, ".properties");
		propFilenameArr = new String[propFilenames.size()];
		propFilenames.toArray(propFilenameArr);
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
