package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSB extends BeancpInvocation<Short,Byte> {
	byte proceed(BeancpContext context,short fromObj, byte toObj);
}