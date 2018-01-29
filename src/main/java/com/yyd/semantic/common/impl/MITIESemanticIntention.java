package com.yyd.semantic.common.impl;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.yyd.semantic.common.SemanticMatching;

public class MITIESemanticIntention implements SemanticMatching {
	private ICompiler compiler = null;

	public MITIESemanticIntention(MITIESemanticScene semanticScene, String serviceName) {
		compiler = semanticScene.getCompilerBySerivce(serviceName);
	}

	@Override
	public YbnfCompileResult matching(String text) {
		YbnfCompileResult result = null;
		if (compiler != null) {
			try {
				long startTs = System.currentTimeMillis();
				System.out.println("Text :" + text);
				result = compiler.compile(text);
				System.out.println("MITIE Semantic Intention Run Time :" + (System.currentTimeMillis() - startTs)
						+ " Service :" + result.getService());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
