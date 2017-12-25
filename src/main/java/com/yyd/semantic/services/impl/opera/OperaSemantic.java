package com.yyd.semantic.services.impl.opera;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.beans.AbstractSemanticResult.Operation;
import com.ybnf.compiler.beans.AbstractSemanticResult.ParamType;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.opera.Opera;
import com.yyd.semantic.db.service.opera.OperaCategoryService;
import com.yyd.semantic.db.service.opera.OperaService;



@Component
public class OperaSemantic implements Semantic<OperaBean> {
	@Autowired
	private OperaCategoryService categoryService;
	@Autowired
	private OperaService operaService;	
	
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
				result = new OperaBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	private OperaBean queryOpera(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		OperaSlot ss = new OperaSlot(semanticContext.getParams());
		Opera entity = null;
		
		if (slots.isEmpty()) {
			//随机挑选一首戏曲
			List<Integer> ids = operaService.getIdList();
			if(ids.size() > 0) {
				int idx = CommonUtils.randomInt(ids.size());
				entity = operaService.getById(ids.get(idx));	
			}
				
		}
		else
		{
			String name = slots.get(OperaSlot.OPERA_NAME);
			String actor = slots.get(OperaSlot.OPERA_ACTOR);			
			String category = slots.get(OperaSlot.OPERA_CATEGORY);
			
			if(null != name) {
				//根据戏曲名字查找
				List<Opera> operas = operaService.getByName(name);
				if(null == operas || operas.isEmpty()) {
					result = "我没听过戏曲" + name;
				}
				else
				{
					int idx = CommonUtils.randomInt(operas.size());
					entity = operas.get(idx);
				}
			}
			else if(null != actor) {
				//根据演员查找
				result = "我还没听过" + actor + "的戏曲";
			}
			else if(null != category) {
				//根据类别查找
				List<Opera> operas = operaService.findByCategoryName(category);
				if(null == operas || operas.isEmpty() ) {
					result = "我还没听过这个类型的戏曲";
				}
				else
				{						
					// 随机取
					int idx = CommonUtils.randomInt(operas.size());
					entity = operas.get(idx);
				}
			}
			
		}
		
		
		OperaBean resultBean = null;
		
		if (entity != null) {
			ss.setId(entity.getId());
			result = entity.getResourceUrl();
			
			resultBean = new OperaBean(null,entity.getResourceUrl(),entity);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			resultBean = new OperaBean(result);			
		}
		
		
		return resultBean;
	}	
}
