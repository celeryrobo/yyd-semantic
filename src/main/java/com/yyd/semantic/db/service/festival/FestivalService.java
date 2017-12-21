package com.yyd.semantic.db.service.festival;

import java.util.List;

import com.yyd.semantic.db.bean.festival.Festival;

public interface FestivalService {
	public List<Festival> getNameByMonth(String month, int dateCode);

	public List<Festival> getNameByMonthAndDay(String month, String day, int dateCode);

	public Festival getDateByName(String name);

	public List<String> getAllNames();

}
