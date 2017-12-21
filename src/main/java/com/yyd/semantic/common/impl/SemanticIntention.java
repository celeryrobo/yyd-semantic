package com.yyd.semantic.common.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.JCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

public class SemanticIntention implements SemanticMatching {
	private static Map<String, ICompiler> compilerMap = null;
	private ICompiler compiler;

	public static void init() {
		try {
			compilerMap = new HashMap<>();
			String intentionBaseDirname = FileUtils.getResourcePath() + "semantics/intentions/";
			List<File> files = FileUtils.listFiles(intentionBaseDirname, ".ybnf");
			for (File file : files) {
				ICompiler comp = new JCompiler(FileUtils.readFile(file));
				String service = file.getName().split("\\.")[0];
				compilerMap.put(service, comp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SemanticIntention(String service, SemanticCallable semanticCallable) throws Exception {
		if (compilerMap == null) {
			init();
		}
		compiler = compilerMap.get(service);
		if (compiler != null) {
			compiler.setSemanticCallable(semanticCallable);
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
			System.out.println("Semantic Intention Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
					+ result.getService());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
