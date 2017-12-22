package com.yyd.semantic.db.service.phonenumber;

import java.util.List;

import com.yyd.semantic.db.bean.phonenumber.PhoneNumber;

public interface PhoneNumberService {
	public List<Integer> getIdList();
	
	public List<String> getAllName();
	
	public List<String> getAllNumber();
	
	public PhoneNumber getById(Integer id);
	
	public List<PhoneNumber> findByName(String name);
	
	public List<PhoneNumber> getByNumber(String number);
}
