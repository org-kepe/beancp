package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,char fromObj, R toObj);
	Class<Character> getFromClass();
	Class<R> getToClass();
}