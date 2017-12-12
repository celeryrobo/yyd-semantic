package com.yyd.semantic.db.service.festival;

import java.util.List;

import com.yyd.semantic.db.bean.festival.Festival;

public interface FestivalService {
	public List<Festival> getNameByDate(String date);

	public Festival getDateByName(String name);

	public List<String> getAllNames();

	public List<String> getAllDates();
}
