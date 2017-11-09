package com.yyd.semantic.common.impl;

import java.util.HashMap;
import java.util.Map;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.SemanticMatching;
import com.yyd.semantic.services.SemanticService;

public class SemanticIntention implements SemanticMatching {
	private static Map<String, Compiler> compilerMap = new HashMap<String, Compiler>();
	private Compiler compiler;

	public SemanticIntention(SemanticService semanticService, String service, SemanticCallable semanticCallable)
			throws Exception {
		if (compilerMap.containsKey(service)) {
			compiler = compilerMap.get(service);
		} else {
			String semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
			String langFilename = String.format("%sintentions/%s.ybnf", semanticBaseDirname, service);
			String ybnf = semanticService.getSemanticLang(langFilename);
			compiler = new YbnfCompiler(ybnf);
			if (compiler.isFailure()) {
				throw new Exception(compiler.getFailure());
			}
			compiler.setSemanticCallable(semanticCallable);
			System.out.println(compiler.getGrammar());
			compilerMap.put(service, compiler);
		}
	}

	@Override
	public YbnfCompileResult matching(String text) {
		YbnfCompileResult result = null;
		try {
			result = compiler.compile(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
