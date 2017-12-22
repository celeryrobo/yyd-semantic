package com.yyd.semantic.db.mapper.crosstalk;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.crosstalk.Actor;



@Mapper
public interface ActorMapper {
	@Select("SELECT id, name FROM yyd_resources.tb_crosstalk_actor WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
	})
	public Actor getById(Integer id);
	
	@Select("SELECT id FROM yyd_resources.tb_crosstalk_actor WHERE name = #{name}")
	public List<Integer> getIdsByName(String name);
	
	@Select("SELECT name FROM yyd_resources.tb_crosstalk_actor")
	public List<String> getAllNames();
}
