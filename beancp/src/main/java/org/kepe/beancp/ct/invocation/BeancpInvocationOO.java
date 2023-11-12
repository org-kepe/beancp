package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOO<T,R> extends BeancpInvocation {
	R proceed(BeancpContext context,T fromObj, R toObj);
	Class<T> getFromClass();
	Class<R> getToClass();
}
