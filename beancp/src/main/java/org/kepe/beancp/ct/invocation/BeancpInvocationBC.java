package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBC extends BeancpInvocation {
	char proceed(BeancpContext context,byte fromObj, char toObj);
	Class<Byte> getFromClass();
	Class<Character> getToClass();
}