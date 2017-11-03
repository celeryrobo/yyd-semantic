package com.yyd.semantic.common;

public interface SemanticContext {
	void loadByUserIdentify(String userIdentify);

	String getService();

	void setService(String service);
	
	void save();
	
}
