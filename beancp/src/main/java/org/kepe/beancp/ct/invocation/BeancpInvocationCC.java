package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCC extends BeancpInvocation {
	char proceed(BeancpContext context,char fromObj, char toObj);
	Class<Character> getFromClass();
	Class<Character> getToClass();
}