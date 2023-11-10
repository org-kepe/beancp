package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSC extends BeancpInvocation<Short,Character> {
	char proceed(BeancpContext context,short fromObj, char toObj);
}