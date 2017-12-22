package com.yyd.semantic.db.service.music;

import java.util.List;

import com.yyd.semantic.db.bean.music.Category;

public interface CategoryService {
	public List<String> getAllNames();

	public List<Category> getByName(String name);
}
