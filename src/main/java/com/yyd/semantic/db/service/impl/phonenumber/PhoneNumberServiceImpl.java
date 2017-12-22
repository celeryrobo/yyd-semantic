package com.yyd.semantic.db.service.impl.phonenumber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyd.semantic.db.bean.phonenumber.PhoneNumber;
import com.yyd.semantic.db.mapper.phonenumber.PhoneNumberMapper;
import com.yyd.semantic.db.service.phonenumber.PhoneNumberService;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService{
	@Autowired
	private PhoneNumberMapper phoneNumberMapper;
	
	@Override
	public List<Integer> getIdList(){
		return phoneNumberMapper.getIdList();
	}
	
	@Override
	public List<String> getAllName(){
		return phoneNumberMapper.getAllName();
	}
	
	@Override
	public List<String> getAllNumber(){
		return phoneNumberMapper.getAllNumber();
	}
	
	@Override
	public PhoneNumber getById(Integer id){
		return phoneNumberMapper.getById(id);
	}
	
	@Override
	public List<PhoneNumber> findByName(String name){
		return phoneNumberMapper.findByName(name);
	}
	
	@Override
	public List<PhoneNumber> getByNumber(String number){
		return phoneNumberMapper.getByNumber(number);
	}
}


