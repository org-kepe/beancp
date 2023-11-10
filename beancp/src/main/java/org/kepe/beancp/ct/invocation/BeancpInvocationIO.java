package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIO<R> extends BeancpInvocation<Integer,R> {
	R proceed(BeancpContext context,int fromObj, R toObj);
}