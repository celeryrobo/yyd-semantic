package com.yyd.semantic.services.impl.song;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

@Component
public class SongSemantic implements Semantic<SongBean> {

	@Override
	public SongBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return new SongBean(ybnfCompileResult.toString());
	}

}
