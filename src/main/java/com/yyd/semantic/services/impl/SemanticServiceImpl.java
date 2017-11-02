package com.yyd.semantic.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticContext;
import com.yyd.semantic.common.SemanticFactory;
import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.common.impl.SemanticIntention;
import com.yyd.semantic.common.impl.SemanticScene;
import com.yyd.semantic.services.SemanticService;

@Service
public class SemanticServiceImpl implements SemanticService {
	private static Map<String, String> fileLangMap;
	private String semanticBaseDirname;
	private String semanticLang;
	@Autowired
	private SemanticFactory semanticFactory;
	@Autowired
	private SemanticContext semanticContext;

	public SemanticServiceImpl() throws Exception {
		fileLangMap = new HashMap<String, String>();
		semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
		semanticLang = FileUtils.readFile(semanticBaseDirname + "main.ybnf");
	}

	@Override
	public String getSemanticLang() {
		return semanticLang;
	}

	@Override
	public String getSemanticLang(String langFilename) throws Exception {
		String lang = "";
		if (fileLangMap.containsKey(langFilename)) {
			lang = fileLangMap.get(langFilename);
		} else {
			lang = FileUtils.readFile(langFilename);
			fileLangMap.put(langFilename, lang);
		}
		return lang;
	}

	@Override
	public SemanticResult handleSemantic(String text, String userIdentify) throws Exception {
		semanticContext.setUserIdentify(userIdentify);
		YbnfCompileResult result = parseSemantic(text, semanticContext.getService());
		SemanticResult sr;
		if (result == null) {
			sr = new SemanticResult(404, "match error ！！！", result);
			sr.setText(text);
		} else {
			semanticContext.setService(result.getService());
			Semantic<?> semantic = semanticFactory.build(result.getService());
			AbstractSemanticResult rs = semantic.handle(result);
			sr = new SemanticResult(rs.getErrCode(), null, result);
			sr.setData(rs);
		}
		return sr;
	}

	private YbnfCompileResult parseSemantic(String text, String service) throws Exception {
		YbnfCompileResult result;
		if (service == null || "".equals(service)) {
			result = new SemanticScene(this).matching(text);
			if (result != null) {
				YbnfCompileResult rs = parseSemantic(text, result.getService());
				result = (rs == null ? result : rs);
			}
		} else {
			result = new SemanticIntention(this, service).matching(text);
			if (result == null) {
				result = parseSemantic(text, null);
			}
		}
		return result;
	}

}
