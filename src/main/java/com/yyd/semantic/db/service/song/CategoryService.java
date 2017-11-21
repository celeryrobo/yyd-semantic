package com.yyd.semantic.db.service.song;

import java.util.List;

import com.yyd.semantic.db.bean.song.Category;

public interface CategoryService {
	public List<String> getAllNames();

	public List<Category> getByParentId(Integer parentId);

	public List<Category> getByName(String name);
}
