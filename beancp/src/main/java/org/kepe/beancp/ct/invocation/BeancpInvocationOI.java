package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOI<T> extends BeancpInvocation<T,Integer> {
	int proceed(BeancpContext context,T fromObj, int toObj);
}