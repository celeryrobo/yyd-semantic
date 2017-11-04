package com.yyd.semantic.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SemanticContext;

@Component
@Scope("prototype")
public class RedisSemanticContext implements SemanticContext {
	@Autowired
	private StringRedisTemplate redisTemplate;
	private ContextOperations contextOperations;
	private Map<String, OperationEntity> mapParams;

	public RedisSemanticContext() {
		mapParams = new HashMap<String, OperationEntity>();
	}

	@Override
	public void loadByUserIdentify(String userIdentify) {
		contextOperations = new ContextOperations(redisTemplate, userIdentify);
		String[] paramNames = { "service", "params" };
		List<Object> objs = contextOperations.load(paramNames);
		for (int index = 0; index < paramNames.length; index++) {
			mapParams.put(paramNames[index], new OperationEntity(objs.get(index)));
		}
	}

	@Override
	public String getService() {
		OperationEntity entity = mapParams.get("service");
		if (entity == null) {
			return null;
		}
		return (String) entity.getValue();
	}

	@Override
	public void setService(String service) {
		OperationEntity entity = mapParams.get("service");
		entity.setValue(service);
		entity.setStatus(OperationEntity.MIDIFYED);
	}

	@Override
	public void save() {
		Map<String, Object> params = new HashMap<String, Object>();
		for (Map.Entry<String, OperationEntity> entry : mapParams.entrySet()) {
			OperationEntity entity = entry.getValue();
			if (entity.getStatus() == OperationEntity.MIDIFYED) {
				params.put(entry.getKey(), entity.getValue());
			}
		}
		contextOperations.save(params);
	}

	/**
	 * 上下文操作类
	 * 
	 * @author celery
	 * 
	 */
	private static class ContextOperations {
		private String redisKeyname;
		private StringRedisTemplate redisTemplate;
		private Long timeout = 24L * 60L * 60L;

		public ContextOperations(StringRedisTemplate redisTemplate, String userIdentify) {
			this.redisTemplate = redisTemplate;
			if (userIdentify != null) {
				redisKeyname = "SEMANTIC:CONTEXT:USER:" + userIdentify;
			}
		}

		public List<Object> load(String... keys) {
			Collection<Object> collection = new ArrayList<Object>();
			for (String key : keys) {
				collection.add(key);
			}
			return redisTemplate.opsForHash().multiGet(redisKeyname, collection);
		}

		public Object save(Map<String, Object> args) {
			if (args.size() == 0) {
				return null;
			}
			SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
				@SuppressWarnings("unchecked")
				@Override
				public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
					operations.multi();
					operations.opsForHash().putAll((K) redisKeyname, args);
					return operations.exec();
				}
			};
			Object result = redisTemplate.execute(sessionCallback);
			redisTemplate.expire(redisKeyname, timeout, TimeUnit.SECONDS);
			return result;
		}
	}

	private static class OperationEntity {
		public static final int NORMAL = 0;
		public static final int MIDIFYED = 1;
		private Object value;
		private int status;

		public OperationEntity(Object value) {
			setValue(value);
			setStatus(OperationEntity.NORMAL);
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

}
