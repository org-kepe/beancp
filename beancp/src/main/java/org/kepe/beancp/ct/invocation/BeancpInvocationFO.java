package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,float fromObj, R toObj);
	Class<Float> getFromClass();
	Class<R> getToClass();
}