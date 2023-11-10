package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZC extends BeancpInvocation<Boolean,Character> {
	char proceed(BeancpContext context,boolean fromObj, char toObj);
}