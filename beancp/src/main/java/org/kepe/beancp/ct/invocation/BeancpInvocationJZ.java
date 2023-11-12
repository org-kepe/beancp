package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,long fromObj, boolean toObj);
	Class<Long> getFromClass();
	Class<Boolean> getToClass();
}