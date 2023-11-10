package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSI extends BeancpInvocation<Short,Integer> {
	int proceed(BeancpContext context,short fromObj, int toObj);
}