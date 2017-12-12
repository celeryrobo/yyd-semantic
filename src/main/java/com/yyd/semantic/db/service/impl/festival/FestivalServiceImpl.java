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
	public List<Festival> getNameByDate(String date) {
		return festival.getNameByDate(date);
	}
	@Override
	public Festival getDateByName(String name) {
		return festival.getDateByName(name);
	}
	@Override
	public List<String> getAllNames() {
		// TODO Auto-generated method stub
		return festival.getAllNames();
	}
	@Override
	public List<String> getAllDates() {
		// TODO Auto-generated method stub
		return festival.getAllDates();
	}
}
