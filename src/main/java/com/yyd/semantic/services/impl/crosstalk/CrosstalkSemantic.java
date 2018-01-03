package com.yyd.semantic.services.impl.crosstalk;

import java.util.ArrayList;
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
import com.yyd.semantic.db.bean.crosstalk.Crosstalk;
import com.yyd.semantic.db.bean.crosstalk.CrosstalkActor;
import com.yyd.semantic.db.bean.crosstalk.CrosstalkTag;
import com.yyd.semantic.db.bean.crosstalk.CrosstalkTagType;
import com.yyd.semantic.db.service.crosstalk.ActorService;
import com.yyd.semantic.db.service.crosstalk.CrosstalkService;
import com.yyd.semantic.db.service.crosstalk.CrosstalkTagService;
import com.yyd.semantic.services.impl.crosstalk.CrosstalkBean;


@Component
public class CrosstalkSemantic implements Semantic<CrosstalkBean> {
	@Autowired
	private CrosstalkService crosstalkService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CrosstalkTagService crosstalkTagService;
	
	
	@Override
	public CrosstalkBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CrosstalkBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case CrosstalkIntent.QUERY_CROSSTALK: {
				result = queryCrosstalk(objects, semanticContext);
				break;
			}		
			default: {
				result = new CrosstalkBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	private CrosstalkBean queryCrosstalk(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		CrosstalkSlot ss = new CrosstalkSlot(semanticContext.getParams());
		Crosstalk entity = null;
		
		if (slots.isEmpty()) {
			//随机挑选一首相声
			List<Integer> crosstalkIds = crosstalkService.getIdList();
			if(crosstalkIds.size() > 0) {
				int idx = CommonUtils.randomInt(crosstalkIds.size());
				entity = crosstalkService.getById(crosstalkIds.get(idx));
			}
			
		}
		else {
			String name = slots.get(CrosstalkSlot.CROSSTALK_NAME);
			String actor = slots.get(CrosstalkSlot.CROSSTALK_ACTPR);			
			String category = slots.get(CrosstalkSlot.CROSSTALK_CATEGORY);
			
			CrosstalkActor targetActor = null;
			if(null != actor) {
				List<CrosstalkActor> actors = actorService.getByName(actor);
				if(null != actors && actors.size() > 0) {
					targetActor = actors.get(0);
				}
			}
			
			if(null != name) {
				List<Crosstalk> crosstalks = crosstalkService.getByName(name);
				if(null == crosstalks || crosstalks.isEmpty()) {
					result = "我没听过相声" + name;
				}
				else if(null != category) {
					result = "我没听过此类相声";
				}
				else
				{
					List<Crosstalk> crosstalkList = new ArrayList<Crosstalk>();
					if(null != targetActor) {
						for(Crosstalk crosstalk:crosstalks) {
							List<CrosstalkTag> tags = crosstalkTagService.getByResourceId(crosstalk.getId());
							if(null == tags || tags.size() < 0) {
								continue;
							}
							
							
							boolean find = false;
							for(CrosstalkTag tag:tags) {
								if(tag.getTagId() == targetActor.getId() && tag.getTagTypeId() == CrosstalkTagType.TAG_CROSSTALK_ACTOR) {
									find = true;
									break;
								}
							}
							
							if(find) {
								crosstalkList.add(crosstalk);
							}
						}
						
					}
					else
					{
						crosstalkList.addAll(crosstalks);
					}
					
					if(crosstalkList.size() > 0) {
						int idx = CommonUtils.randomInt(crosstalks.size());
						entity = crosstalks.get(idx);
					}
					else
					{
						result = "我还没听过此相声";
					}
				}
				
			}			
			else if(null != category) {		
				
				result = "我还没听过此类相声";
				
			}
			else if(null != actor) {
				List<Integer> acotrIds = actorService.getIdsByName(actor);
				int idx = CommonUtils.randomInt(acotrIds.size());
				Integer actorId = acotrIds.get(idx);
				List<Crosstalk> crosstalks = crosstalkService.findByActorId(actorId);
				if (crosstalks.isEmpty()) {
					result = "我没听过" + actor + "的相声";
				} else {
					idx = CommonUtils.randomInt(crosstalks.size());
					entity = crosstalks.get(idx);
				}
			}
			
			
			
		}
		
		
		CrosstalkBean resultBean = null;

		if (entity != null) {
			ss.setId(entity.getId());
			result = entity.getResourceUrl();
			
			resultBean = new CrosstalkBean(null,entity.getResourceUrl(),entity);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			resultBean = new CrosstalkBean(result);			
		}
		
		return resultBean;
	}	
}
