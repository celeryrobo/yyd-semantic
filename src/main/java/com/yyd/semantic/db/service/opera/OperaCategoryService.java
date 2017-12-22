package com.yyd.semantic.db.service.opera;

import java.util.List;

import com.yyd.semantic.db.bean.opera.OperaCategory;

public interface OperaCategoryService {
	public List<String> getAllNames();

	public List<OperaCategory> getByName(String name);
}
