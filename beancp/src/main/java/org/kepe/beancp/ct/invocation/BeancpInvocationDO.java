package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,double fromObj, R toObj);
	Class<Double> getFromClass();
	Class<R> getToClass();
}