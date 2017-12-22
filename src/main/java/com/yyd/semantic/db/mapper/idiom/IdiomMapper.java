package com.yyd.semantic.db.mapper.idiom;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yyd.semantic.db.bean.idiom.Idiom;

@Mapper
public interface IdiomMapper {
	@Select("SELECT id, content, py_first, py_last, refcount FROM yyd_resources.tb_idiom_resource WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "content", column = "content"),
			@Result(property = "pyFirst", column = "py_first"), @Result(property = "pyLast", column = "py_last"),
			@Result(property = "refcount", column = "refcount"), })
	public Idiom getById(Integer id);

	@Select("SELECT id, content, py_first, py_last, refcount FROM yyd_resources.tb_idiom_resource WHERE content = #{content}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "content", column = "content"),
			@Result(property = "pyFirst", column = "py_first"), @Result(property = "pyLast", column = "py_last"),
			@Result(property = "refcount", column = "refcount"), })
	public Idiom getByContent(String content);

	@Select("SELECT id, content, py_first, py_last, refcount FROM yyd_resources.tb_idiom_resource WHERE py_first = #{pyFirst} ORDER BY refcount ASC")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "content", column = "content"),
			@Result(property = "pyFirst", column = "py_first"), @Result(property = "pyLast", column = "py_last"),
			@Result(property = "refcount", column = "refcount"), })
	public List<Idiom> findByPyFirst(String pyFirst);

	@Select("SELECT id, content, py_first, py_last, refcount FROM yyd_resources.tb_idiom_resource WHERE py_last = #{pyLast} ORDER BY refcount ASC")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "content", column = "content"),
			@Result(property = "pyFirst", column = "py_first"), @Result(property = "pyLast", column = "py_last"),
			@Result(property = "refcount", column = "refcount"), })
	public List<Idiom> findByPyLast(String pyLast);

	@Select("SELECT content FROM yyd_resources.tb_idiom_resource")
	public List<String> findAll();

	@Update("UPDATE yyd_resources.tb_idiom_resource SET refcount = #{refcount} WHERE id = #{id}")
	public void updateRefcount(Idiom idiom);
}
