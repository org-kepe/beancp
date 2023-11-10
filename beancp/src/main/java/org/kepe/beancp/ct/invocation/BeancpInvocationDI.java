package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDI extends BeancpInvocation<Double,Integer> {
	int proceed(BeancpContext context,double fromObj, int toObj);
}