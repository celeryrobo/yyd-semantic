package com.yyd.semantic.services.impl.opera;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.opera.Opera;
import com.yyd.semantic.db.bean.opera.OperaCategory;
import com.yyd.semantic.db.bean.opera.OperaTag;
import com.yyd.semantic.db.bean.opera.OperaTagType;
import com.yyd.semantic.db.service.opera.OperaCategoryService;
import com.yyd.semantic.db.service.opera.OperaService;
import com.yyd.semantic.db.service.opera.OperaTagService;



@Component
public class OperaSemantic implements Semantic<OperaBean> {
	@Autowired
	private OperaCategoryService categoryService;
	@Autowired
	private OperaService operaService;	
	@Autowired
	private OperaTagService operaTagService;
	
	@Override
	public OperaBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		OperaBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case OperaIntent.QUERY_OPERA: {
				result = queryOpera(objects, semanticContext);
				break;
			}		
			default: {
				String msg = OperaError.getMsg(OperaError.ERROR_UNKNOW_INTENT);
				result = new OperaBean(OperaError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
		return result;
	}
	
	private OperaBean queryOpera(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = OperaError.ERROR_NO_RESOURCE;
		
		OperaSlot ss = new OperaSlot(semanticContext.getParams());
		Opera entity = null;
		
		if (slots.isEmpty()) {
			//随机挑选一首戏曲
			List<Integer> ids = operaService.getIdList();
			if(ids.size() > 0) {
				int idx = CommonUtils.randomInt(ids.size());
				entity = operaService.getById(ids.get(idx));	
			}
			else
			{
				errorCode = OperaError.ERROR_NO_RESOURCE;
			}
				
		}
		else
		{
			String name = slots.get(OperaSlot.OPERA_NAME);
			String actor = slots.get(OperaSlot.OPERA_ACTOR);			
			String category = slots.get(OperaSlot.OPERA_CATEGORY);
			
			
			OperaCategory targetCategory = null;			
			if(null != category) {
				List<OperaCategory> operaCategory = null;				
				operaCategory = categoryService.getByName(category);				
				if(null != operaCategory && operaCategory.size() > 0) {
					targetCategory = operaCategory.get(0);
				}
			}			
			
			
			if(null != name) {
				List<Opera> operas = null;
				operas = operaService.getByName(name);
				//根据戏曲名字查找
				if(null == operas || operas.isEmpty()) {
					errorCode = OperaError.ERROR_NO_RESOURCE;
				}
				else if(null != actor){
					//暂时没有戏曲演员
					errorCode = OperaError.ERROR_NO_RESOURCE;
				}
				else 
				{
					List<Opera> listOpera = new ArrayList<Opera>();
					if(targetCategory != null) {
						//验证戏曲类型
						for(Opera opera:operas){
							List<OperaTag> operaTag =  operaTagService.getByResourceId(opera.getId());	
							if(null == operaTag || operaTag.size() <= 0) {
								continue;
							}
							
							boolean find = false;
							for(OperaTag tag:operaTag) {
								if(tag.getTagId() == targetCategory.getId() && tag.getTagTypeId() == OperaTagType.TAG_OPERA_CATEGORY) {
									find = true;									
									break;
								}
							}
							
							if(find) {
								listOpera.add(opera);
							}
							
						}
						
					}
					else
					{
						listOpera.addAll(operas);
					}
					
					if(listOpera.size() > 0) {
						int idx = CommonUtils.randomInt(listOpera.size());
						entity = listOpera.get(idx);
					}
					else
					{
						errorCode = OperaError.ERROR_NO_RESOURCE;
					}
				}
			}
			else if(null != actor) {
				//根据演员查找
				errorCode = OperaError.ERROR_NO_RESOURCE;
			}
			else if(null != category) {
				//根据类别查找	
				List<Opera> operas = null;
				if(null != targetCategory) {
					operas = operaService.findByCategoryId(targetCategory.getId());
				}
				else
				{
					operas = operaService.findByCategoryName(category);
				}
				
				if(null == operas || operas.isEmpty() ) {
					errorCode = OperaError.ERROR_NO_RESOURCE;
				}
				else
				{						
					// 随机取
					int idx = CommonUtils.randomInt(operas.size());
					entity = operas.get(idx);
				}
			}
			
		}
		
		
		if(null != entity) {
			errorCode = OperaError.ERROR_SUCCESS;
		}
		
		
		OperaBean resultBean = null;		
		if (errorCode.equals(OperaError.ERROR_SUCCESS)) {
			ss.setId(entity.getId());
					
			resultBean = new OperaBean(null,entity.getResourceUrl(),entity);			
		}
		else
		{
			String msg = OperaError.getMsg(errorCode);
			resultBean = new OperaBean(errorCode,msg);			
		}
		
		
		return resultBean;
	}	
}
