package com.yyd.semantic.db.mapper.festival;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.festival.Festival;

@Mapper
public interface FestivalMapper {
	@Select("SELECT * FROM yyd_resources.tb_festival WHERE cn_date = #{cnDate}")
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "enDate", column = "en_date"),
        @Result(property = "cnDate", column = "cn_date"),
        @Result(property = "enName", column = "en_name")
    })
	public List<Festival> getNameByDate(String date);
	
	@Select("SELECT * FROM yyd_resources.tb_festival WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "enDate", column = "en_date"),
        @Result(property = "cnDate", column = "cn_date"),
        @Result(property = "enName", column = "en_name")
    })
	public Festival getDateByName(String name);
	
	@Select("SELECT name FROM yyd_resources.tb_festival")
	public List<String> getAllNames();
	
	@Select("SELECT cn_date FROM yyd_resources.tb_festival")
	public List<String> getAllDates();
}
