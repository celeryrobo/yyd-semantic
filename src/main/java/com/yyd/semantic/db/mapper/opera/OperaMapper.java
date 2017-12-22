package com.yyd.semantic.db.mapper.opera;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.opera.Opera;


@Mapper
public interface OperaMapper {
	@Select("SELECT id FROM yyd_resources.tb_opera_resource ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT name FROM yyd_resources.tb_opera_resource")
	public List<String> getAllName();
	
	@Select("SELECT id, name, content_url FROM yyd_resources.tb_opera_resource WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public Opera getById(Integer id);
	
	@Select("SELECT id, name, content_url FROM yyd_resources.tb_opera_resource WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "resourceUrl", column = "content_url")
	})
	public List<Opera> getByName(String name);
}
