package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCB extends BeancpInvocation<Character,Byte> {
	byte proceed(BeancpContext context,char fromObj, byte toObj);
}