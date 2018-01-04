package com.yyd.semantic.services.impl.festival;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.DbSegLoader;
import com.yyd.semantic.common.DbSegLoader.Item;
import com.yyd.semantic.db.bean.festival.Festival;
import com.yyd.semantic.db.service.festival.FestivalService;
import com.yyd.semantic.services.impl.date.SlotsData;
import com.yyd.semantic.services.impl.festival.FestivalBean;

@Component
public class FestivalSemantic implements Semantic<FestivalBean> {
	public static final Integer SEMANTIC_SUCCESS = 0;
	public static final Integer SEMANTIC_FAILURE = 301;
	public static final String ERRO_ANSWER = "不好意思，我好像没听懂。。。";
	public static final Integer SOLAR_FESTIVAL = 1;
	public static final Integer LUNAR_FESTIVAL = 2;
	public static final Integer OTHER_FESTIVAL = 3;
	@Autowired
	private FestivalService fs;

	@Autowired
	private DbSegLoader dbSegLoader;
	Calendar cal = Calendar.getInstance();

	@Override
	public FestivalBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		FestivalBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case FestivalIntent.QUERY_FESTIVAL_NAME_BY_SOLAR: {
			result = query1(objects, semanticContext, SOLAR_FESTIVAL);
			break;
		}
		case FestivalIntent.QUERY_FESTIVAL_NAME_BY_LUNAR: {
			result = query2(objects, semanticContext, LUNAR_FESTIVAL);
			break;
		}
		case FestivalIntent.QUERY_DATE: {
			result = query3(objects, semanticContext);
			break;
		}
		case FestivalIntent.QUERY_DISTANCE_TIME: {
			result = query4(objects, semanticContext);
			break;
		}
		default:
			result = new FestivalBean(ERRO_ANSWER, SEMANTIC_SUCCESS);
			break;
		}
		return result;
	}

	private FestivalBean query1(Map<String, String> slots, SemanticContext semanticContext, int dateCode) {
		if (!slots.isEmpty()) {
			String d_month = slots.get(FestivalSlot.D_MONTH);
			String d_day = slots.get(FestivalSlot.D_DAY);
			List<Festival> festival = null;
			StringBuffer answerText = new StringBuffer();
			if (d_month != null) {
				if (d_day != null) {
					// 9月10号是什么节日 教师节
					festival = fs.getNameByMonthAndDay(d_month, d_day, dateCode);
					if (festival != null) {
						int len = festival.size();
						if (len == 1) {
							answerText.append("是").append(festival.get(0).getName());
							return new FestivalBean(answerText.toString(), festival);
						} else if (len > 1) {
							answerText.append("有");
							for (int i = 0; i < len; i++) {
								answerText.append(festival.get(i).getName());
								if (i != len - 1) {
									answerText.append(",");
								}
							}
							return new FestivalBean(answerText.toString(), festival);
						}
					}
				} else {
					// 9月份有什么节日 教师节
					festival = fs.getNameByMonth(d_month, dateCode);
					if (festival != null) {
						answerText.append("有");
						for (int i = 0; i < festival.size(); i++) {
							answerText.append(festival.get(i).getName());
							if (i != festival.size() - 1) {
								answerText.append(",");
							}
						}
						return new FestivalBean(answerText.toString(), festival);
					}
				}
			}
		}
		return new FestivalBean(ERRO_ANSWER, SEMANTIC_FAILURE);
	}

	private FestivalBean query2(Map<String, String> slots, SemanticContext semanticContext, int dateCode) {
		if (!slots.isEmpty()) {
			String h_month = slots.get(FestivalSlot.H_MONTH);
			String h_day = slots.get(FestivalSlot.H_DAY);
			List<Festival> festival = null;
			StringBuffer answerText = new StringBuffer();
			if (h_month != null) {
				if (h_day != null) {
					// 农历八月十五是什么节日
					festival = fs.getNameByMonthAndDay(h_month, h_day, dateCode);
					if (festival != null) {
						int len = festival.size();
						if (len == 1) {
							answerText.append("是").append(festival.get(0).getName());
							return new FestivalBean(answerText.toString(), festival);
						} else if (len > 1) {
							answerText.append("有");
							for (int i = 0; i < len; i++) {
								answerText.append(festival.get(i).getName());
								if (i != len - 1) {
									answerText.append(",");
								}
							}
							return new FestivalBean(answerText.toString(), festival);
						}
					}
				} else {
					// 农历八月份有什么节日
					festival = fs.getNameByMonth(h_month, dateCode);
					if (festival != null) {
						answerText.append("有");
						for (int i = 0; i < festival.size(); i++) {
							answerText.append(festival.get(i).getName());
							if (i != festival.size() - 1) {
								answerText.append(",");
							}
						}
						return new FestivalBean(answerText.toString(), festival);
					}
				}
			}
		}
		return new FestivalBean(ERRO_ANSWER, SEMANTIC_FAILURE);
	}

	private FestivalBean query3(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(FestivalSlot.YEAR);
			String d_year = slots.get(FestivalSlot.D_YEAR);
			String name = slots.get(FestivalSlot.NAME);
			String term = slots.get(FestivalSlot.SOLAR_TERM);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			cal.setTime(date);
			Festival festival = null;
			DateEntity de = new DateEntity();
			if (name != null) {
				Item item = dbSegLoader.getItem(name);
				Set<String> items = null;
				if (item == null) {
					items = new TreeSet<>();
					items.add(name);
				} else {
					items = item.getItems();
				}
				for (String it : items) {
					if (fs.getDateByName(it) != null) {
						festival = fs.getDateByName(it);
						break;
					}
				}
				if (festival != null) {
					if (festival.getDateCode() == OTHER_FESTIVAL) {
						answerText.append(name).append("是").append(festival.getDes());
						return new FestivalBean(answerText.toString(), null);
					}
					if (d_year != null) {
						int y = Integer.parseInt(d_year);
						if (festival.getDateCode() == SOLAR_FESTIVAL) {
							// 2017年的国庆节是哪一天
							int m = Integer.parseInt(festival.getMonth());
							int d = Integer.parseInt(festival.getDay());
							cal.set(y, m - 1, d);
							date = cal.getTime();
							String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
							de = DateUtil.getDateEntityBySolar(str);
							answerText.append(d_year).append("年").append(name).append("是").append(" ")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate());
							return new FestivalBean(answerText.toString(), festival);

						} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
							// 2017年的清明节是哪一天
							String enDate = d_year + "-" + festival.getEnDate();
							String str = DateUtil.getEnDate(enDate);
							de = DateUtil.getDateEntityBySolar(str);
							answerText.append(d_year).append("年").append(name).append("是").append(" ")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate());
							return new FestivalBean(answerText.toString(), festival);
						}
					} else {
						if (year != null) {
							int y = SlotsData.YEAR.get(year);
							cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + y);
							if (festival.getDateCode() == SOLAR_FESTIVAL) {
								// 今年的国庆节是哪一天
								int m = Integer.parseInt(festival.getMonth());
								int d = Integer.parseInt(festival.getDay());
								cal.set(Calendar.MONTH, m - 1);
								cal.set(Calendar.DATE, d);
								date = cal.getTime();
								String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str);
								answerText.append(year).append("年").append(name).append("是").append(" ")
										.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
										.append(de.getWeekDate());
								return new FestivalBean(answerText.toString(), festival);

							} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
								// 今年中秋节是哪一天
								String yyyy = String.valueOf(cal.get(Calendar.YEAR));
								String enDate = yyyy + "-" + festival.getEnDate();
								String str = DateUtil.getEnDate(enDate);
								de = DateUtil.getDateEntityBySolar(str);
								answerText.append(year).append(name).append("是").append(" ").append(de.getCnDate())
										.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate());
								return new FestivalBean(answerText.toString(), festival);
							}
						} else {
							if (festival.getDateCode() == SOLAR_FESTIVAL) {
								// 国庆节是哪一天
								int y = cal.get(Calendar.YEAR);
								int m1 = Integer.parseInt(festival.getMonth());
								int d1 = Integer.parseInt(festival.getDay());
								cal.set(Calendar.MONTH, m1 - 1);
								cal.set(Calendar.DATE, d1);
								date = cal.getTime();
								String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									y = y + 1;
									int m = Integer.parseInt(festival.getMonth());
									int d = Integer.parseInt(festival.getDay());
									cal.set(Calendar.YEAR, y);
									cal.set(Calendar.MONTH, m - 1);
									cal.set(Calendar.DATE, d);
									date = cal.getTime();
									String str2 = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
									int day = DateUtil.getDifferentDays(new Date(), date);
									de = DateUtil.getDateEntityBySolar(str2);
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(day).append("天");
								}
								return new FestivalBean(answerText.toString(), festival);
							} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
								// 中秋节是哪一天
								int y = cal.get(Calendar.YEAR);
								String yyyy = String.valueOf(y);
								String enDate1 = yyyy + "-" + festival.getEnDate();
								String str1 = DateUtil.getEnDate(enDate1);
								de = DateUtil.getDateEntityBySolar(str1);
								try {
									date = new SimpleDateFormat("yyyy-MM-dd").parse(str1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									y = y + 1;
									String enDate2 = y + "-" + festival.getEnDate();
									String str2 = DateUtil.getEnDate(enDate2);
									de = DateUtil.getDateEntityBySolar(str2);
									try {
										date = new SimpleDateFormat("yyyy-MM-dd").parse(str2);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									int day = DateUtil.getDifferentDays(new Date(), date);
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(day).append("天");
								}
								return new FestivalBean(answerText.toString(), festival);
							}
						}
					}
				}
			}
			if (term != null) {
				String[] solarTerms = IntentData.SOLAR_TERM;
				if (d_year != null) {
					// 2017年的大寒是哪一天
					int y = Integer.parseInt(d_year);
					for (int i = 0; i < solarTerms.length; i++) {
						if (term.equals(solarTerms[i])) {
							date = Lunar.getSolarTermCalendar(y, i);
							String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
							de = DateUtil.getDateEntityBySolar(str);
							answerText.append(d_year).append("年").append(term).append("是").append(" ")
									.append(de.getCnDate()).append(" ").append(de.getLunarDate()).append(" ")
									.append(de.getWeekDate());
							return new FestivalBean(answerText.toString(), null);
						}
					}
				} else {
					if (year != null) {
						// 今年的大寒是哪一天
						int j = SlotsData.YEAR.get(year);
						cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + j);
						int y = cal.get(Calendar.YEAR);
						for (int i = 0; i < solarTerms.length; i++) {
							if (term.equals(solarTerms[i])) {
								date = Lunar.getSolarTermCalendar(y, i);
								String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str);
								answerText.append(year).append(term).append("是").append(" ").append(de.getCnDate())
										.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate());
								return new FestivalBean(answerText.toString(), null);
							}
						}
					} else {
						// 大寒是哪一天
						int y = cal.get(Calendar.YEAR);
						for (int i = 0; i < solarTerms.length; i++) {
							if (term.equals(solarTerms[i])) {
								date = Lunar.getSolarTermCalendar(y, i);
								String str1 = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str1);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
								} else {
									y = y + 1;
									cal.set(Calendar.YEAR, y);
									date = Lunar.getSolarTermCalendar(y, i);
									String str2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
									de = DateUtil.getDateEntityBySolar(str2);
									answerText.append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate());
								}
								return new FestivalBean(answerText.toString(), null);
							}
						}
					}
				}
			}
		}
		return new FestivalBean(ERRO_ANSWER, SEMANTIC_FAILURE);
	}

	private FestivalBean query4(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String year = slots.get(FestivalSlot.YEAR);
			String d_year = slots.get(FestivalSlot.D_YEAR);
			String name = slots.get(FestivalSlot.NAME);
			String term = slots.get(FestivalSlot.SOLAR_TERM);
			StringBuffer answerText = new StringBuffer();
			Date date = new Date();
			DateEntity de = new DateEntity();
			cal.setTime(date);
			if (name != null) {
				// 节日同义词
				Festival festival = null;
				Item item = dbSegLoader.getItem(name);
				Set<String> items = null;
				if (item == null) {
					items = new TreeSet<>();
					items.add(name);
				} else {
					items = item.getItems();
				}
				for (String it : items) {
					if (fs.getDateByName(it) != null) {
						festival = fs.getDateByName(it);
						break;
					}
				}
				if (festival != null) {
					if (festival.getDateCode() == OTHER_FESTIVAL) {
						// 藏历年还有多久
						answerText.append(name).append("是").append(festival.getDes());
						return new FestivalBean(answerText.toString(), festival);
					}
					if (d_year != null) {
						int y = Integer.parseInt(d_year);
						String str = "";
						if (festival.getDateCode() == SOLAR_FESTIVAL) {
							// 2017年国庆节还有多久
							int m = Integer.parseInt(festival.getMonth());
							int d = Integer.parseInt(festival.getDay());
							cal.set(y, m - 1, d);
							date = cal.getTime();
							str = new SimpleDateFormat("yyyy-MM-dd").format(date);
						} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
							// 2017年中秋节还有多久
							str = d_year + "-" + festival.getEnDate();
							str = DateUtil.getEnDate(str);
							try {
								date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						de = DateUtil.getDateEntityBySolar(str);
						int days = DateUtil.getDifferentDays(new Date(), date);
						if (days == 0) {
							answerText.append("今天就是");
						} else if (days > 0) {
							answerText.append(d_year).append("年").append(name).append("是").append(de.getCnDate())
									.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate())
									.append(" ").append("还有").append(days).append("天");
						} else {
							days = Math.abs(days);
							answerText.append(d_year).append("年").append(name).append("是").append(de.getCnDate())
									.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate())
									.append(" ").append("已经过去").append(days).append("天");
						}
						return new FestivalBean(answerText.toString(), festival);

					} else {
						String str = "";
						if (year != null) {
							int j = SlotsData.YEAR.get(year);
							cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + j);
							int y = cal.get(Calendar.YEAR);
							if (festival.getDateCode() == SOLAR_FESTIVAL) {
								// 今年国庆节还有多久
								int m = Integer.parseInt(festival.getMonth());
								int d = Integer.parseInt(festival.getDay());
								cal.set(y, m - 1, d);
								date = cal.getTime();
								str = new SimpleDateFormat("yyyy-MM-dd").format(date);
							} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
								// 今年中秋节还有多久
								str = String.valueOf(y) + "-" + festival.getEnDate();
								str = DateUtil.getEnDate(str);
								try {
									date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							de = DateUtil.getDateEntityBySolar(str);
							int days = DateUtil.getDifferentDays(new Date(), date);
							if (days == 0) {
								answerText.append("今天就是");
							} else if (days > 0) {
								answerText.append(year).append(name).append("是").append(de.getCnDate()).append(" ")
										.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
										.append("还有").append(days).append("天");
							} else {
								days = Math.abs(days);
								answerText.append(year).append(name).append("是").append(de.getCnDate())
										.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate())
										.append(" ").append("已经过去").append(days).append("天");
							}
							return new FestivalBean(answerText.toString(), festival);
						} else {
							if (festival.getDateCode() == SOLAR_FESTIVAL) {
								// 国庆节还有多久
								int y = cal.get(Calendar.YEAR);
								int m = Integer.parseInt(festival.getMonth());
								int d = Integer.parseInt(festival.getDay());
								cal.set(y, m - 1, d);
								date = cal.getTime();
								str = new SimpleDateFormat("yyyy-MM-dd").format(date);
								de = DateUtil.getDateEntityBySolar(str);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									y = y + 1;
									cal.set(Calendar.YEAR, y);
									date = cal.getTime();
									str = new SimpleDateFormat("yyyy-MM-dd").format(date);
									de = DateUtil.getDateEntityBySolar(str);
									int day = DateUtil.getDifferentDays(new Date(), date);
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(day).append("天");
								}
								return new FestivalBean(answerText.toString(), festival);
							} else if (festival.getDateCode() == LUNAR_FESTIVAL) {
								// 中秋节还有多久
								int y = cal.get(Calendar.YEAR);
								str = String.valueOf(y) + "-" + festival.getEnDate();
								str = DateUtil.getEnDate(str);
								try {
									date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								de = DateUtil.getDateEntityBySolar(str);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									y = y + 1;
									str = String.valueOf(y) + "-" + festival.getEnDate();
									str = DateUtil.getEnDate(str);
									de = DateUtil.getDateEntityBySolar(str);
									try {
										date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
									int day = DateUtil.getDifferentDays(new Date(), date);
									answerText.append(name).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(day).append("天");
								}
								return new FestivalBean(answerText.toString(), festival);
							}
						}
					}
				}
			}
			if (term != null) {
				String[] solarTerms = IntentData.SOLAR_TERM;
				if (d_year != null) {
					// 2017年冬至还有多久
					int y = Integer.parseInt(d_year);
					for (int i = 0; i < solarTerms.length; i++) {
						if (term.equals(solarTerms[i])) {
							date = Lunar.getSolarTermCalendar(y, i);
							String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
							de = DateUtil.getDateEntityBySolar(str);
							int days = DateUtil.getDifferentDays(new Date(), date);
							if (days == 0) {
								answerText.append("今天就是");
							} else if (days > 0) {
								answerText.append(d_year).append("年").append(term).append("是").append(de.getCnDate())
										.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate())
										.append(" ").append("还有").append(days).append("天");
							} else {
								days = Math.abs(days);
								answerText.append(d_year).append("年").append(term).append("是").append(de.getCnDate())
										.append(" ").append(de.getLunarDate()).append(" ").append(de.getWeekDate())
										.append(" ").append("已经过去").append(days).append("天");
							}
							return new FestivalBean(answerText.toString(), null);
						}
					}
				} else {
					if (year != null) {
						// 明年冬至还有多久
						int j = SlotsData.YEAR.get(year);
						cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + j);
						int y = cal.get(Calendar.YEAR);
						for (int i = 0; i < solarTerms.length; i++) {
							if (term.equals(solarTerms[i])) {
								date = Lunar.getSolarTermCalendar(y, i);
								String str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(year).append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									days = Math.abs(days);
									answerText.append(year).append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("已经过去").append(days).append("天");
								}
								return new FestivalBean(answerText.toString(), null);
							}
						}
					} else {
						// 冬至还有多久
						int y = cal.get(Calendar.YEAR);
						for (int i = 0; i < solarTerms.length; i++) {
							if (term.equals(solarTerms[i])) {
								date = Lunar.getSolarTermCalendar(y, i);
								String str1 = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								de = DateUtil.getDateEntityBySolar(str1);
								int days = DateUtil.getDifferentDays(new Date(), date);
								if (days == 0) {
									answerText.append("今天就是");
								} else if (days > 0) {
									answerText.append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								} else {
									y = y + 1;
									cal.set(Calendar.YEAR, y);
									date = Lunar.getSolarTermCalendar(y, i);
									String str2 = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
									de = DateUtil.getDateEntityBySolar(str2);
									answerText.append(term).append("是").append(de.getCnDate()).append(" ")
											.append(de.getLunarDate()).append(" ").append(de.getWeekDate()).append(" ")
											.append("还有").append(days).append("天");
								}
								return new FestivalBean(answerText.toString(), null);
							}
						}
					}
				}
			}
		}
		return new FestivalBean(ERRO_ANSWER, SEMANTIC_FAILURE);
	}
}