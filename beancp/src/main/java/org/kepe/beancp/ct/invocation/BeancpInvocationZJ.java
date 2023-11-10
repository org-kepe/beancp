package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZJ extends BeancpInvocation<Boolean,Long> {
	long proceed(BeancpContext context,boolean fromObj, long toObj);
}