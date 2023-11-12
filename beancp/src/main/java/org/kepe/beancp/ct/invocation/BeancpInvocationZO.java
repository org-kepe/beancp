package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,boolean fromObj, R toObj);
	Class<Boolean> getFromClass();
	Class<R> getToClass();
}