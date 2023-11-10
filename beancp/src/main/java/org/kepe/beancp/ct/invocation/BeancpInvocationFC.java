package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFC extends BeancpInvocation<Float,Character> {
	char proceed(BeancpContext context,float fromObj, char toObj);
}