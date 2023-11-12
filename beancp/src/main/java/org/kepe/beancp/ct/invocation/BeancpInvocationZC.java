package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZC extends BeancpInvocation {
	char proceed(BeancpContext context,boolean fromObj, char toObj);
	Class<Boolean> getFromClass();
	Class<Character> getToClass();
}