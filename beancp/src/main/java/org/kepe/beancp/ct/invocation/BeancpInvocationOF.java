package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOF<T> extends BeancpInvocation {
	float proceed(BeancpContext context,T fromObj, float toObj);
	Class<T> getFromClass();
	Class<Float> getToClass();
}