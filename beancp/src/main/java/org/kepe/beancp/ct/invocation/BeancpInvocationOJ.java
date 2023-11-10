package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOJ<T> extends BeancpInvocation<T,Long> {
	long proceed(BeancpContext context,T fromObj, long toObj);
}