package com.yyd.semantic.services.impl.phonenumber;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.phonenumber.PhoneNumber;
import com.yyd.semantic.db.service.phonenumber.PhoneNumberService;



@Component
public class PhoneNumberSemantic implements Semantic<PhoneNumberBean> {
	@Autowired
	private PhoneNumberService phoneNumberService;
	
	@Override
	public PhoneNumberBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PhoneNumberBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case PhoneNumberIntent.QUERY_NUMBER: {
				result = queryNumber(objects, semanticContext);
				break;
			}	
			case PhoneNumberIntent.QUERY_NAME: {
				result = queryName(objects, semanticContext);
				break;
			}	
			default: {
				String msg = PhonenumberError.getMsg(PhonenumberError.ERROR_UNKNOW_INTENT);
				result = new PhoneNumberBean(PhonenumberError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
		return result;
	}
	
	private PhoneNumberBean queryNumber(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = PhonenumberError.ERROR_NO_RESOURCE;
		
		PhoneNumberSlot ss = new PhoneNumberSlot(semanticContext.getParams());
		PhoneNumber entity = null;
		
		String name = slots.get(PhoneNumberSlot.PHONENUMBER_NAME);
		if(null != name) {
			List<PhoneNumber> numbers = phoneNumberService.findByName(name);
			
			if(null == numbers || numbers.isEmpty()) {
				errorCode = PhonenumberError.ERROR_NO_RESOURCE;
			}
			else
			{
				int idx = CommonUtils.randomInt(numbers.size());
				entity = numbers.get(idx);
			}
		}
		
		if(null != entity) {
			errorCode = PhonenumberError.ERROR_SUCCESS;
		}
		
		
		PhoneNumberBean resultBean = null;		
		if (errorCode.equals(PhonenumberError.ERROR_SUCCESS)) {
			ss.setId(entity.getId());
			String result = entity.getNumber();
			
			resultBean = new PhoneNumberBean(result);			
		}
		else
		{
			String msg = PhonenumberError.getMsg(errorCode);
			resultBean = new PhoneNumberBean(errorCode,msg);			
		}
		
		
		return resultBean;
	}
	
	private PhoneNumberBean queryName(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = PhonenumberError.ERROR_NO_RESOURCE;
		
		PhoneNumberSlot ss = new PhoneNumberSlot(semanticContext.getParams());
		PhoneNumber entity = null;
		
		String number = slots.get(PhoneNumberSlot.PHONENUMBER_NUMBER);
		if(null != number) {
			List<PhoneNumber> numbers = phoneNumberService.getByNumber(number);
			
			if(null == numbers || numbers.isEmpty()) {
				errorCode = PhonenumberError.ERROR_NO_RESOURCE;
			}
			else
			{
				int idx = CommonUtils.randomInt(numbers.size());
				entity = numbers.get(idx);
			}
		}
		
		if(null != entity) {
			errorCode = PhonenumberError.ERROR_SUCCESS;
		}
		
		PhoneNumberBean resultBean = null;		
		if (errorCode.equals(PhonenumberError.ERROR_SUCCESS)){
			ss.setId(entity.getId());
			String result = entity.getName();
			
			resultBean = new PhoneNumberBean(result);			
		}
		else
		{
			String msg = PhonenumberError.getMsg(errorCode);
			resultBean = new PhoneNumberBean(errorCode,msg);
		}
		
		
		return resultBean;
	}
	
}
