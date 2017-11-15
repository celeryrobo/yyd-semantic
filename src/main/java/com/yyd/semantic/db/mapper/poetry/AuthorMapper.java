package com.yyd.semantic.db.mapper.poetry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.poetry.Author;

@Mapper
public interface AuthorMapper {
	@Select("SELECT id, name, caodai FROM tb_author WHERE id = #{id}")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "chaodai", column = "caodai")
    })
	public Author getAuthorById(Integer id);
	
	@Select("SELECT id, name, caodai FROM tb_author WHERE name = #{name}")
	@Results({
		@Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "chaodai", column = "caodai")
    })
	public List<Author> findByName(String name);
}
