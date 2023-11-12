package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOZ<T> extends BeancpInvocation {
	boolean proceed(BeancpContext context,T fromObj, boolean toObj);
	Class<T> getFromClass();
	Class<Boolean> getToClass();
}