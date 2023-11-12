package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJC extends BeancpInvocation {
	char proceed(BeancpContext context,long fromObj, char toObj);
	Class<Long> getFromClass();
	Class<Character> getToClass();
}