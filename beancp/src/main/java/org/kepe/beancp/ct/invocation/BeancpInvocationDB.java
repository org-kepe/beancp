package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDB extends BeancpInvocation<Double,Byte> {
	byte proceed(BeancpContext context,double fromObj, byte toObj);
}