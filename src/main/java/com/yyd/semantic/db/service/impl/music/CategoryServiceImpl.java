package com.yyd.semantic.db.service.impl.music;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.music.Category;
import com.yyd.semantic.db.mapper.music.CategoryMapper;
import com.yyd.semantic.db.service.music.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
		
	@Override
	public List<Category> getByName(String name) {
		return categoryMapper.findByName(name);
	}

	@Override
	public List<String> getAllNames() {
		return categoryMapper.getAllNames();
	}

}
