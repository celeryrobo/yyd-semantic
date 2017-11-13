package com.yyd.semantic.common.impl;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

public class SemanticScene implements SemanticMatching {
	private static Compiler compiler = null;
	private static String semanticLang;
	
	static {
		String semanticBaseDirname = FileUtils.getResourcePath() + "semantics/";
		try {
			semanticLang = FileUtils.readFile(semanticBaseDirname + "main.ybnf");
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
