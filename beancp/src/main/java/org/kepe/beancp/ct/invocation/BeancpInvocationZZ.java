package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,boolean fromObj, boolean toObj);
	Class<Boolean> getFromClass();
	Class<Boolean> getToClass();
}