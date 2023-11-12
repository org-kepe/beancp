package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFC extends BeancpInvocation {
	char proceed(BeancpContext context,float fromObj, char toObj);
	Class<Float> getFromClass();
	Class<Character> getToClass();
}