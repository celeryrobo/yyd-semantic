package com.yyd.semantic.db.mapper.poetry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.poetry.PoetrySentence;

@Mapper
public interface PoetrySentenceMapper {
	@Select("SELECT id, sentence, poetry_id FROM tb_sentence WHERE sentence = #{sentence}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "sentence", column = "sentence"),
        @Result(property = "poetryId", column = "poetry_id")
    })
	public List<PoetrySentence> getBySent(String sentence);
	
	@Select("SELECT id, sentence, poetry_id FROM tb_sentence WHERE poetry_id = #{poetryId} ORDER BY id ASC")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "sentence", column = "sentence"),
        @Result(property = "poetryId", column = "poetry_id")
    })
	public List<PoetrySentence> getByPoetryId(Integer poetryId);
}
