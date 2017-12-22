package com.yyd.semantic.db.mapper.phonenumber;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.semantic.db.bean.phonenumber.PhoneNumber;


@Mapper
public interface PhoneNumberMapper {
	@Select("SELECT id FROM yyd_resources.tb_phone_number ORDER BY RAND() LIMIT 10")
	public List<Integer> getIdList();
	
	@Select("SELECT distinct name FROM yyd_resources.tb_phone_number")
	public List<String> getAllName();
	
	@Select("SELECT distinct number FROM yyd_resources.tb_phone_number")
	public List<String> getAllNumber();
	
	@Select("SELECT id, name, number FROM yyd_resources.tb_phone_number WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "number", column = "number"),
	})
	public PhoneNumber getById(Integer id);
	
	@Select("SELECT id, name, number FROM yyd_resources.tb_phone_number WHERE name  = #{name} ")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "number", column = "number"),		
	})
	public List<PhoneNumber> findByName(String name);
	
	@Select("SELECT id, name, number FROM yyd_resources.tb_phone_number WHERE number = #{number} ")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "number", column = "number"),		
	})
	public List<PhoneNumber> getByNumber(String number);
}
