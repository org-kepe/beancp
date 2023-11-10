package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIB extends BeancpInvocation<Integer,Byte> {
	byte proceed(BeancpContext context,int fromObj, byte toObj);
}