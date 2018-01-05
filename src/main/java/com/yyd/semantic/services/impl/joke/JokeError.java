package com.yyd.semantic.services.impl.joke;

import java.util.HashMap;
import java.util.Map;

public class JokeError {
	public static Integer ERROR_SUCCESS = 0;
	public static Integer ERROR_NO_RESOURCE = 901;	
	public static Integer ERROR_UNKNOW_INTENT = 902;
	
	public static Map<Integer,String> mapMsg = new HashMap<Integer,String>();
	
	
	static {
		mapMsg.put(ERROR_SUCCESS,null);
		mapMsg.put(ERROR_NO_RESOURCE,"找不到相应资源");		
		mapMsg.put(ERROR_UNKNOW_INTENT,"未知的意图");
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
