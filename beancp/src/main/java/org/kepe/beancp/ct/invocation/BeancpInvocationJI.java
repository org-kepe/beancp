package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJI extends BeancpInvocation {
	int proceed(BeancpContext context,long fromObj, int toObj);
	Class<Long> getFromClass();
	Class<Integer> getToClass();
}