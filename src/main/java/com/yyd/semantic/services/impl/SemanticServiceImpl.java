package com.yyd.semantic.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class SemanticServiceImpl implements SemanticService {
	private static Map<String, String> fileLangMap;
	private static String semanticBaseDirname;
	private static String semanticLang;
	@Autowired
	private SemanticFactory semanticFactory;
	@Autowired
	private SemanticContext semanticContext;

	public SemanticServiceImpl() throws Exception {
		fileLangMap = new HashMap<String, String>();
		if (semanticBaseDirname == null) {
			semanticBaseDirname = this.getClass().getResource("/semantics/").getPath();
		}
		if (semanticLang == null) {
			semanticLang = FileUtils.readFile(semanticBaseDirname + "main.ybnf");
		}
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
		semanticContext.loadByUserIdentify(userIdentify);
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
		semanticContext.save();
		return sr;
	}

	private YbnfCompileResult parseSemantic(String text, String service) throws Exception {
		return parseSemantic(text, service, 0);
	}

	private YbnfCompileResult parseSemantic(String text, String service, int loopCount) throws Exception {
		if (loopCount > 5) {
			return null;
		}
		YbnfCompileResult result;
		if (service == null || "".equals(service)) {
			// 场景为空时表示场景匹配，场景不为空表示意图匹配
			result = new SemanticScene(this).matching(text);
			// 如果匹配失败则进入分词实体识别，否则进入意图匹配
			if (result == null) {
				String serv = cutWord(text);
				if (serv != null) {
					// 表示匹配到了场景，否则没有匹配到场景
					result = parseSemantic(text, serv, loopCount + 1);
				}
			} else {
				YbnfCompileResult rs = parseSemantic(text, result.getService(), loopCount + 1);
				result = (rs == null ? result : rs);
			}
		} else {
			// 根据场景名进行意图匹配
			result = new SemanticIntention(this, service).matching(text);
			// 意图匹配失败后，则进入场景匹配
			if (result == null) {
				result = parseSemantic(text, null, loopCount + 1);
			}
		}
		return result;
	}

	/**
	 * 分词意图识别方法
	 * 
	 * @param text
	 *            语料
	 * @return 场景名
	 */
	private String cutWord(String text) {
		return "song";
	}

}
