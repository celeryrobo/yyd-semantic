package com.yyd.semantic.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.PoetrySentence;
import com.yyd.semantic.db.mapper.PoetrySentenceMapper;
import com.yyd.semantic.db.service.PoetrySentenceService;

@Service
public class PoetrySentenceServiceImpl implements PoetrySentenceService{
	@Autowired
	private PoetrySentenceMapper poetrySentenceMapper;
	
	@Override
	public List<PoetrySentence> getBySent(String sentence){
		return this.poetrySentenceMapper.getBySent(sentence);
	}
}
