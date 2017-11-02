package com.yyd.semantic.common;

import com.ybnf.compiler.beans.YbnfCompileResult;

public interface SemanticMatching {
	YbnfCompileResult matching(String text);
}
