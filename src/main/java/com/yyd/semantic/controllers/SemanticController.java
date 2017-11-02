package com.yyd.semantic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		try {
			sr = semanticService.handleSemantic(lang);
		} catch (Exception e) {
			sr = new SemanticResult(500, e.getMessage(), null);
			e.printStackTrace();
		}
		return sr;
	}
}
