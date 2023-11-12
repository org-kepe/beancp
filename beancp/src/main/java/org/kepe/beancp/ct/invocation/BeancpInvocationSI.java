package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSI extends BeancpInvocation {
	int proceed(BeancpContext context,short fromObj, int toObj);
	Class<Short> getFromClass();
	Class<Integer> getToClass();
}