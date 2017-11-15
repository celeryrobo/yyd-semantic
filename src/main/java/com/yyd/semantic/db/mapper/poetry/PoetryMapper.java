package com.yyd.semantic.db.mapper.poetry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.poetry.Poetry;

@Mapper
public interface PoetryMapper {
	@Select("SELECT id FROM tb_poetry")
	public List<Integer> getIdList();
	
	@Select("SELECT id, title, author_name, author_id, content FROM tb_poetry WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title"),
		@Result(property = "authorName", column = "author_name"),
		@Result(property = "authorId", column = "author_id"),
		@Result(property = "content", column = "content")
	})
	public Poetry getById(int id);

	@Select("SELECT id, title, author_name, author_id, content FROM tb_poetry WHERE title = #{title}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title"),
		@Result(property = "authorName", column = "author_name"),
		@Result(property = "authorId", column = "author_id"),
		@Result(property = "content", column = "content")
	})
	public List<Poetry> getByTitle(String title);

	@Select("SELECT id, title, author_name, author_id, content FROM tb_poetry WHERE author_name = #{author}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title"),
		@Result(property = "authorName", column = "author_name"),
		@Result(property = "authorId", column = "author_id"),
		@Result(property = "content", column = "content")
	})
	public List<Poetry> getByAuthor(String author);

	@Select("SELECT id, title, author_name, author_id, content FROM tb_poetry WHERE author_id = #{authorId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title"),
		@Result(property = "authorName", column = "author_name"),
		@Result(property = "authorId", column = "author_id"),
		@Result(property = "content", column = "content")
	})
	public List<Poetry> getByAuthorId(int authorId);
}
