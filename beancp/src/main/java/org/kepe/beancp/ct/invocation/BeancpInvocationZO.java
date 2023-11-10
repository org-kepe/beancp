package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZO<R> extends BeancpInvocation<Boolean,R> {
	R proceed(BeancpContext context,boolean fromObj, R toObj);
}