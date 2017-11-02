package com.yyd.semantic.common.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SemanticContext;

@Component
public class RedisSemanticContext implements SemanticContext {
	@Autowired
	private StringRedisTemplate redisTemplate;
	private ContextOperations contextOperations;

	@Override
	public void setUserIdentify(String userIdentify) {
		contextOperations = new ContextOperations(redisTemplate, userIdentify);
	}

	@Override
	public String getService() {
		return (String) contextOperations.get("service");
	}

	@Override
	public void setService(Object value) {
		contextOperations.set("service", value);
	}

	/**
	 * 上下文操作类
	 * 
	 * @author celery
	 * 
	 */
	private class ContextOperations {
		private String redisKeyname;
		private StringRedisTemplate redisTemplate;
		private Long timeout = 24L * 60L * 60L;

		public ContextOperations(StringRedisTemplate redisTemplate, String userIdentify) {
			this.redisTemplate = redisTemplate;
			if (userIdentify != null) {
				redisKeyname = "SEMANTIC:CONTEXT:USER:" + userIdentify;
			}
		}

		public Object get(String key) {
			return redisTemplate.opsForHash().get(redisKeyname, key);
		}

		public void set(String key, Object value) {
			redisTemplate.opsForHash().put(redisKeyname, "service", value);
			redisTemplate.expire(redisKeyname, timeout, TimeUnit.SECONDS);
		}
	}

}
