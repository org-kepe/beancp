package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSJ extends BeancpInvocation<Short,Long> {
	long proceed(BeancpContext context,short fromObj, long toObj);
}