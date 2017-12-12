package com.yyd.semantic.services.impl.festival;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.festival.Festival;
import com.yyd.semantic.db.service.festival.FestivalService;
import com.yyd.semantic.services.impl.festival.FestivalBean;

@Component
public class FestivalSemantic implements Semantic<FestivalBean> {
	@Autowired
	private FestivalService fs;

	@Override
	public FestivalBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		FestivalBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case FestivalIntent.NAME: {
			result = queryFestivalName(objects, semanticContext);
			break;
		}
		case FestivalIntent.DATE: {
			result = queryFestivalDate(objects, semanticContext);
			break;
		}
		case FestivalIntent.LONG_TIME: {
			result = queryFestivalDistanceCurrentDate(objects, semanticContext);
			break;
		}
		default:
			result = new FestivalBean("没有找到");
			break;
		}
		if (result == null) {
			result = new FestivalBean("null");
		}
		return result;
	}

	private FestivalBean queryFestivalName(Map<String, String> slots, SemanticContext semanticContext) {
		String date = "我不太明白你说的这个节日";
		if (!slots.isEmpty()) {
			date = slots.get(FestivalSlot.FESTIVAL_DATE);
			List<Festival> festival = fs.getNameByDate(date);
			StringBuffer sb = new StringBuffer();
			if (festival != null) {
				int len = festival.size();
				if (len == 1) {
					sb.append("是");
					sb.append(festival.get(0).getName());
				} else {
					sb.append("有");
					for (int i = 0; i < len; i++) {
						sb.append(festival.get(i).getName());
						if (i != len - 1) {
							sb.append(",");
						}
					}
				}
				return new FestivalBean(date + sb);
			}
		}
		return new FestivalBean(date);
	}
	private FestivalBean queryFestivalDate(Map<String, String> slots, SemanticContext semanticContext) {
		String festivalName = "我不太明白你说的这个节日";
		String givenYear = "我不太明白你说的这一年";
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			festivalName = slots.get(FestivalSlot.FESTIVAL_NAME);
			givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String mmdd = fs.getDateByName(festivalName).getEnDate(); // 拿到节日的mm-dd
			DateEntity de = getFullDate(festivalName, givenYear, mmdd);
			// 节日阴历xx节日是xxxx年xx月xx日农历xx月xx日星期x
			if(givenYear==null) {
				givenYear="";
			}
			answerText.append(givenYear).append(festivalName).append("是").append(de.getCnDate()).append(de.getLunarDate())
					.append(de.getWeekDate());
			return new FestivalBean(answerText.toString());
		} else {
			return new FestivalBean("---->");
		}
	}
	private FestivalBean queryFestivalDistanceCurrentDate(Map<String, String> slots, SemanticContext semanticContext) {
		String festivalName = "我不太明白你说的这个节日";
		String givenYear = "";
		int distanceDays = 0;
		StringBuffer answerText = new StringBuffer();
		if (!slots.isEmpty()) {
			festivalName = slots.get(FestivalSlot.FESTIVAL_NAME);
			givenYear = slots.get(FestivalSlot.GIVEN_YEAR);
			String mmdd = fs.getDateByName(festivalName).getEnDate(); // 拿到节日的mm-dd
			DateEntity de = getFullDate(festivalName, givenYear, mmdd);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			if (givenYear == null) {
				givenYear = "";
			}
			try {
				distanceDays = DateUtil.getDifferentDays(new Date(), sdf.parse(de.getEnDate()));
				if (distanceDays > 0) {
					// xx节日是xxxx年xx月xx日农历xxxx年xx月xx日星期x还有xx天
					answerText.append(givenYear).append(festivalName).append("是").append(de.getCnDate())
							.append(de.getLunarDate()).append(de.getWeekDate()).append("还有")
							.append(String.valueOf(distanceDays)).append("天");
				} else if (distanceDays == 0) {
					answerText.append("今天就是").append(festivalName);
				} else {
					answerText.append(givenYear).append(festivalName).append("已经过完啦");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return new FestivalBean(answerText.toString());
		} else {
			return new FestivalBean("---->");
		}
	}
	private DateEntity getFullDate(String festivalName, String givenYear, String mmdd) {
		int currentYear = DateUtil.getCurrentYear();
		if (mmdd != null) {
			if (givenYear == null) {
				// 没有指定年份节日的日期
				if (DateUtil.isFestivalPassed(mmdd)) {
					currentYear = currentYear + 1;
				}
			} else {
				// 指定年份的节日的日期
				switch (givenYear) {
				case IntentData.JINNIAN: {
					break;
				}
				case IntentData.MINGNIAN: {
					currentYear = currentYear + 1;
					break;
				}
				case IntentData.HOUNIAN: {
					currentYear = currentYear + 2;
					break;
				}
				case IntentData.DAHOUNIAN: {
					currentYear = currentYear + 3;
					break;
				}
				case IntentData.QUNIAN: {
					currentYear = currentYear - 1;
					break;
				}
				case IntentData.QIANNIAN: {
					currentYear = currentYear - 2;
					break;
				}
				case IntentData.DAQIANNIAN: {
					currentYear = currentYear - 3;
					break;
				}
				default:
					break;
				}
			}
		}
		String enDate = String.valueOf(currentYear) + "-" + mmdd;
		return DateUtil.getDateEntityBySolar(enDate);
	}
}