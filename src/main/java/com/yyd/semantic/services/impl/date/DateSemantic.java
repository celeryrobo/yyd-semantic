package com.yyd.semantic.services.impl.date;

import java.util.Map;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

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
			if (day == days[0]) {
				// 今天
			} else if (day == days[1]) {
				// 明天
			} else if (day == days[2]) {
				// 后天
			} else if (day == days[3]) {
				// 大后天
			} else if (day == days[4]) {
				// 昨天
			} else if (day == days[5]) {
				// 前天
			} else if (day == days[6]) {
				// 大前天
			}
		}

		return null;
	}

	private DateBean query2(Map<String, String> slots, SemanticContext semanticContext) {
		if (!slots.isEmpty()) {
			String[] months=SlotsData.MONTH;
			String month = slots.get(DateSlot.MONTH);
			String d_month = slots.get(DateSlot.D_MONTH);
			if (month != null && d_month != null) {
				if(month==months[0]) {
					//这个月
				}else if(month==months[1]) {
					//下个月
				}else if(month==months[2]) {
					//上个月
				}
			}
		}
		return null;
	}

	private DateBean query3(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

	private DateBean query4(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

	private DateBean query5(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

	private DateBean query6(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

	private DateBean query7(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

	private DateBean query8(Map<String, String> slots, SemanticContext semanticContext) {

		return null;
	}

}
