package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZS extends BeancpInvocation<Boolean,Short> {
	short proceed(BeancpContext context,boolean fromObj, short toObj);
}