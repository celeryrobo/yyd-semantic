package com.yyd.semantic.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.PoetrySentence;

@Mapper
public interface PoetrySentenceMapper {
	@Select("SELECT * FROM tb_sentence WHERE sentence = #{sentence}")
	@Results({
         @Result(property = "poetryId", column = "poetry_id")
    })
	public List<PoetrySentence> getBySent(String sentence);
}
