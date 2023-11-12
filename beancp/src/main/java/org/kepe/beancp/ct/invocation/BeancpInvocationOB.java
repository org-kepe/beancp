package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOB<T> extends BeancpInvocation {
	byte proceed(BeancpContext context,T fromObj, byte toObj);
	Class<T> getFromClass();
	Class<Byte> getToClass();
}