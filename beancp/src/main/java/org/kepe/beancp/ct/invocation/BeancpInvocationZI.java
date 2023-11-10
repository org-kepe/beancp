package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZI extends BeancpInvocation<Boolean,Integer> {
	int proceed(BeancpContext context,boolean fromObj, int toObj);
}