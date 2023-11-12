package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOC<T> extends BeancpInvocation {
	char proceed(BeancpContext context,T fromObj, char toObj);
	Class<T> getFromClass();
	Class<Character> getToClass();
}