package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJS extends BeancpInvocation {
	short proceed(BeancpContext context,long fromObj, short toObj);
	Class<Long> getFromClass();
	Class<Short> getToClass();
}