package com.yyd.semantic.common.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SemanticContext;

@Component
@Scope("prototype")
public class RedisSemanticContext implements SemanticContext {
	private static final Long timeout = 24L * 60L * 60L; // 上下文信息有效期
	@Autowired
	private StringRedisTemplate redisTemplate;
	private Map<Object, Object> attrs; // 上下文属性字典
	private Map<Object, Object> slots; // 语义槽字典
	private Map<Object, Object> objects; // 语义槽实体字典

	@Override
	public void loadByUserIdentify(String userIdentify) {
		String attrKeyname = "SEMANTIC:CONTEXT:ATTRS:USER:" + userIdentify;
		attrs = new RedisStringMap(redisTemplate, attrKeyname);
		redisTemplate.expire(attrKeyname, timeout, TimeUnit.SECONDS);
		String slotKeyname = "SEMANTIC:CONTEXT:SLOTS:USER:" + userIdentify;
		slots = new RedisStringMap(redisTemplate, slotKeyname);
		redisTemplate.expire(slotKeyname, timeout, TimeUnit.SECONDS);
		String objectKeyname = "SEMANTIC:CONTEXT:OBJECTS:USER:" + userIdentify;
		objects = new RedisStringMap(redisTemplate, objectKeyname);
		redisTemplate.expire(objectKeyname, timeout, TimeUnit.SECONDS);
	}

	@Override
	public String getService() {
		return (String) attrs.get("service");
	}

	@Override
	public void setService(String service) {
		attrs.put("service", service);
	}

	@Override
	public Map<Object, Object> getSlots() {
		return slots;
	}

	@Override
	public Map<Object, Object> getObjects() {
		return objects;
	}

}
