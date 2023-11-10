package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOO<T,R> extends BeancpInvocation<T,R> {
	Object proceed(BeancpContext context,T fromObj, R toObj);
}
