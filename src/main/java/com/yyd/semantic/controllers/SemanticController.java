package com.yyd.semantic.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ybnf.compiler.Compiler;
import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.YbnfCompiler;
import com.ybnf.semantic.Semantic;
import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.services.SemanticService;

@RestController
@RequestMapping("/semantic")
public class SemanticController {
	@Autowired
	private SemanticService semanticService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public SemanticResult get(@RequestParam String lang) {
		SemanticResult sr = null;
		String service = "";
		try {
			String semanticLang = semanticService.getSemanticLang();
			Compiler cp = new YbnfCompiler(semanticLang);
			System.out.println(cp.getGrammar());// 语法打印
			YbnfCompileResult result = cp.compile(lang);
			System.out.println(result);// 处理结果打印
			if (result.getSlots().containsKey("service")) {
				service = result.getSlots().get("service");
			} else {
				service = result.getService();
			}
			Semantic<?> semantic = semanticService.handleSemantic(service);
			AbstractSemanticResult rs = semantic.handle(result);
			Map<String, Object> slots = new HashMap<String, Object>();
			slots.putAll(result.getSlots());
			slots.put("objects", result.getObjects());
			sr = new SemanticResult(rs.getErrCode(), null, service, lang);
			sr.setSlots(slots);
			sr.setData(rs);
		} catch (Exception e) {
			sr = new SemanticResult(500, e.getMessage(), null, lang);
			e.printStackTrace();
		}
		return sr;
	}
}
