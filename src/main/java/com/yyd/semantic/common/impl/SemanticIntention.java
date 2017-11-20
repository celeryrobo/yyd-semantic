package com.yyd.semantic.common.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

public class SemanticIntention implements SemanticMatching {
	private static Map<String, Compiler> compilerMap;
	private Compiler compiler;

	static {
		try {
			compilerMap = new HashMap<>();
			String intentionBaseDirname = FileUtils.getResourcePath() + "semantics/intentions/";
			List<File> files = FileUtils.listFiles(intentionBaseDirname, ".ybnf");
			for (File file : files) {
				String lang = FileUtils.readFile(file);
				String service = file.getName().split("\\.")[0];
				Compiler comp = new YbnfCompiler(lang);
				compilerMap.put(service, comp);
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
			long startTs = System.currentTimeMillis();
			result = compiler.compile(text);
			System.out.println("Semantic Intention Run Time :" + (System.currentTimeMillis() - startTs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
