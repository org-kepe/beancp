package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJO<R> extends BeancpInvocation<Long,R> {
	R proceed(BeancpContext context,long fromObj, R toObj);
}