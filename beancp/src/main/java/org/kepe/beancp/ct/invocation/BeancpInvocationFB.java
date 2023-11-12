package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFB extends BeancpInvocation {
	byte proceed(BeancpContext context,float fromObj, byte toObj);
	Class<Float> getFromClass();
	Class<Byte> getToClass();
}