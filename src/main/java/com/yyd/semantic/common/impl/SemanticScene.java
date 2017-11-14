package com.yyd.semantic.common.impl;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

public class SemanticScene implements SemanticMatching {
	private static Compiler compiler = null;
	
	static {
		try {
			String semanticFilename = FileUtils.getResourcePath() + "semantics/main.ybnf";
			String semanticLang = FileUtils.readFile(semanticFilename);
			compiler = new YbnfCompiler(semanticLang);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SemanticScene(SemanticCallable semanticCallable) throws Exception {
		if (compiler != null) {
			if (compiler.isFailure()) {
				compiler = null;
				throw new Exception(compiler.getFailure());
			}
			compiler.setSemanticCallable(semanticCallable);
			System.out.println(compiler.getGrammar());
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
			e.printStackTrace();
		}
		return result;
	}

}
