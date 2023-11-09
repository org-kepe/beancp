package org.kepe.beancp.config;

public interface BeancpValueFilter {
	<T> T filterValue(Object obj,String key,T value);
}
