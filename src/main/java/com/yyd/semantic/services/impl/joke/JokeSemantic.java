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
				result = new JokeBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	private JokeBean queryJoke(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
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
					result = "我还没听过这个类型的笑话";					
				}
				else
				{						
					// 随机获取
					int idx = CommonUtils.randomInt(operas.size());
					entity = operas.get(idx);
				}
			}
			
		}
		
		
		JokeBean resultBean = null;
		
		if (entity != null) {
			ss.setId(entity.getId());
			result = entity.getResourceUrl();
			
			resultBean = new JokeBean(entity.getContent());
			resultBean.setOperation(Operation.SPEAK);
			resultBean.setParamType(ParamType.T);
		}
		else
		{
			resultBean = new JokeBean(result);			
		}
		
		
		return resultBean;
	}
}
