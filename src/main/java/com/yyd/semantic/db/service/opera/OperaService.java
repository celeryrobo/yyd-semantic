package com.yyd.semantic.db.service.opera;

import java.util.List;

import com.yyd.semantic.db.bean.opera.Opera;


public interface OperaService {
	public List<Integer> getIdList();
	
	public List<String> getAllNames();

	public Opera getById(Integer id);

	public List<Opera> getByName(String name);

	public List<Opera> findByCategoryId(Integer categoryId);
	
	public List<Opera> findByCategoryName(String categoryName);
}
