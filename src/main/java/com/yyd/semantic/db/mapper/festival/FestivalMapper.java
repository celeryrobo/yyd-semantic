package com.yyd.semantic.db.mapper.festival;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.festival.Festival;

@Mapper
public interface FestivalMapper {
	@Select("SELECT * FROM yyd_resources.tb_festival WHERE month = #{month} AND date_code = #{dateCode}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "enDate", column = "en_date"), @Result(property = "month", column = "month"),
			@Result(property = "day", column = "day"), @Result(property = "dateCode", column = "date_code"),
			@Result(property = "enName", column = "en_name"), @Result(property = "des", column = "description") })
	public List<Festival> getNameByMonth(@Param("month") String month, @Param("dateCode") int dateCode);

	@Select("SELECT * FROM yyd_resources.tb_festival WHERE month = #{month} AND day = #{day} AND date_code = #{dateCode}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "enDate", column = "en_date"), @Result(property = "month", column = "month"),
			@Result(property = "day", column = "day"), @Result(property = "dateCode", column = "date_code"),
			@Result(property = "enName", column = "en_name"), @Result(property = "des", column = "description") })
	public List<Festival> getNameByMonthAndDay(@Param("month") String month, @Param("day") String day,
			@Param("dateCode") int dateCode);

	@Select("SELECT * FROM yyd_resources.tb_festival WHERE name = #{name}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "enDate", column = "en_date"), @Result(property = "month", column = "month"),
			@Result(property = "day", column = "day"), @Result(property = "dateCode", column = "date_code"),
			@Result(property = "enName", column = "en_name"), @Result(property = "des", column = "description") })
	public Festival getDateByName(String name);

	@Select("SELECT name FROM yyd_resources.tb_festival")
	public List<String> getAllNames();
}
