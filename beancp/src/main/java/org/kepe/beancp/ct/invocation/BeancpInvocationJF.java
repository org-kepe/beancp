package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJF extends BeancpInvocation {
	float proceed(BeancpContext context,long fromObj, float toObj);
	Class<Long> getFromClass();
	Class<Float> getToClass();
}