package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDC extends BeancpInvocation<Double,Character> {
	char proceed(BeancpContext context,double fromObj, char toObj);
}