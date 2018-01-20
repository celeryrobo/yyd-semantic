package com.yyd.semantic.services.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticCallable;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.SemanticFactory;
import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.common.impl.SemanticIntention;
import com.yyd.semantic.common.impl.SemanticScene;
import com.yyd.semantic.common.impl.WaringSemanticResult;
import com.yyd.semantic.nlp.SegSceneParser;
import com.yyd.semantic.services.SemanticService;

@Service
@Scope("prototype")
public class SemanticServiceImpl implements SemanticService {
	@Autowired
	private SemanticFactory semanticFactory;
	@Autowired
	private SemanticContext semanticContext;
	@Resource(name = "GlobalSemanticCallable")
	private SemanticCallable semanticCallable;

	@Override
	public SemanticResult handleSemantic(String text, String userIdentify) throws Exception {
		semanticContext.loadByUserIdentify(userIdentify);
		YbnfCompileResult result = null;
		if (text != null && !text.isEmpty()) {
			String lang = text.replaceAll("[,;:'\"!，。；：‘’“”！\\s+]", "");
			result = parseSemantic(lang, semanticContext.getService());
		}
		SemanticResult sr;
		if (result == null) {
			sr = new SemanticResult(404, "Match Fail!", result, new WaringSemanticResult("我听不懂你想说什么！"));
		} else {
			// 切换场景则清空参数
			if (!result.getService().equals(semanticContext.getService())) {
				if (!semanticContext.getParams().isEmpty()) {
					semanticContext.getParams().clear();
				}
			}
			Semantic<?> semantic = semanticFactory.build(result.getService());
			AbstractSemanticResult rs = semantic.handle(result, semanticContext);
			semanticContext.setService(result.getService());
			sr = new SemanticResult(rs.getErrCode(), rs.getErrMsg(), result, rs);
		}
		sr.setText(text);
		return sr;
	}

	private YbnfCompileResult parseSemantic(String text, String service) throws Exception {
		YbnfCompileResult result;
		if (service == null || "".equals(service)) {
			// 场景为空时表示场景匹配，场景不为空表示意图匹配
			result = new SemanticScene(semanticCallable).matching(text);
			// 如果匹配失败则进入分词实体识别，否则进入意图匹配
			if (result == null) {
				// 分词场景识别
				String[] servs = detectService(text);
				if (servs != null) {
					// 遍历关键词所识别的场景，直到获得结果未知，遍历完成后还没有结果则表示语义识别失败
					for (String serv : servs) {
						result = new SemanticIntention(serv, semanticCallable).matching(text);
						if (result != null) {
							break;
						}
					}
				}
			}
		} else {
			// 根据场景名进行意图匹配
			result = new SemanticIntention(service, semanticCallable).matching(text);
			// 意图匹配失败后，则进入场景匹配
			if (result == null) {
				result = parseSemantic(text, null);
			}
		}
		return result;
	}

	/**
	 * 分词场景识别方法
	 * 
	 * @param text
	 *            语料
	 * @return 场景名
	 */
	private String[] detectService(String text) {
		return SegSceneParser.parse(text);
	}

}
