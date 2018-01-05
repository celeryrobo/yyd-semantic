package com.yyd.semantic.services.impl.joke;

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
import com.yyd.semantic.db.bean.joke.Joke;
import com.yyd.semantic.db.service.joke.JokeService;



@Component
public class JokeSemantic implements Semantic<JokeBean>{
	@Autowired
	private JokeService jokeService;	
	
	@Override
	public JokeBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		JokeBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case JokeIntent.QUERY_JOKE: {
				result = queryJoke(objects, semanticContext);
				break;
			}		
			default: {
				String msg = JokeError.getMsg(JokeError.ERROR_UNKNOW_INTENT);
				result = new JokeBean(JokeError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
		return result;
	}
	
	private JokeBean queryJoke(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = JokeError.ERROR_NO_RESOURCE;
		
		JokeSlot ss = new JokeSlot(semanticContext.getParams());
		Joke entity = null;
		
		if (slots.isEmpty()) {
			//随机挑选一首戏曲
			List<Integer> ids = jokeService.getIdList();
			if(ids.size() > 0) {
				int idx = CommonUtils.randomInt(ids.size());
				entity = jokeService.getById(ids.get(idx));	
			}			

		}
		else
		{
			String category = slots.get(JokeSlot.JOKE_CATEGORY);
			
			if(null != category) {
				//根据类别查找
				List<Joke> operas = jokeService.findByCategoryName(category);
				if(null == operas || operas.isEmpty() ) {
					errorCode = JokeError.ERROR_NO_RESOURCE;				
				}
				else
				{						
					// 随机获取
					int idx = CommonUtils.randomInt(operas.size());
					entity = operas.get(idx);
				}
			}
			
		}
		
		if(null != entity) {
			errorCode = JokeError.ERROR_SUCCESS;
		}
		
		
		JokeBean resultBean = null;		
		if (errorCode.equals(JokeError.ERROR_SUCCESS)) {
			ss.setId(entity.getId());
						
			resultBean = new JokeBean(entity.getContent());
			resultBean.setOperation(Operation.SPEAK);
			resultBean.setParamType(ParamType.T);
		}
		else
		{
			String msg = JokeError.getMsg(errorCode);
			resultBean = new JokeBean(errorCode,msg);			
		}
		
		
		return resultBean;
	}
}
