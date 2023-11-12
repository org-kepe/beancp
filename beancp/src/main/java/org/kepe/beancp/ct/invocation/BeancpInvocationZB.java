package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZB extends BeancpInvocation {
	byte proceed(BeancpContext context,boolean fromObj, byte toObj);
	Class<Boolean> getFromClass();
	Class<Byte> getToClass();
}