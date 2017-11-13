package com.yyd.semantic.db.mapper.poetry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.poetry.Author;

@Mapper
public interface AuthorMapper {
	@Select("SELECT * FROM tb_author")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "chaodai", column = "caodai")
    })
	public List<Author> getAuthorList();
	
	@Select("SELECT * FROM tb_author WHERE id = #{id}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "chaodai", column = "caodai")
    })
	public Author getAuthorById(Integer id);
	
	@Select("SELECT * FROM tb_author WHERE name = #{name}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "chaodai", column = "caodai")
    })
	public List<Author> findByName(String name);
	
	@Select("SELECT * FROM tb_author WHERE caodai = #{dynasty}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "chaodai", column = "caodai")
    })
	public List<Author> findByDynasty(String dynasty);
}
