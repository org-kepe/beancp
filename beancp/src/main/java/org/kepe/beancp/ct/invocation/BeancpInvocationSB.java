package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSB extends BeancpInvocation {
	byte proceed(BeancpContext context,short fromObj, byte toObj);
	Class<Short> getFromClass();
	Class<Byte> getToClass();
}