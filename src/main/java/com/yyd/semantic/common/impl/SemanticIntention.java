package com.yyd.semantic.common.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

public class SemanticIntention implements SemanticMatching {
	private static Map<String, Compiler> compilerMap = new HashMap<>();
	private Compiler compiler;

	static {
		try {
			Properties properties = FileUtils.buildProperties("semantics/semantic.properties");
			String intentionBaseDirname = FileUtils.getResourcePath() + "semantics/intentions/";
			for (Object service : properties.keySet()) {
				String langFilename = intentionBaseDirname + service + ".ybnf";
				String lang = FileUtils.readFile(langFilename);
				Compiler comp = new YbnfCompiler(lang);
				compilerMap.put((String) service, comp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SemanticIntention(String service, SemanticCallable semanticCallable) throws Exception {
		if (compilerMap.containsKey(service)) {
			compiler = compilerMap.get(service);
			if (compiler.isFailure()) {
				throw new Exception(compiler.getFailure());
			}
			compiler.setSemanticCallable(semanticCallable);
			System.out.println(compiler.getGrammar());
		} else {
			throw new Exception("场景：" + service + "，不存在！");
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
