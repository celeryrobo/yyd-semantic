package com.yyd.semantic.db.service.impl.opera;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.opera.OperaCategory;
import com.yyd.semantic.db.mapper.opera.OperaCategoryMapper;
import com.yyd.semantic.db.service.opera.OperaCategoryService;

@Service
public class OperaCategoryServiceImpl implements OperaCategoryService {
	@Autowired
	private OperaCategoryMapper categoryMapper;
		
	@Override
	public List<OperaCategory> getByName(String name) {
		return categoryMapper.findByName(name);
	}

	@Override
	public List<String> getAllNames() {
		return categoryMapper.getAllNames();
	}
}
