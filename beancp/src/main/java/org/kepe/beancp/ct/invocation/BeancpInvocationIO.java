package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,int fromObj, R toObj);
	Class<Integer> getFromClass();
	Class<R> getToClass();
}