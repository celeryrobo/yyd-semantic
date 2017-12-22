package com.yyd.semantic.db.service.idiom;

import java.util.List;

import com.yyd.semantic.db.bean.idiom.Idiom;

public interface IdiomService {

	Idiom getById(Integer id);

	Idiom getByContent(String content);

	List<Idiom> findByPyFirst(String pyFirst);

	List<Idiom> findByPyLast(String pyLast);

	List<String> findAll();

	void update(Idiom idiom);
}
