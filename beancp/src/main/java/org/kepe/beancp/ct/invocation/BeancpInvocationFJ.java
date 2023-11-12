package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFJ extends BeancpInvocation {
	long proceed(BeancpContext context,float fromObj, long toObj);
	Class<Float> getFromClass();
	Class<Long> getToClass();
}