package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBB extends BeancpInvocation {
	byte proceed(BeancpContext context,byte fromObj, byte toObj);
	Class<Byte> getFromClass();
	Class<Byte> getToClass();
}