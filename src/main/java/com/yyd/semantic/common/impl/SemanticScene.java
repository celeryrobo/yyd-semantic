package com.yyd.semantic.common.impl;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.yyd.semantic.common.SemanticMatching;
import com.yyd.semantic.services.SemanticService;

public class SemanticScene implements SemanticMatching {
	private static Compiler compiler = null;

	public SemanticScene(SemanticService semanticService) throws Exception {
		if (compiler == null) {
			String ybnf = semanticService.getSemanticLang();
			compiler = new YbnfCompiler(ybnf);
			if (compiler.isFailure()) {
				compiler = null;
				throw new Exception(compiler.getFailure());
			}
		}
	}

	@Override
	public YbnfCompileResult matching(String text) {
		YbnfCompileResult result = null;
		try {
			result = compiler.compile(text);
			if (result.getSlots().containsKey("service")) {
				result.setService(result.getSlots().get("service"));
			}
		} catch (Exception e) {
		}
		return result;
	}

}
