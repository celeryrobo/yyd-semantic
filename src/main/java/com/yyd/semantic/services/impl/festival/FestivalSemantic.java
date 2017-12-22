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
import com.yyd.semantic.services.impl.festival.FestivalBean;

@Component
public class FestivalSemantic implements Semantic<FestivalBean> {
	@Autowired
	private FestivalService fs;

	@Autowired
	private DbSegLoader dbSegLoader;

	@Override
	public FestivalBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		FestivalBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case FestivalIntent.DATE: {
			result = queryFestivalDate(objects, semanticContext);
			break;
		}
		case FestivalIntent.LONG_TIME: {
			result = queryFestivalDistanceCurrentDate(objects, semanticContext);
			break;
		}
		case FestivalIntent.SOLAR_TERM: {
			result = querySolarTerm(objects, semanticContext);
			break;
		}
		case FestivalIntent.SOLAR_TERM_LONG_TIME: {
			result = querySolarTermDistanceCurrentDate(objects, semanticContext);
			break;
		}
		case FestivalIntent.SOLAR_NAME: {
			result = queryFestivalName(objects, semanticContext, 1);
			break;
		}
		case FestivalIntent.LUNAR_NAME: {
			result = queryFestivalName(objects, semanticContext, 2);
			break;
		}
		default:
			result = new FestivalBean("我不知道你在说什么", null);
			break;
		}
		return result;
	}

	private FestivalBean queryFestivalName(Map<String, String> slots, SemanticContext semanticContext, int dateCode) {
		String date = "我也不知道这个是什么节日";
		if (!slots.isEmpty()) {
			String month = slots.get(FestivalSlot.DIGITAL_MONTH);
			String day = slots.get(FestivalSlot.DIGITAL_DAY);
			System.out.println("month=" + month + "  day" + day);
			StringBuffer answerText = new StringBuffer();
			List<Festival> festival = null;
			if (month != null && day != null) {
				festival = fs.getNameByMonthAndDay(month, day, dateCode);
				int len = festival.size();
				if (festival != null && len == 1) {
					answerText.append("是").append(festival.get(0).getName());
					return new FestivalBean(answerText.toString(), festival);
				} else if (festival != null && len > 1) {
					answerText.append("有");
					for (int i = 0; i < len; i++) {
						answerText.append(festival.get(i).getName());
						if (i != len - 1) {
							answerText.append(",");
						}
					}
					return new FestivalBean(answerText.toString(), festival);
				}
			} else if (month != null && day == null) {
				festival = fs.getNameByMonth(month, dateCode);
				int len = festival.size();
				if (festival != null) {
					answerText.append("有");
					for (int i = 0; i < len; i++) {
						answerText.append(festival.get(i).getName());
						if (i != len - 1) {
							answerText.append(",");
						}
					}
				}
				return new FestivalBean(answerText.toString(), festival);
			}
		}
		return new FestivalBean(date, null);
	}

	private FestivalBean queryFestivalDate(Map<String, String> slots, SemanticContext semanticContext) {
		String festivalName = "我不太明白你说的这个节日";
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			festivalName = slots.get(FestivalSlot.FESTIVAL_NAME);
			String givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String digitalYear = slots.get(FestivalSlot.GIVEN_DIGITAL_YEAR);
			Festival festival = null;
			if (festivalName != null) {
				Item item = dbSegLoader.getItem(festivalName);
				Set<String> items = null;
				if (item == null) {
					items = new TreeSet<>();
					items.add(festivalName);
				} else {
					items = item.getItems();
				}
				for (String it : items) {
					if (fs.getDateByName(it) != null) {
						festival = fs.getDateByName(it);
						break;
					}
				}
			}
			if (festival.getDateCode() == 3) {
				answerText.append(festivalName).append("是").append(festival.getDes());
				return new FestivalBean(answerText.toString(), null);
			}
			DateEntity de = getFullDate(festivalName, givenYear, digitalYear, festival.getEnDate(),
					festival.getDateCode());
			if (givenYear == null) {
				answerText.append("");
			} else {
				answerText.append(givenYear);
			}
			answerText.append(festivalName).append("是").append(de.getCnDate()).append(de.getLunarDate())
					.append(de.getWeekDate());
			return new FestivalBean(answerText.toString(), festival);
		}
		return new FestivalBean(festivalName, null);
	}

	private FestivalBean queryFestivalDistanceCurrentDate(Map<String, String> slots, SemanticContext semanticContext) {
		String festivalName = "我不太明白你说的这个节日";
		int distanceDays = 0;
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			festivalName = slots.get(FestivalSlot.FESTIVAL_NAME);
			String givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String digitalYear = slots.get(FestivalSlot.GIVEN_DIGITAL_YEAR);
			Festival festival = null;
			if (festivalName != null) {
				Item item = dbSegLoader.getItem(festivalName);
				Set<String> items = null;
				if (item == null) {
					items = new TreeSet<>();
					items.add(festivalName);
				} else {
					items = item.getItems();
				}
				for (String it : items) {
					if (fs.getDateByName(it) != null) {
						festival = fs.getDateByName(it);
						break;
					}
				}
			}
			if (festival.getDateCode() == 3) {
				answerText.append(festivalName).append("是").append(festival.getDes());
				return new FestivalBean(answerText.toString(), festival);
			}
			DateEntity de = getFullDate(festivalName, givenYear, digitalYear, festival.getEnDate(),
					festival.getDateCode());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (givenYear == null && digitalYear == null) {
				answerText.append("");
			} else if (givenYear != null && digitalYear == null) {
				answerText.append(givenYear);
			} else if (digitalYear != null && givenYear == null) {
				answerText.append(digitalYear).append("年");
			}
			try {
				distanceDays = DateUtil.getDifferentDays(new Date(), sdf.parse(de.getEnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (distanceDays == 0) {
				answerText.append("今天就是").append(festivalName);
			} else if (distanceDays < 0) {
				answerText.append(festivalName).append("已经过完啦");
			} else {
				answerText.append(festivalName).append("是").append(de.getCnDate()).append(de.getLunarDate())
						.append(de.getWeekDate()).append("还有").append(String.valueOf(distanceDays)).append("天");
			}
			return new FestivalBean(answerText.toString(), festival);
		}
		return new FestivalBean(festivalName, null);
	}

	private FestivalBean querySolarTermDistanceCurrentDate(Map<String, String> slots, SemanticContext semanticContext) {
		String solarTermName = "我不知道你说的这个节气";
		int distanceDays = 0;
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			solarTermName = slots.get(FestivalSlot.SOLAR_TERM);
			String givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String digitalYear = slots.get(FestivalSlot.GIVEN_DIGITAL_YEAR);
			String[] solarTerms = IntentData.SOLAR_TERM;
			if (solarTermName != null) {
				for (int i = 0; i < solarTerms.length; i++) {
					if (solarTermName.equals(solarTerms[i])) {
						DateEntity de = getFullSolarTermDate(givenYear, digitalYear, i);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (givenYear == null && digitalYear == null) {
							answerText.append("");
						} else if (givenYear != null && digitalYear == null) {
							answerText.append(givenYear);
						} else if (digitalYear != null && givenYear == null) {
							answerText.append(digitalYear).append("年");
						}
						try {
							distanceDays = DateUtil.getDifferentDays(new Date(), sdf.parse(de.getEnDate()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if (distanceDays == 0) {
							answerText.append("今天就是").append(solarTermName);
						} else if (distanceDays < 0) {
							answerText.append(solarTermName).append("已经过啦");
						} else {
							answerText.append(solarTermName).append("是").append(de.getCnDate())
									.append(de.getLunarDate()).append(de.getWeekDate()).append("还有")
									.append(String.valueOf(distanceDays)).append("天");
						}
						return new FestivalBean(answerText.toString(), null);
					}
				}
			}
		}
		return new FestivalBean(solarTermName, null);
	}

	private FestivalBean querySolarTerm(Map<String, String> slots, SemanticContext semanticContext) {
		String solarTermName = "我不知道你说的这个节气";
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			solarTermName = slots.get(FestivalSlot.SOLAR_TERM);
			String givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String digitalYear = slots.get(FestivalSlot.GIVEN_DIGITAL_YEAR);
			String[] solarTerms = IntentData.SOLAR_TERM;
			if (solarTermName != null) {
				for (int i = 0; i < solarTerms.length; i++) {
					if (solarTermName.equals(solarTerms[i])) {
						DateEntity de = getFullSolarTermDate(givenYear, digitalYear, i);
						if (givenYear == null && digitalYear == null) {
							answerText.append("");
						} else if (givenYear != null && digitalYear == null) {
							answerText.append(givenYear);
						} else if (digitalYear != null && givenYear == null) {
							answerText.append(digitalYear);
						}
						answerText.append(solarTermName).append("是").append(de.getCnDate()).append(de.getLunarDate())
								.append(de.getWeekDate());
						return new FestivalBean(answerText.toString(), null);
					}
				}
			}
		}
		return new FestivalBean(solarTermName, null);
	}

	private DateEntity getFullSolarTermDate(String givenYear, String digitalYear, int indexSolarTerm) {
		int currentYear = DateUtil.getCurrentYear();
		String enDate = "";
		if (givenYear == null && digitalYear == null) {
			String mmdd = getMMDD(currentYear, indexSolarTerm);
			enDate = String.valueOf(currentYear) + "-" + mmdd;
		} else if (givenYear != null && digitalYear == null) {
			currentYear = getYear(givenYear, currentYear);
			String mmdd = getMMDD(currentYear, indexSolarTerm);
			enDate = String.valueOf(currentYear) + "-" + mmdd;
		} else if (digitalYear != null && givenYear == null && digitalYear.length() == 4) {
			currentYear = Integer.parseInt(digitalYear);
			String mmdd = getMMDD(currentYear, indexSolarTerm);
			enDate = String.valueOf(currentYear) + "-" + mmdd;
		} else {
			String mmdd = getMMDD(currentYear, indexSolarTerm);
			enDate = String.valueOf(currentYear) + "-" + mmdd;
		}
		System.out.println("enDate=" + enDate);
		return DateUtil.getDateEntityBySolar(enDate);
	}

	private String getMMDD(int currentYear, int indexSolarTerm) {
		Calendar cl = Calendar.getInstance();
		Date date = Lunar.getSolarTermCalendar(currentYear, indexSolarTerm);
		cl.setTime(date);
		String mm = "";
		String dd = "";
		if (cl.get(Calendar.MONTH) < 10) {
			int i = cl.get(Calendar.MONTH) + 1;
			mm = (i < 10) ? ("0" + i) : String.valueOf(i);
			// mm = "0"+String.valueOf((cl.get(Calendar.MONTH) + 1));
		} else {
			mm = String.valueOf((cl.get(Calendar.MONTH) + 1));
		}
		if (cl.get(Calendar.DAY_OF_MONTH) < 10) {
			dd = "0" + cl.get(Calendar.DAY_OF_MONTH);
		} else {
			dd = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
		}
		return mm + "-" + dd;
	}

	private int getYear(String givenYear, int currentYear) {
		switch (givenYear) {
		case IntentData.JINNIAN: {
			break;
		}
		case IntentData.MINGNIAN: {
			return currentYear = currentYear + 1;
		}
		case IntentData.HOUNIAN: {
			return currentYear = currentYear + 2;
		}
		case IntentData.DAHOUNIAN: {
			return currentYear = currentYear + 3;
		}
		case IntentData.QUNIAN: {
			return currentYear = currentYear - 1;
		}
		case IntentData.QIANNIAN: {
			return currentYear = currentYear - 2;

		}
		case IntentData.DAQIANNIAN: {
			return currentYear = currentYear - 3;
		}
		default:
			return currentYear;
		}
		return currentYear;

	}

	private DateEntity getFullDate(String festivalName, String givenYear, String digitalYear, String mmdd,
			int dateCode) {
		int currentYear = 2000;
		String enDate = "";
		if (dateCode == 1) {
			currentYear = DateUtil.getCurrentYear();
			if (givenYear != null) {
				currentYear = getYear(givenYear, currentYear);
			} else {
				if (DateUtil.isFestivalPassed(mmdd)) {
					currentYear = currentYear + 1;
				}
			}
			if (digitalYear != null && digitalYear.length() == 4) {
				currentYear = Integer.parseInt(digitalYear);
			}
			// 指定阳历节日年份的节日的日期
			enDate = String.valueOf(currentYear) + "-" + mmdd;
		} else if (dateCode == 2) {
			currentYear = DateUtil.getCurrentLunarYear();
			if (givenYear != null) {
				currentYear = getYear(givenYear, currentYear);
			} else {
				if (DateUtil.isLunarFestivalPassed(mmdd)) {
					currentYear = currentYear + 1;
				}
			}
			if (digitalYear != null && digitalYear.length() == 4) {
				currentYear = Integer.parseInt(digitalYear);
			}
			// 指定阴历节日年份的节日的日期
			String lunarDate = String.valueOf(currentYear) + "-" + mmdd;
			// 该阴历节日的阳历时间
			enDate = DateUtil.getEnDate(lunarDate);
		} else {
			// 其它历法节日
		}
		return DateUtil.getDateEntityBySolar(enDate);
	}
}