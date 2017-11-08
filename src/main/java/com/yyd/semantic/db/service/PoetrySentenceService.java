package com.yyd.semantic.db.service;

import java.util.List;

import com.yyd.semantic.db.bean.PoetrySentence;

public interface PoetrySentenceService {
	public List<PoetrySentence> getBySent(String sentence);
}
