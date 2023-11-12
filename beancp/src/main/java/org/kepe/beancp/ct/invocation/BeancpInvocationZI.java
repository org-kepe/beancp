package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZI extends BeancpInvocation {
	int proceed(BeancpContext context,boolean fromObj, int toObj);
	Class<Boolean> getFromClass();
	Class<Integer> getToClass();
}