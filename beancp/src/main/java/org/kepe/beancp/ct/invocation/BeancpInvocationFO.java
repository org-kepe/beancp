package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFO<R> extends BeancpInvocation<Float,R> {
	R proceed(BeancpContext context,float fromObj, R toObj);
}