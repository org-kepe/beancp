package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCI extends BeancpInvocation {
	int proceed(BeancpContext context,char fromObj, int toObj);
	Class<Character> getFromClass();
	Class<Integer> getToClass();
}