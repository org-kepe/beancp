package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJB extends BeancpInvocation<Long,Byte> {
	byte proceed(BeancpContext context,long fromObj, byte toObj);
}