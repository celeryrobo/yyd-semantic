package com.yyd.semantic.services.impl.calc;

import java.util.HashMap;
import java.util.Map;

public class CalcError {
	public static Integer ERROR_SUCCESS = 0;
	public static Integer ERROR_NO_RESOURCE = 1601;	
	public static Integer ERROR_UNKNOW_INTENT = 1602;
	public static Integer ERROR_EXPRESSION = 1603;
	public static Integer ERROR_CALC_FAIL = 1604;
	public static Integer ERROR_DIVISOR_ZERO = 1605;
		
	public static Map<Integer,String> mapMsg = new HashMap<Integer,String>();
	
	
	static {
		mapMsg.put(ERROR_SUCCESS,null);
		mapMsg.put(ERROR_NO_RESOURCE,"找不到相应资源");		
		mapMsg.put(ERROR_UNKNOW_INTENT,"未知的意图");	
		mapMsg.put(ERROR_EXPRESSION,"非法的数学表达式");	
		mapMsg.put(ERROR_CALC_FAIL,"计算失败");	
		mapMsg.put(ERROR_DIVISOR_ZERO,"除数可能为0");	
	}
	
	public static String getMsg(Integer errorCode) {
		if(errorCode.equals(ERROR_SUCCESS)) {
			return null;
		}
		
		String msg = mapMsg.get(errorCode);
		if(null == msg) {
			msg = "未知的错误码:"+errorCode;
		}
		
		return msg;
	}
}
