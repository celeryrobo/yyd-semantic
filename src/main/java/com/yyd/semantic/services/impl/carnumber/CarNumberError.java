package com.yyd.semantic.services.impl.carnumber;

import java.util.HashMap;
import java.util.Map;

public class CarNumberError {
	public static Integer ERROR_SUCCESS = 0;
	public static Integer ERROR_NO_RESOURCE = 1401;	
	public static Integer ERROR_UNKNOW_INTENT = 1402;
	public static Integer ERROR_REGION_NAME_ERROR = 1403;
	public static Integer ERROR_NO_SLOG_DATA = 1404;
	
	public static Map<Integer,String> mapMsg = new HashMap<Integer,String>();
	
	
	static {
		mapMsg.put(ERROR_SUCCESS,null);
		mapMsg.put(ERROR_NO_RESOURCE,"找不到相应资源");		
		mapMsg.put(ERROR_UNKNOW_INTENT,"未知的意图");
		mapMsg.put(ERROR_REGION_NAME_ERROR,"地名错误或未知的地名");
		mapMsg.put(ERROR_NO_SLOG_DATA,"缺少语义槽数据");
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
