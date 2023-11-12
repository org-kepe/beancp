package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDJ extends BeancpInvocation {
	long proceed(BeancpContext context,double fromObj, long toObj);
	Class<Double> getFromClass();
	Class<Long> getToClass();
}