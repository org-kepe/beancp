package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDO<R> extends BeancpInvocation<Double,R> {
	R proceed(BeancpContext context,double fromObj, R toObj);
}