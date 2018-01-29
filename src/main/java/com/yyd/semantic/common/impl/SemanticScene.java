package com.yyd.semantic.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.JCompiler;
import com.ybnf.semantic.SemanticCallable;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

@Component("SemanticScene")
public class SemanticScene implements SemanticMatching {
	private static ICompiler compiler = null;
	@Resource(name = "GlobalSemanticCallable")
	private SemanticCallable semanticCallable;

	public static void init() {
		try {
			System.out.println("Service Init : common");
			String semanticFilename = FileUtils.getResourcePath() + "semantics/main.ybnf";
			compiler = new JCompiler(FileUtils.readFile(semanticFilename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SemanticScene() throws Exception {
		if (compiler == null) {
			init();
		}
		compiler.setSemanticCallable(semanticCallable);
	}

	@Override
	public YbnfCompileResult matching(String text) {
		String lang = text.replaceAll("[\\?,;:'\"!？，。；：‘’“”！\\s+]", "");
		YbnfCompileResult result = null;
		try {
			long startTs = System.currentTimeMillis();
			System.out.println("Text :" + lang);
			result = compiler.compile(lang);
			System.out.println("Semantic Scene Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
					+ result.getService());
			if (result.getSlots().containsKey("service")) {
				result.setService(result.getSlots().get("service"));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}

}
