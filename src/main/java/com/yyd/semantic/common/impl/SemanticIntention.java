package com.yyd.semantic.common.impl;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.yyd.semantic.common.SemanticMatching;
import com.yyd.semantic.services.SemanticService;

public class SemanticIntention implements SemanticMatching {
	private Compiler compiler;

	public SemanticIntention(SemanticService semanticService, String service) throws Exception {
		String semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
		String langFilename = String.format("%sintentions/%s.ybnf", semanticBaseDirname, service);
		String ybnf = semanticService.getSemanticLang(langFilename);
		compiler = new YbnfCompiler(ybnf);
		System.out.println(compiler.isFailure());
		if (compiler.isFailure()) {
			throw new Exception(compiler.getFailure());
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
