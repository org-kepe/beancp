package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOS<T> extends BeancpInvocation<T,Short> {
	short proceed(BeancpContext context,T fromObj, short toObj);
}