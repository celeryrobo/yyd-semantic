package com.yyd.semantic.db.service.impl.song;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.song.Category;
import com.yyd.semantic.db.mapper.song.CategoryMapper;
import com.yyd.semantic.db.mapper.song.CategoryRelationshipMapper;
import com.yyd.semantic.db.service.song.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private CategoryRelationshipMapper categoryRelationshipMapper;

	@Override
	public List<Category> getByParentId(Integer parentId) {
		Set<Integer> subIds = getSubidsByPid(parentId, 5);
		if (subIds == null) {
			subIds = new TreeSet<>();
		}
		subIds.add(parentId);
		List<Category> categories = new ArrayList<>();
		for (Integer subId : subIds) {
			Category category = categoryMapper.getById(subId);
			if (category != null) {
				categories.add(category);
			}
		}
		return categories;
	}

	public Set<Integer> getSubidsByPid(Integer pid, int loopCount) {
		loopCount -= 1;
		if (loopCount <= 0) {
			return null;
		}
		Set<Integer> result = new TreeSet<>();
		List<Integer> subIds = categoryRelationshipMapper.getSubIdsByParentId(pid);
		for (Integer subId : subIds) {
			Set<Integer> ids = getSubidsByPid(subId, loopCount);
			if (ids != null) {
				result.addAll(ids);
			}
		}
		result.addAll(subIds);
		return result;
	}

	@Override
	public List<Category> getByName(String name) {
		return categoryMapper.findByName(name);
	}

	@Override
	public List<String> getAllNames() {
		return categoryMapper.getAllNames();
	}

}
