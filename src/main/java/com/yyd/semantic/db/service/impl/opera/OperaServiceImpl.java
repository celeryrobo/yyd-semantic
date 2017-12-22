package com.yyd.semantic.db.service.impl.opera;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.opera.OperaCategory;
import com.yyd.semantic.db.bean.opera.Opera;
import com.yyd.semantic.db.bean.opera.OperaTagType;
import com.yyd.semantic.db.mapper.opera.OperaCategoryMapper;
import com.yyd.semantic.db.mapper.opera.OperaTagMapper;
import com.yyd.semantic.db.mapper.opera.OperaMapper;
import com.yyd.semantic.db.service.opera.OperaService;

@Service
public class OperaServiceImpl implements OperaService{
	
	@Autowired
	private OperaMapper operaMapper;
	@Autowired
	private OperaCategoryMapper categoryMapper;
	@Autowired
	private OperaTagMapper operaTagMapper;
	
	@Override
	public List<Integer> getIdList(){
		return operaMapper.getIdList();
	}
	
	@Override
	public List<String> getAllNames(){
		return operaMapper.getAllName();
	}

	public Opera getById(Integer id) {
		return operaMapper.getById(id);
	}

	@Override
	public List<Opera> getByName(String name){
		return operaMapper.getByName(name);
	}
		
	@Override
	public List<Opera> findByCategoryId(Integer categoryId){
		List<Integer> operaIds = operaTagMapper.getResourceIdsByTag(categoryId, OperaTagType.TAG_OPERA_CATEGORY);
		List<Opera> operas = new ArrayList<>();
		for (Integer operaId : operaIds) {
			Opera song = getById(operaId);
			if(song!=null) {
				operas.add(song);
			}
		}
		return operas;
	}
	
	@Override
	public List<Opera> findByCategoryName(String categoryName){
		List<Opera> operas = new ArrayList<>();
		List<OperaCategory> categorys = categoryMapper.findByName(categoryName);
		if(categorys.size() <= 0) {
			return operas;
		}
		int categoryId = categorys.get(0).getId();
		
		operas = findByCategoryId(categoryId);		
		
		return operas;
	}

}
