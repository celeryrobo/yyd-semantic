package com.yyd.semantic.db.service.impl.poetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.poetry.PoetrySentence;
import com.yyd.semantic.db.mapper.poetry.PoetrySentenceMapper;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;

@Service
public class PoetrySentenceServiceImpl implements PoetrySentenceService{
	@Autowired
	private PoetrySentenceMapper poetrySentenceMapper;
	
	@Override
	public List<PoetrySentence> getBySent(String sentence){
		return poetrySentenceMapper.getBySent(sentence);
	}

	@Override
	public List<PoetrySentence> getByPoetryId(Integer poetryId) {
		return poetrySentenceMapper.getByPoetryId(poetryId);
	}

	@Override
	public List<String> getAllSentences() {
		return poetrySentenceMapper.getAllSentences();
	}
}
