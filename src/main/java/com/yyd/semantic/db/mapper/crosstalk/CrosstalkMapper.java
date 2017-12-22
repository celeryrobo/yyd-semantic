package com.yyd.semantic.db.mapper.crosstalk;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.crosstalk.Crosstalk;



@Mapper
public interface CrosstalkMapper {
	@Select("SELECT id FROM yyd_resources.tb_crosstalk_resource ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT name FROM yyd_resources.tb_crosstalk_resource")
	public List<String> getAllName();
	
	@Select("SELECT id, name, content_url FROM yyd_resources.tb_crosstalk_resource WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public Crosstalk getById(Integer id);
	
	@Select("SELECT id, name, content_url FROM yyd_resources.tb_crosstalk_resource WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public List<Crosstalk> getByName(String name);
}
