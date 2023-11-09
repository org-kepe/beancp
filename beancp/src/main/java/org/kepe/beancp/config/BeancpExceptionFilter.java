package org.kepe.beancp.config;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;

public interface BeancpExceptionFilter {
	default Boolean isThrowException(Object provider,BeancpFeature feature,BeancpContext context,String key,int handle,Exception e) {
		return null;
	}
	default void filterException(Object provider,BeancpFeature feature,BeancpContext context,String key,int handle,Exception e) {
		this.filterException(feature, context, key, handle, e);
	}

	void filterException(BeancpFeature feature,BeancpContext context,String key,int handle,Exception e);
}
