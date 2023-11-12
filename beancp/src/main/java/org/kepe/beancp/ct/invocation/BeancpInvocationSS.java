package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSS extends BeancpInvocation {
	short proceed(BeancpContext context,short fromObj, short toObj);
	Class<Short> getFromClass();
	Class<Short> getToClass();
}