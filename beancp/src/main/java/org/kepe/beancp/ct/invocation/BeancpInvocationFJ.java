package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFJ extends BeancpInvocation<Float,Long> {
	long proceed(BeancpContext context,float fromObj, long toObj);
}