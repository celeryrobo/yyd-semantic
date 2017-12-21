package com.yyd.semantic.db.service.impl.festival;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.festival.Festival;
import com.yyd.semantic.db.mapper.festival.FestivalMapper;
import com.yyd.semantic.db.service.festival.FestivalService;

@Service
public class FestivalServiceImpl implements FestivalService {
	@Autowired
	private FestivalMapper festival;

	@Override
	public List<Festival> getNameByMonth(String month, int dateCode) {
		return festival.getNameByMonth(month, dateCode);
	}

	@Override
	public List<Festival> getNameByMonthAndDay(String month, String day, int dateCode) {
		return festival.getNameByMonthAndDay(month, day, dateCode);
	}

	@Override
	public Festival getDateByName(String name) {
		return festival.getDateByName(name);
	}

	@Override
	public List<String> getAllNames() {
		return festival.getAllNames();
	}

}
