package com.yyd.semantic.db.service.poetry;

import java.util.List;

import com.yyd.semantic.db.bean.poetry.PoetrySentence;

public interface PoetrySentenceService {
	public List<PoetrySentence> getBySent(String sentence);

	public List<PoetrySentence> getByPoetryId(Integer poetryId);

	public List<String> getAllSentences();
}
