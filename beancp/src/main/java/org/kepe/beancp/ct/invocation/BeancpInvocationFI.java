package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFI extends BeancpInvocation<Float,Integer> {
	int proceed(BeancpContext context,float fromObj, int toObj);
}