package com.yyd.semantic.services.impl.date;

import java.util.HashMap;
import java.util.Map;

public class SlotsData {
	public static final String[] DAY1 = { "今天", "明天", "后天", "大后天", "昨天", "前天", "大前天" };
	public static final String[] YEAR1 = { "今年", "明年", "后年", "大后年", "去年", "前年", "大前年" };
	public static final String[] MONTH1 = { "这个月", "下个月", "上个月" };

	public static final Map<String, Integer> MONTH = new HashMap<>();
	static {
		MONTH.put("这个月", 0);
		MONTH.put("下个月", 1);
		MONTH.put("上个月", 2);
	}
	public static final Map<String, Integer> YEAR = new HashMap<>();
	static {
		YEAR.put("今年", 0);
		YEAR.put("明年", 1);
		YEAR.put("后年", 2);
		YEAR.put("大后年", 3);
		YEAR.put("去年", -1);
		YEAR.put("前年", -2);
		YEAR.put("大前年", -3);
	}
	
	public static final Map<String, Integer> DAY = new HashMap<>();
	static {
		DAY.put("今天", 0);
		DAY.put("明天", 1);
		DAY.put("后天", 2);
		DAY.put("大后天", 3);
		DAY.put("昨天", -1);
		DAY.put("前天", -2);
		DAY.put("大前天", -3);
	}
	
	public static final Map<String, Integer> CN_NUMBER = new HashMap<>();
	static {
		CN_NUMBER.put("一", 1);
		CN_NUMBER.put("二", 2);
		CN_NUMBER.put("三", 3);
		CN_NUMBER.put("四", 4);
		CN_NUMBER.put("五", 5);
		CN_NUMBER.put("六", 6);
		CN_NUMBER.put("七", 7);
		CN_NUMBER.put("八", 8);
		CN_NUMBER.put("九", 9);
		CN_NUMBER.put("十", 10);
		CN_NUMBER.put("十一", 11);
		CN_NUMBER.put("十二", 12);
		CN_NUMBER.put("十三", 13);
		CN_NUMBER.put("十四", 14);
		CN_NUMBER.put("十五", 15);
		CN_NUMBER.put("十六", 16);
		CN_NUMBER.put("十七", 17);
		CN_NUMBER.put("十八", 18);
		CN_NUMBER.put("十九", 19);
		CN_NUMBER.put("二十", 20);
		CN_NUMBER.put("二十一", 21);
		CN_NUMBER.put("二十二", 22);
		CN_NUMBER.put("二十三", 23);
		CN_NUMBER.put("二十四", 24);
		CN_NUMBER.put("二十五", 25);
		CN_NUMBER.put("二十六", 26);
		CN_NUMBER.put("二十七", 27);
		CN_NUMBER.put("二十八", 28);
		CN_NUMBER.put("二十九", 29);
		CN_NUMBER.put("三十", 30);
	}

}
