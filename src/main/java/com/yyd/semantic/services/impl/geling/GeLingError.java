package com.yyd.semantic.services.impl.geling;

import java.util.HashMap;
import java.util.Map;

public class GeLingError {
	public static Integer ERROR_SUCCESS = 0;
	public static Integer ERROR_NO_RESOURCE = 1201;	
	public static Integer ERROR_UNKNOW_INTENT = 1202;
	public static Integer ERROR_UNKNOW_PARAM = 1203;
	
	public static Map<Integer,String> mapMsg = new HashMap<Integer,String>();
	
	
	static {
		mapMsg.put(ERROR_SUCCESS,null);
		mapMsg.put(ERROR_NO_RESOURCE,"找不到相应资源");		
		mapMsg.put(ERROR_UNKNOW_INTENT,"未知的意图");
		mapMsg.put(ERROR_UNKNOW_PARAM,"未知的参数");
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
