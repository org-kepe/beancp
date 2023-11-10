package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBB extends BeancpInvocation<Byte,Byte> {
	byte proceed(BeancpContext context,byte fromObj, byte toObj);
}