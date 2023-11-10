package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZB extends BeancpInvocation<Boolean,Byte> {
	byte proceed(BeancpContext context,boolean fromObj, byte toObj);
}