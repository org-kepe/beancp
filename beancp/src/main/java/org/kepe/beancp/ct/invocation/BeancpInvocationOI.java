package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOI<T> extends BeancpInvocation {
	int proceed(BeancpContext context,T fromObj, int toObj);
	Class<T> getFromClass();
	Class<Integer> getToClass();
}