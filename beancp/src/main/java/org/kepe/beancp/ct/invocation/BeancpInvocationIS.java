package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIS extends BeancpInvocation {
	short proceed(BeancpContext context,int fromObj, short toObj);
	Class<Integer> getFromClass();
	Class<Short> getToClass();
}