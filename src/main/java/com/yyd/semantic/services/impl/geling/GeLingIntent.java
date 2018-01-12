package com.yyd.semantic.services.impl.geling;

import java.util.HashMap;
import java.util.Map;

public class GeLingIntent {
	/**
	 * 意图，汉字拼音-->汉字
	 */
	static Map<String,String> intents =new  HashMap<String,String>();
	
	static {
		intents.put("shijuekongjian", "视觉空间");
		intents.put("xiguanyangcheng", "习惯养成");
		intents.put("ziranzhishi", "自然知识");
		intents.put("shuliluoji", "数理逻辑");
		intents.put("ergetiandi", "儿歌天地");
		intents.put("guishixiaowu", "故事小屋");
		intents.put("kuaileguoxue", "快乐国学");
		intents.put("yuyanqimeng", "语言启蒙");
		intents.put("xiaoxuejiaoyu", "小学教育");
		intents.put("chuzhongjiaoyu", "初中教育");
		intents.put("gaozhongjiaoyu", "高中教育");
	}

	public static String get(String pinyin) {
		String hanzi = intents.get(pinyin);
		return hanzi;
	}
}
