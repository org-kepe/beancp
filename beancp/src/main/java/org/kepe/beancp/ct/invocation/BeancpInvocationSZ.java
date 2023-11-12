package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,short fromObj, boolean toObj);
	Class<Short> getFromClass();
	Class<Boolean> getToClass();
}