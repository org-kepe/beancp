package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCS extends BeancpInvocation {
	short proceed(BeancpContext context,char fromObj, short toObj);
	Class<Character> getFromClass();
	Class<Short> getToClass();
}