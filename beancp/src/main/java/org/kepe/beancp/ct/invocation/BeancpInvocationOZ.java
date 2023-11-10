package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOZ<T> extends BeancpInvocation<T,Boolean> {
	boolean proceed(BeancpContext context,T fromObj, boolean toObj);
}