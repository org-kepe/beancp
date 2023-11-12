package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJJ extends BeancpInvocation {
	long proceed(BeancpContext context,long fromObj, long toObj);
	Class<Long> getFromClass();
	Class<Long> getToClass();
}