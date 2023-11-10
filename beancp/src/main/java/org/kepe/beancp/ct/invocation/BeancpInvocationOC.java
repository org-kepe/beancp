package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOC<T> extends BeancpInvocation<T,Character> {
	char proceed(BeancpContext context,T fromObj, char toObj);
}