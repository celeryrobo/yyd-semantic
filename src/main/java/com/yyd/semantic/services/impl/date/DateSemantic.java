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
	public static final Integer SEMANTIC_SUCCESS = 0;
	public static final Integer SEMANTIC_FAILURE = 1101;
	public static final String ERRO_ANSWER = "不好意思，我好像没听懂。。。";
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
			result = new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
			break;
		}
		return result;
	}

	private DateBean query1(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String day = slots.get(DateSlot.DAY);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			StringBuffer answerText = new StringBuffer();
			DateEntity de = new DateEntity();
			if (day != null) {
				int d = SlotsData.DAY.get(day);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + d);
			}
			date = cal.getTime();
			String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
			de = DateUtil.getDateEntityBySolar(str);
			answerText.append(day).append("是").append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
					.append(de.getWeekDate());
			return new DateBean(answerText.toString());
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}

	private DateBean query2(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String month = slots.get(DateSlot.MONTH);
			String d_day = slots.get(DateSlot.D_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (d_day != null && Integer.parseInt(d_day) <= 31) {
				int d = Integer.parseInt(d_day);
				if (month != null) {
					int m = SlotsData.MONTH.get(month);
					// 这个月26号星期几 这个月26号是2017年12月26日 丁酉年 十一月初九 星期二
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + m);
					cal.set(Calendar.DATE, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					answerText.append(month).append(d_day).append("日").append("是").append(de.getCnDate()).append(" ")
							.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
					return new DateBean(answerText.toString());
				} else {
					// 5号是什么时候 5号是2018年01月05日 丁酉年 十一月十九 星期五
					if (cal.get(Calendar.DATE) > d) {
						cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
					}
					cal.set(Calendar.DATE, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					answerText.append(d_day).append("号").append("是").append(de.getCnDate()).append(" ")
							.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
					return new DateBean(answerText.toString());
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}

	private DateBean query3(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String month = slots.get(DateSlot.MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (h_day != null) {
				int d = SlotsData.CN_NUMBER.get(h_day);
				if (d < 31) {
					if (month != null) {
						// 这个月五号星期几
						int m = SlotsData.MONTH.get(month);
						cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + m);
						cal.set(Calendar.DATE, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						answerText.append(month).append(h_day).append("日").append("是").append(de.getCnDate()).append(" ")
								.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
						return new DateBean(answerText.toString());
					} else {
						// 五号星期几
						cal.set(Calendar.DATE, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						answerText.append(h_day).append("号").append("是").append(de.getCnDate()).append(" ")
								.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
						return new DateBean(answerText.toString());
					}
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}

	private DateBean query4(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(DateSlot.YEAR);
			String d_year = slots.get(DateSlot.D_YEAR);
			String d_month = slots.get(DateSlot.D_MONTH);
			String d_day = slots.get(DateSlot.D_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (d_month != null && d_day != null) {
				int m = Integer.parseInt(d_month);
				int d = Integer.parseInt(d_day);
				if (m > 0 && m <= 12 && d > 0 && d <= 31) {
					if (d_year != null) {
						// 2018年01月05日是什么时候 2018年01月05日是2018年01月05日 丁酉年 十一月十九 星期五
						int y = Integer.parseInt(d_year);
						cal.set(y, m - 1, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						answerText.append(d_year).append("年").append(d_month).append("月").append(d_day).append("日")
								.append("是").append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate());
						return new DateBean(answerText.toString());
					} else if (d_year == null && year == null) {
						// 11月12号是什么时候
						cal.set(Calendar.MONTH, m - 1);
						cal.set(Calendar.DATE, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						answerText.append(d_month).append("月").append(d_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate());
						return new DateBean(answerText.toString());
					} else if (d_year == null && year != null) {
						if (SlotsData.YEAR.get(year) != null) {
							int y = SlotsData.YEAR.get(year);
							cal.set(cal.get(Calendar.YEAR) + y, m - 1, d);
							date = cal.getTime();
							String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
							de = DateUtil.getDateEntityBySolar(str);
							answerText.append(year).append(d_month).append("月").append(d_day).append("日").append("是")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate());
							return new DateBean(answerText.toString());
						}
					}
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}

	private DateBean query5(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(DateSlot.YEAR);
			String h_month = slots.get(DateSlot.H_MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (h_month != null && h_day != null) {
				int m = SlotsData.CN_NUMBER.get(h_month);
				int d = SlotsData.CN_NUMBER.get(h_day);
				if (year != null) {
					// 今年十二月十号是什么时候
					if (SlotsData.YEAR.get(year) != null) {
						int y = SlotsData.YEAR.get(year);
						cal.set(cal.get(Calendar.YEAR) + y, m - 1, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						answerText.append(year).append(h_month).append("月").append(h_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate());
						return new DateBean(answerText.toString());
					}
				} else {
					// 十二月十号是什么时候
					cal.set(cal.get(Calendar.YEAR), m - 1, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					answerText.append(h_month).append("月").append(h_day).append("日").append("是").append(de.getCnDate())
							.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate());
					return new DateBean(answerText.toString());
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
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
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (d_month != null && d_day != null) {
				int m = Integer.parseInt(d_month);
				int d = Integer.parseInt(d_day);
				if (d_year != null) {
					int y = Integer.parseInt(d_year);
					// 距离2012年12月10号还有多久
					cal.set(y, m - 1, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					int days = DateUtil.getDifferentDays(new Date(), date);
					if (days == 0) {
						answerText.append("今天就是");
					} else if (days > 0) {
						answerText.append(d_year).append("年").append(d_month).append("月").append(d_day).append("日")
								.append("是").append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("还有").append(days).append("天");
					} else {
						days = Math.abs(days);
						answerText.append(d_year).append("年").append(d_month).append("月").append(d_day).append("日")
								.append("是").append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("已经过去").append(days).append("天");
					}
					return new DateBean(answerText.toString());
				} else {
					if (year == null) {
						// 距离12月10号还有多久
						cal.set(Calendar.MONTH, m - 1);
						cal.set(Calendar.DATE, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						int days = DateUtil.getDifferentDays(new Date(), date);
						if (days == 0) {
							answerText.append("今天就是");
						} else if (days > 0) {
							answerText.append(d_month).append("月").append(d_day).append("日").append("是")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate()).append("还有").append(days).append("天");
						} else {
							days = Math.abs(days);
							answerText.append(d_month).append("月").append(d_day).append("日").append("是")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate()).append("已经过去").append(days).append("天");
						}
						return new DateBean(answerText.toString());
					} else {
						// 距离今年12月10号还有多久
						int y = SlotsData.YEAR.get(year);
						cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + y);
						cal.set(Calendar.MONTH, m - 1);
						cal.set(Calendar.DATE, d);
						date = cal.getTime();
						String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
						de = DateUtil.getDateEntityBySolar(str);
						int days = DateUtil.getDifferentDays(new Date(), date);
						if (days == 0) {
							answerText.append("今天就是");
						} else if (days > 0) {
							answerText.append(year).append(d_month).append("月").append(d_day).append("日").append("是")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate()).append("还有").append(days).append("天");
						} else {
							days = Math.abs(days);
							answerText.append(year).append(d_month).append("月").append(d_day).append("日").append("是")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate()).append("已经过去").append(days).append("天");
						}
						return new DateBean(answerText.toString());
					}
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}

	private DateBean query8(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(DateSlot.YEAR);
			String h_month = slots.get(DateSlot.H_MONTH);
			String h_day = slots.get(DateSlot.H_DAY);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateEntity de = new DateEntity();
			if (h_month != null && h_day != null) {
				int m = SlotsData.CN_NUMBER.get(h_month);
				int d = SlotsData.CN_NUMBER.get(h_day);
				if (year != null) {
					// 距离今年十二月十号还有多久
					int y = SlotsData.YEAR.get(year);
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + y);
					cal.set(Calendar.MONTH, m - 1);
					cal.set(Calendar.DATE, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					int days = DateUtil.getDifferentDays(new Date(), date);

					if (days == 0) {
						answerText.append("今天就是");
					} else if (days > 0) {
						answerText.append(year).append(h_month).append("月").append(h_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("还有").append(days).append("天");
					} else {
						days = Math.abs(days);
						answerText.append(year).append(h_month).append("月").append(h_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("已经过去").append(days).append("天");
					}
					return new DateBean(answerText.toString());

				} else {
					// 距离十二月十号还有多久
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 0);
					cal.set(Calendar.MONTH, m - 1);
					cal.set(Calendar.DATE, d);
					date = cal.getTime();
					String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
					de = DateUtil.getDateEntityBySolar(str);
					int days = DateUtil.getDifferentDays(new Date(), date);

					if (days == 0) {
						answerText.append("今天就是");
					} else if (days > 0) {
						answerText.append(year).append(h_month).append("月").append(h_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("还有").append(days).append("天");
					} else {
						days = Math.abs(days);
						answerText.append(year).append(h_month).append("月").append(h_day).append("日").append("是")
								.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
								.append(de.getWeekDate()).append("已经过去").append(days).append("天");
					}
					return new DateBean(answerText.toString());
				}
			}
		}
		return new DateBean(ERRO_ANSWER,SEMANTIC_FAILURE);
	}
}
