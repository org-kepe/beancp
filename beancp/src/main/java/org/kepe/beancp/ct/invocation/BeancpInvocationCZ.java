package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,char fromObj, boolean toObj);
	Class<Character> getFromClass();
	Class<Boolean> getToClass();
}