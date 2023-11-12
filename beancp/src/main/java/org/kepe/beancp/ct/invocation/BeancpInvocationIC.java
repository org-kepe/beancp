package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIC extends BeancpInvocation {
	char proceed(BeancpContext context,int fromObj, char toObj);
	Class<Integer> getFromClass();
	Class<Character> getToClass();
}