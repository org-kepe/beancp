package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIC extends BeancpInvocation<Integer,Character> {
	char proceed(BeancpContext context,int fromObj, char toObj);
}