package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZJ extends BeancpInvocation {
	long proceed(BeancpContext context,boolean fromObj, long toObj);
	Class<Boolean> getFromClass();
	Class<Long> getToClass();
}