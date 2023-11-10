package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDJ extends BeancpInvocation<Double,Long> {
	long proceed(BeancpContext context,double fromObj, long toObj);
}