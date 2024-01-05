package org.kepe.beancp.config;

public interface BeancpPropertyGetAndSet {
	default boolean isValidGet(String key) {
		return true;
	}
	Object get(String key);
	default boolean isValidSet(String key) {
		return true;
	}
	void set(String key,Object value);
	
	boolean containsGetKey(String key);
	boolean containsSetKey(String key);
	
}
