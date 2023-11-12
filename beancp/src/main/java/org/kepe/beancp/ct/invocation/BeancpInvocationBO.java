package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBO<R> extends BeancpInvocation {
	R proceed(BeancpContext context,byte fromObj, R toObj);
	Class<Byte> getFromClass();
	Class<R> getToClass();
}