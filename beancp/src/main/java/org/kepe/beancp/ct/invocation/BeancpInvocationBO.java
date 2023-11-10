package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBO<R> extends BeancpInvocation<Byte,R> {
	R proceed(BeancpContext context,byte fromObj, R toObj);
}