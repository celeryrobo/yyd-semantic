package com.yyd.semantic.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.Poetry;

@Mapper
public interface PoetryMapper {
	@Select("SELECT id FROM tb_poetry")
	public List<Integer> getIdList();
	
	@Select("SELECT * FROM tb_poetry WHERE id = #{id}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "authorName", column = "author_name"),
        @Result(property = "authorId", column = "author_id")
    })
	public Poetry getById(int id);
	
	@Select("SELECT * FROM tb_poetry WHERE title = #{title}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "authorName", column = "author_name"),
        @Result(property = "authorId", column = "author_id")
    })
	public List<Poetry> getByTitle(String title);
	
	@Select("SELECT * FROM tb_poetry WHERE author_name = #{author}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "authorName", column = "author_name"),
        @Result(property = "authorId", column = "author_id")
    })
	public List<Poetry> getByAuthor(String author);
	
	@Select("SELECT * FROM tb_poetry WHERE author_id = #{authorId}")
	@Results({
        @Result(property = "sourceUrl",  column = "source_url"),
        @Result(property = "authorName", column = "author_name"),
        @Result(property = "authorId", column = "author_id")
    })
	public List<Poetry> getByAuthorId(int authorId);
}
