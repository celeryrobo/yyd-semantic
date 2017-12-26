package com.yyd.semantic.services.impl.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.services.impl.festival.DateEntity;
import com.yyd.semantic.services.impl.festival.DateUtil;

@Component
public class DateSemantic implements Semantic<DateBean> {

	@Override
	public DateBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		DateBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case DateIntent.QUERY_1: {
			result = query1(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_2: {
			result = query2(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_3: {
			result = query3(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_4: {
			result = query4(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_5: {
			result = query5(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_6: {
			result = query6(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_7: {
			result = query7(objects, semanticContext);
			break;
		}
		case DateIntent.QUERY_8: {
			result = query8(objects, semanticContext);
			break;
		}
		default:
			result = new DateBean("我不知道你在说什么");
			break;
		}
		return result;
	}

	private DateBean query1(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String[] days = SlotsData.DAY;
			String day = slots.get(DateSlot.DAY);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			StringBuffer answerText = new StringBuffer();
			DateEntity de = new DateEntity();
			if (day.equals(days[0])) {
				// 今天是什么时候 今天是2017年12月25日 丁酉年 十一月初八 星期一
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 0);
			} else if (day.equals(days[1])) {
				// 明天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			} else if (day.equals(days[2])) {
				// 后天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 2);
			} else if (day.equals(days[3])) {
				// 大后天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 3);
			} else if (day.equals(days[4])) {
				// 昨天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			} else if (day.equals(days[5])) {
				// 前天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 2);
			} else if (day.equals(days[6])) {
				// 大前天
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 3);
			} else {
				return new DateBean("你说什么");
			}

			date = cal.getTime();
			String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
			de = DateUtil.getDateEntityBySolar(str);
			answerText.append(day).append("是").append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
					.append(de.getWeekDate());
			return new DateBean(answerText.toString());

		}
		return new DateBean("不知道你说什么");
	}

	private DateBean query2(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String[] months = SlotsData.MONTH;
			String month = slots.get(DateSlot.MONTH);
			String d_day = slots.get(DateSlot.D_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (month != null && d_day != null && Integer.parseInt(d_day)<=31) {
				
				if (month.equals(months[0])) {
					// 这个月26号星期几 这个月26号是2017年12月26日 丁酉年 十一月初九 星期二
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 0);
				} else if (month.equals(months[1])) {
					// 下个月
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
				} else if (month.equals(months[2])) {
					// 上个月
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
				}
				cal.set(Calendar.DATE, Integer.parseInt(d_day));
				date = cal.getTime();
				String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
				de = DateUtil.getDateEntityBySolar(str);
				answerText.append(month).append(d_day).append("日").append("是").append(de.getCnDate()).append(" ")
						.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
			} else if (month == null && d_day != null && Integer.parseInt(d_day)<=31) {
				// 5号是什么时候 5号是2018年01月05日 丁酉年 十一月十九 星期五
				if (cal.get(Calendar.DATE) > Integer.parseInt(d_day)) {
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
				}
				cal.set(Calendar.DATE, Integer.parseInt(d_day));
				date = cal.getTime();
				String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
				de = DateUtil.getDateEntityBySolar(str);
				answerText.append(d_day).append("号").append("是").append(de.getCnDate()).append(" ")
						.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
			} else {
				return new DateBean("不知道你说什么");
			}
			return new DateBean(answerText.toString());
		}
		return new DateBean("不知道你说什么");
	}

	private DateBean query3(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String[] months = SlotsData.MONTH;
			String month = slots.get(DateSlot.MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (month != null && h_day != null) {
				int day = DateUtil.CN_NUMBER.get(h_day);
				if(day>31) {
					return new DateBean("不知道你说什么");
				}
				if (month == months[0]) {
					// 这个月五号星期几
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 0);
				} else if (month == months[1]) {
					// 下个月
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
				} else if (month == months[2]) {
					// 上个月
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
				}
				cal.set(Calendar.DATE, day);
				date = cal.getTime();
				String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
				de = DateUtil.getDateEntityBySolar(str);
				answerText.append(month).append(h_day).append("日").append("是").append(de.getCnDate()).append(" ")
						.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
			} else if (month == null && h_day != null) {
				// 五号是什么时候
				int day = DateUtil.CN_NUMBER.get(h_day);
				if(day>31) {
					return new DateBean("不知道你说什么");
				}
				if (cal.get(Calendar.DATE) > day) {
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
				}
				cal.set(Calendar.DATE, day);
				date = cal.getTime();
				String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
				de = DateUtil.getDateEntityBySolar(str);
				answerText.append(h_day).append("号").append("是").append(de.getCnDate()).append(" ")
						.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
			} else {
				return new DateBean("不知道你说什么");
			}
		}
		return new DateBean("不知道你说什么");
	}

	private DateBean query4(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String d_year = slots.get(DateSlot.D_YEAR);
			String d_month = slots.get(DateSlot.D_MONTH);
			String d_day = slots.get(DateSlot.D_DAY);
			if (d_year != null && d_month != null && d_day != null) {
				// 2012年12月10号是什么时候

			} else if (d_year == null && d_month != null && d_day != null) {
				// 11月12号是什么时候

			} else {
				return new DateBean("不知道你说什么");
			}
		}
		return new DateBean("不知道你说什么");
	}

	private DateBean query5(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String h_year = slots.get(DateSlot.YEAR);
			String h_month = slots.get(DateSlot.H_MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			if (h_year != null && h_month != null && h_day != null) {
				// 今年十二月十号是什么时候

			} else if (h_month != null && h_day != null) {
				// 十二月十号是什么时候

			}
		}
		return null;
	}

	private DateBean query6(Map<String, String> slots, SemanticContext semanticContext) {
		// 现在几点了
		StringBuffer answerText = new StringBuffer();
		Date date = new Date();
		String str1 = (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
		String str2 = (new SimpleDateFormat("HH:mm:ss")).format(date);
		String str3 = (new SimpleDateFormat("EEEE")).format(date);
		answerText.append("现在是").append(str1).append(" ").append(str3).append(" ").append(str2);
		return new DateBean(answerText.toString());
	}

	private DateBean query7(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(DateSlot.YEAR);
			String d_year = slots.get(DateSlot.D_YEAR);
			String d_month = slots.get(DateSlot.D_MONTH);
			String d_day = slots.get(DateSlot.D_DAY);
			if (year == null && d_year != null && d_month != null && d_day != null) {
				// 距离2012年12月10号还有多久

			} else if (year == null && d_year == null && d_month != null && d_day != null) {
				// 距离12月10号还有多久

			} else if (year != null && d_year == null && d_month != null && d_day != null) {
				// 距离今年12月10号还有多久
			}
		}
		return null;
	}

	private DateBean query8(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(DateSlot.YEAR);
			String h_month = slots.get(DateSlot.H_MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			if (year != null && h_month != null && h_day != null) {
				// 距离今年十二月十号还有多久

			} else if (year == null && h_month != null && h_day != null) {
				// 距离十二月十号还有多久

			}
		}
		return null;
	}
}
