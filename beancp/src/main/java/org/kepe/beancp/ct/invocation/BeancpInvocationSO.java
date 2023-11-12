package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,short fromObj, R toObj);
	Class<Short> getFromClass();
	Class<R> getToClass();
}