package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCJ extends BeancpInvocation {
	long proceed(BeancpContext context,char fromObj, long toObj);
	Class<Character> getFromClass();
	Class<Long> getToClass();
}