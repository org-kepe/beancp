package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZS extends BeancpInvocation {
	short proceed(BeancpContext context,boolean fromObj, short toObj);
	Class<Boolean> getFromClass();
	Class<Short> getToClass();
}