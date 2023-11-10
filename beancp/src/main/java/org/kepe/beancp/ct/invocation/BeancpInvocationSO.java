package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSO<R> extends BeancpInvocation<Short,R> {
	R proceed(BeancpContext context,short fromObj, R toObj);
}