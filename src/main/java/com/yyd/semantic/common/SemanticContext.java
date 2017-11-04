package com.yyd.semantic.common;

import java.util.Map;

public interface SemanticContext {
	/**
	 * 根据用户标识加载用户上下文信息
	 * 
	 * @param userIdentify
	 *            用户标识
	 */
	void loadByUserIdentify(String userIdentify);

	/**
	 * 当前所在场景
	 * 
	 * @return 场景标识字符串
	 */
	String getService();

	/**
	 * 设置当前场景
	 * 
	 * @param service
	 *            场景标识字符串
	 */
	void setService(String service);

	/**
	 * 获取上次语义槽信息
	 * 
	 * @return 语义槽字典
	 */
	Map<Object, Object> getSlots();

	/**
	 * 获取上次语义实体信息
	 * 
	 * @return 语义实体字典
	 */
	Map<Object, Object> getObjects();

}
