package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSC extends BeancpInvocation {
	char proceed(BeancpContext context,short fromObj, char toObj);
	Class<Short> getFromClass();
	Class<Character> getToClass();
}