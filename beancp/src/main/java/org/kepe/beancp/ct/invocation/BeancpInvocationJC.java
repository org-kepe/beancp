package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJC extends BeancpInvocation<Long,Character> {
	char proceed(BeancpContext context,long fromObj, char toObj);
}