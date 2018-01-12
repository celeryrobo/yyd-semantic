package com.yyd.semantic.services.impl.geling;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;


@Component
public class GeLingSemantic implements Semantic<GeLingBean>{
	@Override
	public GeLingBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		GeLingBean result = null;
		Integer errorCode = GeLingError.ERROR_SUCCESS;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String intent = slots.get("intent");
		String param = slots.get("param");
		String hanziParam = null;
		String module = null;
		
		if(null != intent) {
			module = GeLingIntent.get(intent);
			if(null != module) {
				if(null != param) {
					hanziParam = GeLingSlot.get(param);
					if(null == hanziParam) {
						errorCode = GeLingError.ERROR_UNKNOW_PARAM;
					}
				}
			}
			else
			{
				errorCode = GeLingError.ERROR_UNKNOW_INTENT;
			}
		}
		else
		{
			errorCode = GeLingError.ERROR_UNKNOW_INTENT;
		}
				
		if(errorCode.equals(GeLingError.ERROR_SUCCESS)) {
			result = new GeLingBean(module,hanziParam);
		}
		else
		{
			String msg = GeLingError.getMsg(errorCode);
			result = new GeLingBean(errorCode,msg);
		}
		
		
		return result;
	}
}
