package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJB extends BeancpInvocation {
	byte proceed(BeancpContext context,long fromObj, byte toObj);
	Class<Long> getFromClass();
	Class<Byte> getToClass();
}