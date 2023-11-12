package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOS<T> extends BeancpInvocation {
	short proceed(BeancpContext context,T fromObj, short toObj);
	Class<T> getFromClass();
	Class<Short> getToClass();
}