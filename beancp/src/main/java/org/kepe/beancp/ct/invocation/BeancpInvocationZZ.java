package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZZ extends BeancpInvocation<Boolean,Boolean> {
	boolean proceed(BeancpContext context,boolean fromObj, boolean toObj);
}