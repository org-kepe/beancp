package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDC extends BeancpInvocation {
	char proceed(BeancpContext context,double fromObj, char toObj);
	Class<Double> getFromClass();
	Class<Character> getToClass();
}