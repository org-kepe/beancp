package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDB extends BeancpInvocation {
	byte proceed(BeancpContext context,double fromObj, byte toObj);
	Class<Double> getFromClass();
	Class<Byte> getToClass();
}