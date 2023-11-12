package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationII extends BeancpInvocation {
	int proceed(BeancpContext context,int fromObj, int toObj);
	Class<Integer> getFromClass();
	Class<Integer> getToClass();
}